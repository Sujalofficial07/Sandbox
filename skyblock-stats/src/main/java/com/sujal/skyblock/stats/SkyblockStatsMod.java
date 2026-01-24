package com.sujal.skyblock.stats;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockStatsMod implements ModInitializer {
    public static final String MOD_ID = "skyblock-stats";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Skyblock Stats Engine Loaded");
        StatSyncHandler.register();
    }
}
