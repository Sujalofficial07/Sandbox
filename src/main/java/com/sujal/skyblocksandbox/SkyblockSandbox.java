package com.sujal.skyblocksandbox;

import com.sujal.skyblocksandbox.economy.CurrencyManager;
import com.sujal.skyblocksandbox.scoreboard.SkyblockScoreboard;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockSandbox implements ModInitializer {
    public static final String MOD_ID = "skyblocksandbox";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing SkyblockSandbox Mod...");

        // Initialize Systems
        CurrencyManager.init();

        // Events
        registerEvents();
    }

    private void registerEvents() {
        // Update Scoreboard every second (20 ticks)
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTicks() % 20 == 0) {
                server.getPlayerManager().getPlayerList().forEach(SkyblockScoreboard::updateScoreboard);
            }
        });

        // Setup Scoreboard when player joins
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            SkyblockScoreboard.initPlayer(handler.getPlayer());
        });
    }
}
