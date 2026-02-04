package com.sujal.skyblock.core.api.stats;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;
import java.util.UUID;

public class SkyBlockStatsAPI {
    private static SkyBlockStatsProvider provider;
    
    public static void register(SkyBlockStatsProvider provider) {
        SkyBlockStatsAPI.provider = provider;
    }
    
    public static Optional<SkyBlockStats> getStats(ServerPlayerEntity player) {
        return provider != null ? provider.getStats(player) : Optional.empty();
    }
    
    public static Optional<SkyBlockStats> getStats(UUID uuid) {
        return provider != null ? provider.getStats(uuid) : Optional.empty();
    }
    
    public static double calculateDamage(ServerPlayerEntity attacker, double baseWeaponDamage, boolean isCrit) {
        if (provider == null) return baseWeaponDamage;
        return provider.calculateDamage(attacker, baseWeaponDamage, isCrit);
    }
    
    public static double calculateDefenseReduction(ServerPlayerEntity defender, double incomingDamage) {
        if (provider == null) return incomingDamage;
        return provider.calculateDefenseReduction(defender, incomingDamage);
    }
    
    public static boolean rollCritical(ServerPlayerEntity player) {
        if (provider == null) return false;
        return provider.rollCritical(player);
    }
    
    public interface SkyBlockStatsProvider {
        Optional<SkyBlockStats> getStats(ServerPlayerEntity player);
        Optional<SkyBlockStats> getStats(UUID uuid);
        double calculateDamage(ServerPlayerEntity attacker, double baseWeaponDamage, boolean isCrit);
        double calculateDefenseReduction(ServerPlayerEntity defender, double incomingDamage);
        boolean rollCritical(ServerPlayerEntity player);
    }
}
