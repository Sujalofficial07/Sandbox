package com.sujal.skyblockstats.damage;

import com.sujal.skyblockstats.api.SkyBlockAPI;
import com.sujal.skyblockstats.stats.SkyBlockStatType;
import com.sujal.skyblockstats.stats.StatContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class DamageCalculator {

    public static float calculateDamage(PlayerEntity attacker, LivingEntity target) {
        StatContainer stats = SkyBlockAPI.getStats(attacker);
        if (stats == null) return 1.0f;

        // Base Damage (Weapon damage usually, here simplified to 5 + strength scaling base)
        double baseDamage = 5.0; 
        
        double strength = stats.getStatValue(SkyBlockStatType.STRENGTH);
        double critChance = stats.getStatValue(SkyBlockStatType.CRIT_CHANCE);
        double critDamage = stats.getStatValue(SkyBlockStatType.CRIT_DAMAGE);
        
        // Step 1: Base + Strength calculation
        // Formula: (BaseDamage + Strength/5)
        double damageStep1 = baseDamage + (strength / 5.0);

        // Step 2: Strength Multiplier
        // Formula: * (1 + Strength/100)
        double damageStep2 = damageStep1 * (1.0 + (strength / 100.0));

        // Step 3: Crit Calculation
        boolean isCrit = Math.random() * 100 < critChance;
        if (isCrit) {
            damageStep2 *= (1.0 + (critDamage / 100.0));
        }

        // Target Defense Reduction
        // Note: For a full system, target needs stats too. Assuming vanilla armor points = Defense for now.
        double targetDefense = target.getArmor(); 
        // Or if target is player: SkyBlockAPI.getStats(target).getStatValue(SkyBlockStatType.DEFENSE);

        double reductionMultiplier = 100.0 / (100.0 + targetDefense);
        double finalDamage = damageStep2 * reductionMultiplier;

        // Add True Damage (Ignores defense)
        double trueDamage = stats.getStatValue(SkyBlockStatType.TRUE_DAMAGE);
        
        return (float) (finalDamage + trueDamage);
    }
}
