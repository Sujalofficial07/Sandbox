package com.sujal.skyblock.stats.engine;

import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.core.util.SBItemUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class DamageCalculator {

    // Hypixel Melee Damage Formula
    public static float calculateMeleeDamage(PlayerEntity player, SkyblockProfile profile, boolean isCrit) {
        // 1. Get Stats
        double strength = StatCalculator.getFinalStat(player, profile, StatType.STRENGTH);
        double critDmg = StatCalculator.getFinalStat(player, profile, StatType.CRIT_DAMAGE);
        
        ItemStack heldItem = player.getMainHandStack();
        double weaponDamage = SBItemUtils.getStat(heldItem, StatType.STRENGTH); // Using Str field for Damage for now
        double baseDamage = 5 + weaponDamage; 

        // Formula: (5 + WeaponDamage + Strength/5) * (1 + Strength/100)
        double initialDamage = (baseDamage + (strength / 5.0)) * (1.0 + (strength / 100.0));

        // Apply Crit
        if (isCrit) {
            initialDamage *= (1.0 + (critDmg / 100.0));
        }

        // Add Combat Level Additive (Placeholder: +4% per level, assume lvl 0 for now)
        // initialDamage *= (1 + (combatLevel * 0.04));

        return (float) initialDamage;
    }
    
    // Hypixel Magic/Ability Damage Formula
    public static float calculateMagicDamage(PlayerEntity player, SkyblockProfile profile, double baseAbilityDamage, double abilityScaling) {
        double intelligence = StatCalculator.getFinalStat(player, profile, StatType.INTELLIGENCE);
        
        // Formula: BaseAbilityDmg * (1 + (Intelligence / 100) * Scaling)
        double magicDamage = baseAbilityDamage * (1.0 + ((intelligence / 100.0) * abilityScaling));
        
        return (float) magicDamage;
    }

    public static boolean checkCrit(PlayerEntity player, SkyblockProfile profile) {
        double cc = StatCalculator.getFinalStat(player, profile, StatType.CRIT_CHANCE);
        return Math.random() * 100 < cc;
    }
}
