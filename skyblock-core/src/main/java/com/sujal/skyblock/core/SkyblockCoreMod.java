package com.sujal.skyblock.core;

import com.sujal.skyblock.core.commands.SkyblockAdminCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockCoreMod implements ModInitializer {
    public static final String MOD_ID = "skyblock-core";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[Skyblock-Core] Initializing Core Systems...");

        // Register Admin Commands (/sb coins, /sb profile)
        CommandRegistrationCallback.EVENT.register(SkyblockAdminCommand::register);
        
        LOGGER.info("[Skyblock-Core] Initialization Complete.");
    }
}
