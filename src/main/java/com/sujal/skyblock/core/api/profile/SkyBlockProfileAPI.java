package com.sujal.skyblock.core.api.profile;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;
import java.util.UUID;

public class SkyBlockProfileAPI {
    private static SkyBlockProfileProvider provider;
    
    public static void register(SkyBlockProfileProvider provider) {
        SkyBlockProfileAPI.provider = provider;
    }
    
    public static Optional<SkyBlockProfile> getProfile(ServerPlayerEntity player) {
        return provider != null ? provider.getProfile(player) : Optional.empty();
    }
    
    public static Optional<SkyBlockProfile> getProfile(UUID uuid) {
        return provider != null ? provider.getProfile(uuid) : Optional.empty();
    }
    
    public static SkyBlockProfile getOrCreateProfile(ServerPlayerEntity player) {
        if (provider == null) {
            throw new IllegalStateException("SkyBlockProfileAPI provider not registered");
        }
        return provider.getOrCreateProfile(player);
    }
    
    public static void saveProfile(ServerPlayerEntity player) {
        if (provider != null) {
            provider.saveProfile(player);
        }
    }
    
    public static void saveAllProfiles() {
        if (provider != null) {
            provider.saveAllProfiles();
        }
    }
    
    public interface SkyBlockProfileProvider {
        Optional<SkyBlockProfile> getProfile(ServerPlayerEntity player);
        Optional<SkyBlockProfile> getProfile(UUID uuid);
        SkyBlockProfile getOrCreateProfile(ServerPlayerEntity player);
        void saveProfile(ServerPlayerEntity player);
        void saveAllProfiles();
    }
}
