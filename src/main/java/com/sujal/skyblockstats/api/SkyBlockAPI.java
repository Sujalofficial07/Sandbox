package com.sujal.skyblockstats.api;

import com.sujal.skyblockstats.components.PlayerSkyBlockComponent;
import com.sujal.skyblockstats.registry.ComponentRegistry;
import com.sujal.skyblockstats.stats.SkyBlockProfile;
import com.sujal.skyblockstats.stats.SkyBlockStatType;
import com.sujal.skyblockstats.stats.StatContainer;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;

public class SkyBlockAPI {

    public static Optional<SkyBlockProfile> getProfile(PlayerEntity player) {
        return ComponentRegistry.SKYBLOCK_STATS.maybeGet(player).map(PlayerSkyBlockComponent::getProfile);
    }

    public static StatContainer getStats(PlayerEntity player) {
        return ComponentRegistry.SKYBLOCK_STATS.get(player).getStats();
    }

    public static void addStatBonus(PlayerEntity player, SkyBlockStatType stat, double amount) {
        getStats(player).getStat(stat).addBonus(amount);
    }

    public static void removeStatBonus(PlayerEntity player, SkyBlockStatType stat, double amount) {
        getStats(player).getStat(stat).removeBonus(amount);
    }
    
    public static double getStatValue(PlayerEntity player, SkyBlockStatType stat) {
        return getStats(player).getStatValue(stat);
    }
}
