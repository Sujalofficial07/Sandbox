package com.sujal.skyblock.core.internal.protection;

import com.sujal.skyblock.core.api.zone.SkyBlockZoneAPI;
import com.sujal.skyblock.core.api.zone.Zone;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class WorldProtectionManager {
    
    public boolean canBreakBlock(ServerPlayerEntity player, World world, BlockPos pos) {
        Optional<Zone> zoneOpt = SkyBlockZoneAPI.getZoneAt(world, pos);
        
        if (zoneOpt.isPresent()) {
            Zone zone = zoneOpt.get();
            return zone.isBuildingAllowed();
        }
        
        return true;
    }
    
    public boolean canPlaceBlock(ServerPlayerEntity player, World world, BlockPos pos) {
        Optional<Zone> zoneOpt = SkyBlockZoneAPI.getZoneAt(world, pos);
        
        if (zoneOpt.isPresent()) {
            Zone zone = zoneOpt.get();
            return zone.isBuildingAllowed();
        }
        
        return true;
    }
    
    public boolean canAttackEntity(ServerPlayerEntity attacker, ServerPlayerEntity target) {
        Optional<Zone> zoneOpt = SkyBlockZoneAPI.getPlayerZone(attacker);
        
        if (zoneOpt.isPresent()) {
            Zone zone = zoneOpt.get();
            return zone.isPvpEnabled();
        }
        
        return false;
    }
    
    public boolean isProtectedZone(World world, BlockPos pos) {
        Optional<Zone> zoneOpt = SkyBlockZoneAPI.getZoneAt(world, pos);
        return zoneOpt.isPresent() && !zoneOpt.get().isBuildingAllowed();
    }
}
