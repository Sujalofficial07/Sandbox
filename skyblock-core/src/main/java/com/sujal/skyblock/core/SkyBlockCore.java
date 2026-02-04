package com.sujal.skyblock.core;

import com.sujal.skyblock.core.api.events.SkyBlockEventAPI;
import com.sujal.skyblock.core.api.profile.SkyBlockProfileAPI;
import com.sujal.skyblock.core.api.stats.SkyBlockStatsAPI;
import com.sujal.skyblock.core.api.zone.SkyBlockZoneAPI;
import com.sujal.skyblock.core.config.CoreConfig;
import com.sujal.skyblock.core.internal.events.EventBus;
import com.sujal.skyblock.core.internal.hud.HUDRenderer;
import com.sujal.skyblock.core.internal.profile.ProfileManager;
import com.sujal.skyblock.core.internal.protection.WorldProtectionManager;
import com.sujal.skyblock.core.internal.stats.StatsManager;
import com.sujal.skyblock.core.internal.zone.ZoneManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyBlockCore implements ModInitializer {
    public static final String MOD_ID = "skyblock-core";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static SkyBlockCore instance;
    
    private ProfileManager profileManager;
    private StatsManager statsManager;
    private ZoneManager zoneManager;
    private EventBus eventBus;
    private HUDRenderer hudRenderer;
    private WorldProtectionManager protectionManager;
    private CoreConfig config;
    
    @Override
    public void onInitialize() {
        instance = this;
        LOGGER.info("Initializing SkyBlock Core...");
        
        this.config = CoreConfig.load();
        this.eventBus = new EventBus();
        this.profileManager = new ProfileManager(config.getDataDirectory());
        this.statsManager = new StatsManager();
        this.zoneManager = new ZoneManager();
        this.hudRenderer = new HUDRenderer();
        this.protectionManager = new WorldProtectionManager();
        
        registerAPIs();
        registerEvents();
        
        LOGGER.info("SkyBlock Core initialized successfully!");
    }
    
    private void registerAPIs() {
        SkyBlockProfileAPI.register(profileManager);
        SkyBlockStatsAPI.register(statsManager);
        SkyBlockZoneAPI.register(zoneManager);
        SkyBlockEventAPI.register(eventBus);
    }
    
    private void registerEvents() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            profileManager.loadProfile(handler.player);
            hudRenderer.initializePlayer(handler.player);
        });
        
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            profileManager.saveProfile(handler.player);
            hudRenderer.removePlayer(handler.player);
        });
        
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            LOGGER.info("Saving all SkyBlock profiles...");
            profileManager.saveAllProfiles();
        });
        
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            eventBus.fireServerTick(server);
            hudRenderer.updateAll(server);
        });
    }
    
    public static SkyBlockCore getInstance() {
        return instance;
    }
    
    public ProfileManager getProfileManager() {
        return profileManager;
    }
    
    public StatsManager getStatsManager() {
        return statsManager;
    }
    
    public ZoneManager getZoneManager() {
        return zoneManager;
    }
    
    public EventBus getEventBus() {
        return eventBus;
    }
    
    public HUDRenderer getHudRenderer() {
        return hudRenderer;
    }
    
    public WorldProtectionManager getProtectionManager() {
        return protectionManager;
    }
    
    public CoreConfig getConfig() {
        return config;
    }
}
