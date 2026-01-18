package com.sujal.skyblockstats.damage;

import com.sujal.skyblockstats.api.SkyBlockAPI;
import com.sujal.skyblockstats.stats.SkyBlockStatType;
import com.sujal.skyblockstats.stats.StatContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class DamageCalculator {

    public static float calculateDamage(PlayerEntity attacker, LivingEntity target) {
        StatContainer stats = SkyBlockAPI.getStats(attacker);
        if (stats == null) return 1.0f;

        // 1. Gather Stats
        double weaponDamage = 5.0; // In future, get this from Item NBT
        double strength = stats.getStatValue(SkyBlockStatType.STRENGTH);
        double critChance = stats.getStatValue(SkyBlockStatType.CRIT_CHANCE);
        double critDamage = stats.getStatValue(SkyBlockStatType.CRIT_DAMAGE);
        double ferocity = stats.getStatValue(SkyBlockStatType.FEROCITY);
        
        // 2. Base Damage Calculation
        // Formula: (5 + WeaponDamage + Strength/5)
        double initialDamage = (5.0 + weaponDamage + (strength / 5.0));

        // 3. Strength Multiplier
        // Formula: * (1 + Strength/100)
        double strengthMultiplied = initialDamage * (1.0 + (strength / 100.0));

        // 4. Critical Hit Logic
        boolean isCrit = Math.random() * 100 < critChance;
        double critMultiplier = isCrit ? (1.0 + (critDamage / 100.0)) : 1.0;
        
        double finalRawDamage = strengthMultiplied * critMultiplier;

        // 5. Additive Multipliers (Combat Level, Enchants - placeholders for now)
        double additiveMultiplier = 1.0; // E.g., Giant Killer, Sharpness
        finalRawDamage *= additiveMultiplier;

        // 6. Defense Reduction (Target's Defense)
        // Assuming vanilla Armor = Defense for simple mobs
        double targetDefense = target.getArmor() * 5; // Vanilla armor is weak, multiplying by 5 to simulate SB Defense
        double defenseMultiplier = 100.0 / (100.0 + targetDefense);
        
        double finalDamage = finalRawDamage * defenseMultiplier;

        // 7. Visual Indicator (Action Bar)
        sendDamageIndicator(attacker, (long)finalDamage, isCrit);

        return (float) finalDamage;
    }

    private static void sendDamageIndicator(PlayerEntity player, long damage, boolean isCrit) {
        Formatting color = isCrit ? Formatting.RED : Formatting.GRAY;
        String icon = isCrit ? " ☠" : "";
        // Sends message to Action Bar: "1250 ☠"
        player.sendMessage(Text.literal(damage + icon).formatted(color), true);
    }
}
