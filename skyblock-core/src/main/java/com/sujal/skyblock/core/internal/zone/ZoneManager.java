package com.sujal.skyblock.core.internal.zone;

import com.sujal.skyblock.core.api.events.PlayerZoneChangeEvent;
import com.sujal.skyblock.core.api.events.SkyBlockEventAPI;
import com.sujal.skyblock.core.api.zone.Zone;
import com.sujal.skyblock.core.api.zone.SkyBlockZoneAPI;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ZoneManager implements SkyBlockZoneAPI.SkyBlockZoneProvider {
    private final Map<String, Zone> zones;
    private final ZoneRegistry registry;
    private final ZoneDetector detector;
    private final Map<UUID, Optional<Zone>> playerZones;
    
    public ZoneManager() {
        this.zones = new ConcurrentHashMap<>();
        this.registry = new ZoneRegistry();
        this.detector = new ZoneDetector(this);
        this.playerZones = new ConcurrentHashMap<>();
        
        registerDefaultZones();
    }
    
    private void registerDefaultZones() {
        registerZone(Zone.builder("hub_village")
            .displayName("Village")
            .bounds(new BlockPos(-100, 0, -100), new BlockPos(100, 256, 100))
            .buildingAllowed(false)
            .build());
    }
    
    @Override
    public Optional<Zone> getZone(String id) {
        return Optional.ofNullable(zones.get(id));
    }
    
    @Override
    public Optional<Zone> getZoneAt(World world, BlockPos pos) {
        return zones.values().stream()
            .filter(zone -> zone.getWorldKey().equals(world.getRegistryKey().getValue().toString()))
            .filter(zone -> zone.contains(pos))
            .findFirst();
    }
    
    @Override
    public Optional<Zone> getPlayerZone(ServerPlayerEntity player) {
        return playerZones.getOrDefault(player.getUuid(), Optional.empty());
    }
    
    @Override
    public void registerZone(Zone zone) {
        zones.put(zone.getId(), zone);
        registry.registerZone(zone);
    }
    
    @Override
    public List<Zone> getAllZones() {
        return new ArrayList<>(zones.values());
    }
    
    @Override
    public boolean canPlayerEnter(ServerPlayerEntity player, Zone zone) {
        return detector.canPlayerEnter(player, zone);
    }
    
    public void updatePlayerZone(ServerPlayerEntity player) {
        Optional<Zone> currentZone = playerZones.get(player.getUuid());
        Optional<Zone> newZone = getZoneAt(player.getWorld(), player.getBlockPos());
        
        if (!Objects.equals(currentZone, newZone)) {
            playerZones.put(player.getUuid(), newZone);
            SkyBlockEventAPI.fireEvent(new PlayerZoneChangeEvent(player, currentZone, newZone));
        }
    }
    
    public void removePlayer(UUID uuid) {
        playerZones.remove(uuid);
    }
}
