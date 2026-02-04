package com.sujal.skyblock.core.internal.profile;

import com.sujal.skyblock.core.api.events.PlayerProfileLoadEvent;
import com.sujal.skyblock.core.api.events.SkyBlockEventAPI;
import com.sujal.skyblock.core.api.profile.SkyBlockProfile;
import com.sujal.skyblock.core.api.profile.SkyBlockProfileAPI;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ProfileManager implements SkyBlockProfileAPI.SkyBlockProfileProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileManager.class);
    
    private final Map<UUID, ProfileImpl> profiles;
    private final ProfileStorage storage;
    
    public ProfileManager(File dataDirectory) {
        this.profiles = new ConcurrentHashMap<>();
        this.storage = new ProfileStorage(dataDirectory);
    }
    
    @Override
    public Optional<SkyBlockProfile> getProfile(ServerPlayerEntity player) {
        return Optional.ofNullable(profiles.get(player.getUuid()));
    }
    
    @Override
    public Optional<SkyBlockProfile> getProfile(UUID uuid) {
        return Optional.ofNullable(profiles.get(uuid));
    }
    
    @Override
    public SkyBlockProfile getOrCreateProfile(ServerPlayerEntity player) {
        return profiles.computeIfAbsent(player.getUuid(), uuid -> {
            ProfileImpl profile = new ProfileImpl(player);
            return profile;
        });
    }
    
    public void loadProfile(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        
        ProfileImpl profile = profiles.computeIfAbsent(uuid, id -> new ProfileImpl(player));
        
        boolean isFirstJoin = storage.loadProfile(profile);
        
        if (isFirstJoin) {
            LOGGER.info("Created new SkyBlock profile for player: {}", player.getName().getString());
            profile.setCoins(0);
            profile.setBankCoins(0);
            profile.setSkyBlockLevel(1);
        } else {
            LOGGER.info("Loaded existing SkyBlock profile for player: {}", player.getName().getString());
        }
        
        SkyBlockEventAPI.fireEvent(new PlayerProfileLoadEvent(player, profile, isFirstJoin));
    }
    
    @Override
    public void saveProfile(ServerPlayerEntity player) {
        ProfileImpl profile = profiles.get(player.getUuid());
        if (profile != null) {
            profile.updateLastSeen();
            storage.saveProfile(profile);
            LOGGER.debug("Saved profile for player: {}", player.getName().getString());
        }
    }
    
    @Override
    public void saveAllProfiles() {
        LOGGER.info("Saving {} player profiles...", profiles.size());
        profiles.values().forEach(profile -> {
            profile.updateLastSeen();
            storage.saveProfile(profile);
        });
        LOGGER.info("All profiles saved successfully!");
    }
    
    public void unloadProfile(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        ProfileImpl profile = profiles.remove(uuid);
        if (profile != null) {
            profile.updateLastSeen();
            storage.saveProfile(profile);
            LOGGER.info("Unloaded profile for player: {}", player.getName().getString());
        }
    }
}
