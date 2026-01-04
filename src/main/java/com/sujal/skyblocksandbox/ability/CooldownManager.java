package com.sujal.skyblocksandbox.ability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    // Map<PlayerUUID, Map<AbilityName, TimeStamp>>
    private static final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public static boolean isOnCooldown(PlayerEntity player, String abilityId) {
        if (!cooldowns.containsKey(player.getUuid())) return false;
        
        long end = cooldowns.get(player.getUuid()).getOrDefault(abilityId, 0L);
        return System.currentTimeMillis() < end;
    }

    public static void setCooldown(PlayerEntity player, String abilityId, int seconds) {
        cooldowns.computeIfAbsent(player.getUuid(), k -> new HashMap<>())
                 .put(abilityId, System.currentTimeMillis() + (seconds * 1000L));
    }

    // Overload for milliseconds (for shortbows/spam abilities)
    public static void setCooldownMillis(PlayerEntity player, String abilityId, int ms) {
        cooldowns.computeIfAbsent(player.getUuid(), k -> new HashMap<>())
                 .put(abilityId, System.currentTimeMillis() + ms);
    }
    
    public static void sendCooldownMessage(PlayerEntity player) {
        player.sendMessage(Text.literal("§cThis ability is on cooldown!"), true);
    }
}
