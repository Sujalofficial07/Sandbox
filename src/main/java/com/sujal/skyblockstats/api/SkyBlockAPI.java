package com.sujal.skyblockstats.api;

import com.sujal.skyblockstats.components.PlayerSkyBlockComponent;
import com.sujal.skyblockstats.registry.ComponentRegistry;
import com.sujal.skyblockstats.stats.SkyBlockProfile;
import com.sujal.skyblockstats.stats.SkyBlockStatType;
import com.sujal.skyblockstats.stats.StatContainer;
import net.minecraft.entity.player.PlayerEntity;

public class SkyBlockAPI {

    // --- Stats API ---
    public static StatContainer getStats(PlayerEntity player) {
        return ComponentRegistry.SKYBLOCK_STATS.get(player).getStats();
    }

    public static void addStatBonus(PlayerEntity player, SkyBlockStatType stat, double amount) {
        getStats(player).getStat(stat).addBonus(amount);
    }

    public static double getStatValue(PlayerEntity player, SkyBlockStatType stat) {
        return getStats(player).getStatValue(stat);
    }

    // --- Economy API ---
    public static double getCoins(PlayerEntity player) {
        return ComponentRegistry.SKYBLOCK_STATS.get(player).getProfile().getCoins();
    }

    public static void addCoins(PlayerEntity player, double amount) {
        ComponentRegistry.SKYBLOCK_STATS.get(player).getProfile().addCoins(amount);
    }

    public static boolean removeCoins(PlayerEntity player, double amount) {
        return ComponentRegistry.SKYBLOCK_STATS.get(player).getProfile().removeCoins(amount);
    }

    // --- Mana API ---
    public static boolean consumeMana(PlayerEntity player, double amount) {
        SkyBlockProfile profile = ComponentRegistry.SKYBLOCK_STATS.get(player).getProfile();
        if (profile.getCurrentMana() >= amount) {
            profile.consumeMana(amount);
            return true;
        }
        return false;
    }
}
