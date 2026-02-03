package com.sujal.skyblock.core.stats;

import com.sujal.skyblock.api.SkyBlockStatsAPI;
import com.sujal.skyblock.core.data.DataHolder;
import com.sujal.skyblock.core.data.PlayerProfileData;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import java.util.HashMap;
import java.util.Map;

public class StatManager implements SkyBlockStatsAPI {
    
    public static final StatManager INSTANCE = new StatManager();

    private StatManager() {}

    // --- Accessors (Wait se pehle jaise) ---
    @Override public double getHealth(PlayerEntity p) { return ((DataHolder)p).getSkyBlockData().getCurrentHealth(); }
    @Override public double getMaxHealth(PlayerEntity p) { return ((DataHolder)p).getSkyBlockData().getBaseStat("health"); }
    @Override public double getDefense(PlayerEntity p) { return ((DataHolder)p).getSkyBlockData().getBaseStat("defense"); }
    @Override public double getMana(PlayerEntity p) { return ((DataHolder)p).getSkyBlockData().getCurrentMana(); }
    @Override public double getMaxMana(PlayerEntity p) { return ((DataHolder)p).getSkyBlockData().getBaseStat("mana"); }
    
    // --- New Functional Stats ---
    public double getSpeed(PlayerEntity p) { return ((DataHolder)p).getSkyBlockData().getBaseStat("speed"); }
    public double getAttackSpeed(PlayerEntity p) { return ((DataHolder)p).getSkyBlockData().getBaseStat("attack_speed"); }

    @Override
    public void setBaseStat(PlayerEntity p, String key, double val) {
        ((DataHolder)p).getSkyBlockData().setBaseStat(key, val);
        updateVanillaStats(p); // Trigger sync
    }

    /**
     * Critical: Converts SkyBlock Stats to Vanilla Attributes
     * Taaki player actually tez bhage ya jaldi hit kare.
     */
    public void updateVanillaStats(PlayerEntity player) {
        // 1. Movement Speed
        // SkyBlock: 100 Speed = 100% (Normal). 200 Speed = 200% (2x Fast).
        // Vanilla: 0.1 is base.
        double sbSpeed = getSpeed(player);
        if (sbSpeed == 0) sbSpeed = 100; // default safety
        
        double vanillaSpeed = 0.1 * (sbSpeed / 100.0);
        player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(vanillaSpeed);

        // 2. Attack Speed
        // SkyBlock: 100 Bonus Attack Speed reduces cooldown.
        // Vanilla: 4.0 generic attack speed is instant.
        double sbAtkSpeed = getAttackSpeed(player);
        double vanillaAtkSpeed = 4.0 * (1 + (sbAtkSpeed / 100.0));
        player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED).setBaseValue(vanillaAtkSpeed);
        
        // 3. Max Health (Visual Only - Real logic is in Mixin)
        // Hum vanilla health bar ko 20 hi rakhte hain taaki shake effect control mein rahe
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0);
    }
    
    @Override
    public double getCritChance(PlayerEntity p) { return ((DataHolder)p).getSkyBlockData().getBaseStat("crit_chance"); }
    @Override
    public double getCritDamage(PlayerEntity p) { return ((DataHolder)p).getSkyBlockData().getBaseStat("crit_damage"); }
    
    // Stub methods needed for interface
    @Override public void addBaseStat(PlayerEntity p, String k, double v) { setBaseStat(p, k, ((DataHolder)p).getSkyBlockData().getBaseStat(k) + v); }
    @Override public Map<String, Double> getAllStats(PlayerEntity p) { return new HashMap<>(); }
    
    // Combat math same as before...
    public float calculateDamageReduction(float damage, double defense) {
        return (float) (damage / (1 + (defense / 100.0)));
    }
}
