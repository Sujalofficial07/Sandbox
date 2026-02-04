package com.sujal.skyblock.core.internal.stats;

import com.sujal.skyblock.core.api.stats.SkyBlockStats;
import com.sujal.skyblock.core.api.stats.SkyBlockStatsAPI;
import com.sujal.skyblock.core.api.util.MathUtils;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StatsManager implements SkyBlockStatsAPI.SkyBlockStatsProvider {
    private final Map<UUID, StatsImpl> playerStats;
    private final StatsCalculator calculator;
    
    public StatsManager() {
        this.playerStats = new ConcurrentHashMap<>();
        this.calculator = new StatsCalculator();
    }
    
    @Override
    public Optional<SkyBlockStats> getStats(ServerPlayerEntity player) {
        return Optional.ofNullable(playerStats.computeIfAbsent(player.getUuid(), uuid -> new StatsImpl()));
    }
    
    @Override
    public Optional<SkyBlockStats> getStats(UUID uuid) {
        return Optional.ofNullable(playerStats.get(uuid));
    }
    
    @Override
    public double calculateDamage(ServerPlayerEntity attacker, double baseWeaponDamage, boolean isCrit) {
        return getStats(attacker)
            .map(stats -> calculator.calculateDamage(stats, baseWeaponDamage, isCrit))
            .orElse(baseWeaponDamage);
    }
    
    @Override
    public double calculateDefenseReduction(ServerPlayerEntity defender, double incomingDamage) {
        return getStats(defender)
            .map(stats -> calculator.calculateDefenseReduction(stats, incomingDamage))
            .orElse(incomingDamage);
    }
    
    @Override
    public boolean rollCritical(ServerPlayerEntity player) {
        return getStats(player)
            .map(stats -> MathUtils.rollPercentage(stats.getCritChance()))
            .orElse(false);
    }
    
    public void removeStats(UUID uuid) {
        playerStats.remove(uuid);
    }
}
