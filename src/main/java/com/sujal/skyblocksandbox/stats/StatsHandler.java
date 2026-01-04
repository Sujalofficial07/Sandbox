package com.sujal.skyblocksandbox.stats;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import java.util.EnumMap;
import java.util.Map;

public class StatsHandler {

    // Cache for current player stats
    private static final Map<PlayerEntity, Map<StatType, Double>> PLAYER_STATS = new java.util.HashMap<>();
    
    // Dynamic Mana (Current Mana) storage
    private static final Map<PlayerEntity, Double> CURRENT_MANA = new java.util.HashMap<>();

    public static double getStat(PlayerEntity player, StatType stat) {
        if (!PLAYER_STATS.containsKey(player)) {
            recalculateStats(player);
        }
        return PLAYER_STATS.get(player).getOrDefault(stat, stat.getBaseValue());
    }
    
    public static double getCurrentMana(PlayerEntity player) {
        return CURRENT_MANA.getOrDefault(player, getStat(player, StatType.MANA));
    }
    
    public static void regenerateMana(PlayerEntity player) {
        double maxMana = getStat(player, StatType.MANA);
        double current = getCurrentMana(player);
        // Regenerate 2% of max mana per second (called from tick loop)
        double newMana = Math.min(maxMana, current + (maxMana * 0.02)); 
        CURRENT_MANA.put(player, newMana);
    }

    public static void recalculateStats(PlayerEntity player) {
        Map<StatType, Double> newStats = new EnumMap<>(StatType.class);

        // Initialize with Base Values
        for (StatType type : StatType.values()) {
            newStats.put(type, type.getBaseValue());
        }

        // Add Armor Stats
        for (ItemStack stack : player.getArmorItems()) {
            addStatsFromItem(stack, newStats);
        }

        // Add Held Item Stats (Main Hand)
        addStatsFromItem(player.getMainHandStack(), newStats);

        PLAYER_STATS.put(player, newStats);
        
        // Ensure Health doesn't exceed new Max Health (simple check)
        if (player.getHealth() > newStats.get(StatType.HEALTH).floatValue()) {
             player.setHealth(newStats.get(StatType.HEALTH).floatValue());
        }
    }

    private static void addStatsFromItem(ItemStack stack, Map<StatType, Double> statsMap) {
        if (stack.isEmpty() || !stack.hasNbt()) return;

        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains("skyblock:stats")) {
            NbtCompound statsTag = nbt.getCompound("skyblock:stats");
            
            for (StatType type : StatType.values()) {
                if (statsTag.contains(type.name())) {
                    double val = statsTag.getDouble(type.name());
                    statsMap.put(type, statsMap.get(type) + val);
                }
            }
        }
    }
}
