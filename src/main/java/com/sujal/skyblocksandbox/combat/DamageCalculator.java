package com.sujal.skyblocksandbox.combat;

import com.sujal.skyblocksandbox.stats.StatType;
import com.sujal.skyblocksandbox.stats.StatsHandler;
import net.minecraft.entity.player.PlayerEntity;
import java.util.Random;

public class DamageCalculator {

    private static final Random random = new Random();

    public static class DamageResult {
        public double damage;
        public boolean isCrit;

        public DamageResult(double damage, boolean isCrit) {
            this.damage = damage;
            this.isCrit = isCrit;
        }
    }

    public static DamageResult calculateDamage(PlayerEntity player) {
        // 1. Get Stats
        StatsHandler.recalculateStats(player); // Ensure fresh data
        double strength = StatsHandler.getStat(player, StatType.STRENGTH);
        double critChance = StatsHandler.getStat(player, StatType.CRIT_CHANCE);
        double critDamage = StatsHandler.getStat(player, StatType.CRIT_DAMAGE);
        
        // Base Weapon Damage usually comes from the Item itself, 
        // but for now let's assume Strength acts as base scaler or hardcode a base 5.
        // In a full mod, you'd pull "Damage" from the Item NBT separately.
        double baseDamage = 5.0 + (strength * 0.2); // Simplified Logic

        // 2. Initial Formula: (Base + Strength/5) * (1 + Strength/100)
        double totalDamage = baseDamage * (1 + (strength / 100.0));

        // 3. Crit Calculation
        boolean isCrit = random.nextDouble() * 100 < critChance;
        if (isCrit) {
            totalDamage *= (1 + (critDamage / 100.0));
        }

        return new DamageResult(totalDamage, isCrit);
    }
}
