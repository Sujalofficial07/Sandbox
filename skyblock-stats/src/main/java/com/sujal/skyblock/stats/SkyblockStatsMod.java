package com.sujal.skyblock.stats;

import com.sujal.skyblock.stats.engine.StatSyncHandler; // <--- YEH MISSING THA
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockStatsMod implements ModInitializer {
    public static final String MOD_ID = "skyblock-stats";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Skyblock Stats Engine Loaded");
        
        // Register the Tick Handler to sync stats
        StatSyncHandler.register();
    }
}
