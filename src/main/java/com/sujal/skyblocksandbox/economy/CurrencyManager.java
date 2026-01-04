package com.sujal.skyblocksandbox.economy;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CurrencyManager extends PersistentState {
    
    // Global storage for coins
    private static final Map<UUID, Double> balances = new HashMap<>();
    private static CurrencyManager INSTANCE;

    public static void init() {
        // Initialization logic if needed (e.g. loading config)
        // Usually PersistentState is loaded when the world loads
    }
    
    // Method to get state from the server
    public static CurrencyManager getServerState(MinecraftServer server) {
        // Using Overworld storage for global data
        return server.getWorld(World.OVERWORLD).getPersistentStateManager()
                .getOrCreate(
                        CurrencyManager::createFromNbt,
                        CurrencyManager::new,
                        "skyblock_economy"
                );
    }

    // --- Public API ---

    public static double getBalance(ServerPlayerEntity player) {
        return balances.getOrDefault(player.getUuid(), 0.0);
    }

    public static void addBalance(ServerPlayerEntity player, double amount) {
        UUID uuid = player.getUuid();
        balances.put(uuid, getBalance(player) + amount);
        markDirtyState(player.getServer());
    }

    public static void removeBalance(ServerPlayerEntity player, double amount) {
        UUID uuid = player.getUuid();
        double current = getBalance(player);
        balances.put(uuid, Math.max(0, current - amount));
        markDirtyState(player.getServer());
    }

    // --- Persistence Logic ---

    private static void markDirtyState(MinecraftServer server) {
        if (server != null) {
            getServerState(server).markDirty();
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound balancesTag = new NbtCompound();
        balances.forEach((uuid, amount) -> balancesTag.putDouble(uuid.toString(), amount));
        nbt.put("Balances", balancesTag);
        return nbt;
    }

    public static CurrencyManager createFromNbt(NbtCompound nbt) {
        CurrencyManager state = new CurrencyManager();
        if (nbt.contains("Balances")) {
            NbtCompound balancesTag = nbt.getCompound("Balances");
            balancesTag.getKeys().forEach(key -> {
                balances.put(UUID.fromString(key), balancesTag.getDouble(key));
            });
        }
        return state;
    }
}

