package com.sujal.skyblock.stats.engine;

import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.core.util.SBItemUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class DamageCalculator {

    public static float calculateDamage(PlayerEntity player, SkyblockProfile profile, boolean isCrit) {
        // 1. Get Stats
        double strength = StatCalculator.getFinalStat(player, profile, StatType.STRENGTH);
        double critDmg = StatCalculator.getFinalStat(player, profile, StatType.CRIT_DAMAGE);
        
        // 2. Get Weapon Damage
        ItemStack heldItem = player.getMainHandStack();
        double weaponDamage = SBItemUtils.getStat(heldItem, StatType.STRENGTH); // Usually "Damage" stat, using Strength field for now or add DAMAGE stat
        // NOTE: Ideally add DAMAGE to StatType enum. For now assuming base 5 + item strength.
        double baseDamage = 5 + weaponDamage; 

        // 3. The Hypixel Formula
        // Damage = (5 + WeaponDamage + Strength/5) * (1 + Strength/100) * (1 + CritDamage/100) * ...
        
        double initialDamage = (baseDamage + (strength / 5.0)) * (1.0 + (strength / 100.0));

        // 4. Apply Crit
        if (isCrit) {
            initialDamage *= (1.0 + (critDmg / 100.0));
        }

        // 5. Add Combat Level Multiplier (Example: +4% per level)
        // double combatLevel = profile.getSkillLevel("COMBAT");
        // initialDamage *= (1 + (combatLevel * 0.04));

        return (float) initialDamage;
    }
    
    public static boolean checkCrit(PlayerEntity player, SkyblockProfile profile) {
        double cc = StatCalculator.getFinalStat(player, profile, StatType.CRIT_CHANCE);
        return Math.random() * 100 < cc;
    }
}
