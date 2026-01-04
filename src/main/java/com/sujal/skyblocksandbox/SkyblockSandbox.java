package com.sujal.skyblocksandbox;

import com.sujal.skyblocksandbox.registry.SkyblockRegistries;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockSandbox implements ModInitializer {
    public static final String MOD_ID = "skyblocksandbox";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing SkyblockSandbox Mod [Phase 2]...");
        
        // Delegate all registration to a dedicated registry class
        SkyblockRegistries.initialize();
    }
}
