package com.sujal.skyblocksandbox.stats;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class StatsHandler {

    // Cache for current player stats
    private static final Map<PlayerEntity, Map<StatType, Double>> PLAYER_STATS = new HashMap<>();
    
    // Dynamic Mana (Current Mana) storage
    private static final Map<PlayerEntity, Double> CURRENT_MANA = new HashMap<>();

    public static double getStat(PlayerEntity player, StatType stat) {
        if (!PLAYER_STATS.containsKey(player)) {
            recalculateStats(player);
        }
        return PLAYER_STATS.get(player).getOrDefault(stat, stat.getBaseValue());
    }
    
    public static double getCurrentMana(PlayerEntity player) {
        return CURRENT_MANA.getOrDefault(player, getStat(player, StatType.MANA));
    }
    
    // --- NEW: Mana Consumption Logic ---
    public static double getMaxMana(PlayerEntity player) {
        return getStat(player, StatType.MANA);
    }

    public static boolean consumeMana(PlayerEntity player, double amount) {
        double current = getCurrentMana(player);
        if (current >= amount) {
            CURRENT_MANA.put(player, current - amount);
            // Send action bar message
            player.sendMessage(Text.literal("§b-" + (int)amount + " Mana"), true);
            return true;
        } else {
            player.sendMessage(Text.literal("§cNot enough mana!"), true);
            return false;
        }
    }
    // -----------------------------------
    
    public static void regenerateMana(PlayerEntity player) {
        double maxMana = getStat(player, StatType.MANA);
        double current = getCurrentMana(player);
        // Regenerate 2% of max mana per second
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
        
        // Update Health if needed
        // Note: In a real mod you'd use AttributeModifiers here to sync with vanilla health
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
