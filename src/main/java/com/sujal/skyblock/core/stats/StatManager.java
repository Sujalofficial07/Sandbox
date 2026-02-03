package com.sujal.skyblock.core.stats;

import com.sujal.skyblock.api.SkyBlockStatsAPI;
import com.sujal.skyblock.core.data.DataHolder;
import com.sujal.skyblock.core.data.PlayerProfileData;
import net.minecraft.entity.player.PlayerEntity;
import java.util.HashMap;
import java.util.Map;

public class StatManager implements SkyBlockStatsAPI {
    
    public static final StatManager INSTANCE = new StatManager();

    private StatManager() {}

    @Override
    public double getHealth(PlayerEntity player) {
        return ((DataHolder) player).getSkyBlockData().getCurrentHealth();
    }

    @Override
    public double getMaxHealth(PlayerEntity player) {
        // Formula: Base + Armor + Skills (Simplified for Core, iterates items in full impl)
        PlayerProfileData data = ((DataHolder) player).getSkyBlockData();
        double base = data.getBaseStat("health");
        // Add armor/item logic here iterating equipment
        return base; 
    }

    @Override
    public double getDefense(PlayerEntity player) {
        PlayerProfileData data = ((DataHolder) player).getSkyBlockData();
        return data.getBaseStat("defense");
    }

    @Override
    public double getMana(PlayerEntity player) {
        return ((DataHolder) player).getSkyBlockData().getCurrentMana();
    }
    
    @Override
    public double getMaxMana(PlayerEntity player) {
        return ((DataHolder) player).getSkyBlockData().getBaseStat("mana");
    }

    @Override
    public double getStrength(PlayerEntity player) {
        return ((DataHolder) player).getSkyBlockData().getBaseStat("strength");
    }

    @Override
    public double getCritChance(PlayerEntity player) {
        return ((DataHolder) player).getSkyBlockData().getBaseStat("crit_chance");
    }

    @Override
    public double getCritDamage(PlayerEntity player) {
        return ((DataHolder) player).getSkyBlockData().getBaseStat("crit_damage");
    }

    @Override
    public void setBaseStat(PlayerEntity player, String statKey, double value) {
        ((DataHolder) player).getSkyBlockData().setBaseStat(statKey, value);
    }

    @Override
    public void addBaseStat(PlayerEntity player, String statKey, double value) {
        PlayerProfileData data = ((DataHolder) player).getSkyBlockData();
        data.setBaseStat(statKey, data.getBaseStat(statKey) + value);
    }

    @Override
    public Map<String, Double> getAllStats(PlayerEntity player) {
        // In a full implementation, this aggregates caches
        return new HashMap<>(); 
    }

    // --- Combat Logic ---
    
    public double calculateDamage(PlayerEntity attacker) {
        double damage = 5; // Base weapon damage (would come from item NBT)
        double strength = getStrength(attacker);
        double critDamage = getCritDamage(attacker);
        double critChance = getCritChance(attacker);

        // Hypixel Formula: (5 + Weapon Dmg) * (1 + Strength/100)
        double baseDmg = (5 + damage) * (1 + (strength / 100.0));
        
        // Crit Logic
        boolean isCrit = Math.random() * 100 < critChance;
        if (isCrit) {
            baseDmg *= (1 + (critDamage / 100.0));
        }
        
        return baseDmg;
    }
    
    public float calculateDamageReduction(float damage, double defense) {
        // Hypixel Formula: Damage / (1 + Defense/100)
        return (float) (damage / (1 + (defense / 100.0)));
    }
}
