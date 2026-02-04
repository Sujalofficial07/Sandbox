package com.sujal.skyblock.core.internal.zone;

import com.sujal.skyblock.core.api.zone.Zone;

import java.util.HashMap;
import java.util.Map;

public class ZoneRegistry {
    private final Map<String, Zone> zones;
    
    public ZoneRegistry() {
        this.zones = new HashMap<>();
    }
    
    public void registerZone(Zone zone) {
        zones.put(zone.getId(), zone);
    }
    
    public Zone getZone(String id) {
        return zones.get(id);
    }
    
    public Map<String, Zone> getAllZones() {
        return new HashMap<>(zones);
    }
}
