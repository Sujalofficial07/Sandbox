package com.sujal.skyblock.core.api.zone;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class SkyBlockZoneAPI {
    private static SkyBlockZoneProvider provider;
    
    public static void register(SkyBlockZoneProvider provider) {
        SkyBlockZoneAPI.provider = provider;
    }
    
    public static Optional<Zone> getZone(String id) {
        return provider != null ? provider.getZone(id) : Optional.empty();
    }
    
    public static Optional<Zone> getZoneAt(World world, BlockPos pos) {
        return provider != null ? provider.getZoneAt(world, pos) : Optional.empty();
    }
    
    public static Optional<Zone> getPlayerZone(ServerPlayerEntity player) {
        return provider != null ? provider.getPlayerZone(player) : Optional.empty();
    }
    
    public static void registerZone(Zone zone) {
        if (provider != null) {
            provider.registerZone(zone);
        }
    }
    
    public static List<Zone> getAllZones() {
        return provider != null ? provider.getAllZones() : List.of();
    }
    
    public static boolean canPlayerEnter(ServerPlayerEntity player, Zone zone) {
        return provider != null && provider.canPlayerEnter(player, zone);
    }
    
    public interface SkyBlockZoneProvider {
        Optional<Zone> getZone(String id);
        Optional<Zone> getZoneAt(World world, BlockPos pos);
        Optional<Zone> getPlayerZone(ServerPlayerEntity player);
        void registerZone(Zone zone);
        List<Zone> getAllZones();
        boolean canPlayerEnter(ServerPlayerEntity player, Zone zone);
    }
}
