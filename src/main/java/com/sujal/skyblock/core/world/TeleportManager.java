package com.sujal.skyblock.core.world;

import com.sujal.skyblock.api.SkyBlockTeleportAPI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class TeleportManager implements SkyBlockTeleportAPI {
    
    public static final TeleportManager INSTANCE = new TeleportManager();
    private final Map<Identifier, TeleportDestination> locations = new HashMap<>();

    public TeleportManager() {
        // Default Static Locations
        locations.put(new Identifier("skyblock", "hub"), new TeleportDestination(World.OVERWORLD, new BlockPos(0, 70, 0)));
        // Add other dimensions/locations here
    }

    @Override
    public void teleport(PlayerEntity player, Identifier locationId) {
        if (player.getWorld().isClient) return;
        
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        
        if (locationId.getPath().equals("island")) {
            // Handle dynamic private island logic
            // In a real impl, this gets the UUID-based dimension
            return;
        }

        TeleportDestination dest = locations.get(locationId);
        if (dest != null) {
            ServerWorld world = serverPlayer.getServer().getWorld(dest.dimension);
            if (world != null) {
                serverPlayer.teleport(world, dest.pos.getX(), dest.pos.getY(), dest.pos.getZ(), 0, 0);
            }
        }
    }

    @Override
    public void registerLocation(Identifier id, BlockPos pos, net.minecraft.registry.RegistryKey<World> dim) {
        locations.put(id, new TeleportDestination(dim, pos));
    }

    private record TeleportDestination(net.minecraft.registry.RegistryKey<World> dimension, BlockPos pos) {}
}
