package com.sujal.skyblockstats.components;

import com.sujal.skyblockstats.stats.SkyBlockProfile;
import com.sujal.skyblockstats.stats.SkyBlockStatType;
import com.sujal.skyblockstats.stats.StatContainer;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent; // Import Tick interface
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.Objects;

// Implement ServerTickingComponent to update stats every tick
public class PlayerSkyBlockComponent implements Component, ServerTickingComponent {
    private final PlayerEntity player;
    private final SkyBlockProfile profile;

    public PlayerSkyBlockComponent(PlayerEntity player) {
        this.player = player;
        this.profile = new SkyBlockProfile();
    }

    public SkyBlockProfile getProfile() { return profile; }
    public StatContainer getStats() { return profile.getStatContainer(); }

    @Override
    public void serverTick() {
        syncStatsToVanilla();
        handleRegeneration();
    }

    // ⚡ SYNC: SkyBlock Stats -> Vanilla Attributes
    private void syncStatsToVanilla() {
        double sbHealth = getStats().getStatValue(SkyBlockStatType.HEALTH);
        double sbSpeed = getStats().getStatValue(SkyBlockStatType.SPEED);

        // 1. Sync Max Health
        var healthAttr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (healthAttr != null && healthAttr.getBaseValue() != sbHealth) {
            healthAttr.setBaseValue(sbHealth);
            // Agar current health max se zyada hai toh adjust karo
            if (player.getHealth() > sbHealth) {
                player.setHealth((float) sbHealth);
            }
        }

        // 2. Sync Speed (Default walk speed is 0.1, SkyBlock 100 speed = 0.1)
        // Formula: Vanilla = (SkyBlockSpeed / 1000)
        var speedAttr = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (speedAttr != null) {
            double targetSpeed = sbSpeed / 1000.0; 
            if (Math.abs(speedAttr.getBaseValue() - targetSpeed) > 0.0001) {
                speedAttr.setBaseValue(targetSpeed);
            }
        }
    }

    // 💖 REGEN: Health & Mana Logic
    private void handleRegeneration() {
        // --- MANA REGEN ---
        double maxMana = getStats().getStatValue(SkyBlockStatType.MANA); // Renamed from INTELLIGENCE usually, assuming MANA stat is Max Mana
        double current = profile.getCurrentMana();
        
        if (current < maxMana) {
            // Base 2% regen per second
            double regenRate = maxMana * 0.02; 
            // Add Mana Regen stat later if needed
            
            // Per tick (20 ticks per sec)
            double manaPerTick = regenRate / 20.0;
            profile.setCurrentMana(Math.min(maxMana, current + manaPerTick));
        }

        // --- HEALTH REGEN (Natural) ---
        // Vanilla handles hunger regen, but SkyBlock overrides it usually.
        // Simple logic: If not dead, heal 1% max health + flat amount per 2 seconds
        if (player.age % 40 == 0 && player.getHealth() < player.getMaxHealth()) {
            double regenAmount = (player.getMaxHealth() * 0.01) + 1.0;
            player.heal((float) regenAmount);
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) { profile.readFromNbt(tag); }

    @Override
    public void writeToNbt(NbtCompound tag) { profile.writeToNbt(tag); }
}
