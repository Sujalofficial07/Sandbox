package com.sujal.skyblock.api;

import net.minecraft.entity.player.PlayerEntity;
import java.util.Map;

/**
 * Interface for accessing and modifying player SkyBlock stats.
 */
public interface SkyBlockStatsAPI {
    double getHealth(PlayerEntity player);
    double getMaxHealth(PlayerEntity player);
    double getDefense(PlayerEntity player);
    double getMana(PlayerEntity player);
    double getMaxMana(PlayerEntity player);
    double getStrength(PlayerEntity player);
    double getCritChance(PlayerEntity player);
    double getCritDamage(PlayerEntity player);
    
    // Setters for base stats (Permanent changes)
    void setBaseStat(PlayerEntity player, String statKey, double value);
    void addBaseStat(PlayerEntity player, String statKey, double value);
    
    // For temporary modifiers (Potions, Armor, etc - handled internally via cache)
    Map<String, Double> getAllStats(PlayerEntity player);
}
