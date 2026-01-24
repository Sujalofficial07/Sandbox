package com.sujal.skyblock.core.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    // Map<PlayerUUID, Map<AbilityName, CooldownEndTime>>
    private static final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public static boolean isOnCooldown(PlayerEntity player, String abilityName) {
        if (!cooldowns.containsKey(player.getUuid())) return false;
        
        Map<String, Long> playerCooldowns = cooldowns.get(player.getUuid());
        if (!playerCooldowns.containsKey(abilityName)) return false;

        long endTime = playerCooldowns.get(abilityName);
        return System.currentTimeMillis() < endTime;
    }

    public static void setCooldown(PlayerEntity player, String abilityName, int seconds) {
        if (seconds <= 0) return;
        
        cooldowns.computeIfAbsent(player.getUuid(), k -> new HashMap<>())
                 .put(abilityName, System.currentTimeMillis() + (seconds * 1000L));
    }

    public static double getRemainingTime(PlayerEntity player, String abilityName) {
        if (!isOnCooldown(player, abilityName)) return 0;
        long endTime = cooldowns.get(player.getUuid()).get(abilityName);
        return (endTime - System.currentTimeMillis()) / 1000.0;
    }

    public static void sendCooldownMessage(PlayerEntity player, String abilityName) {
        double left = getRemainingTime(player, abilityName);
        player.sendMessage(Text.literal("§cThis ability is on cooldown for " + String.format("%.1fs", left) + "!"), true);
    }
    
    public static void sendManaMessage(PlayerEntity player, int needed) {
        player.sendMessage(Text.literal("§cNot enough Mana! §7Need " + needed + "✎"), true);
    }
    
    public static void sendAbilityMessage(PlayerEntity player, String abilityName, int manaCost) {
        // Hypixel Style: Used Ability Name (Mana Cost Mana)
        player.sendMessage(Text.literal("§aUsed §6" + abilityName + "§a! (§b-" + manaCost + " Mana§a)"), true);
    }
}
