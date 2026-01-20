package com.sujal.skyblock.core;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockCoreMod implements ModInitializer {
    public static final String MOD_ID = "skyblock-core";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Skyblock Core Initialized - Database Systems Ready");
    }
}
