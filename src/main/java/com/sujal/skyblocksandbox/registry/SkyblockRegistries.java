package com.sujal.skyblocksandbox.registry;

import com.sujal.skyblocksandbox.SkyblockSandbox;
import com.sujal.skyblocksandbox.economy.CurrencyManager;
import com.sujal.skyblocksandbox.scoreboard.SkyblockScoreboard;
import com.sujal.skyblocksandbox.stats.StatsHandler;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

/**
 * Handles initialization and event registration to keep the Main class clean.
 */
public class SkyblockRegistries {

    public static void initialize() {
        initSystems();
        registerEvents();
        SkyblockSandbox.LOGGER.info("Registries initialized successfully.");
    }

    private static void initSystems() {
        // Initialize Economy/Persistence Systems
        CurrencyManager.init();
    }

    private static void registerEvents() {
        // 1. Server Tick Event (Runs 20 times a second)
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            // Logic running every second (20 ticks)
            if (server.getTicks() % 20 == 0) {
                server.getPlayerManager().getPlayerList().forEach(player -> {
                    // Update Scoreboard
                    SkyblockScoreboard.updateScoreboard(player);

                    // Regenerate Mana
                    StatsHandler.regenerateMana(player);
                });
            }
        });

        // 2. Player Join Event
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            SkyblockSandbox.LOGGER.info("Player joined: Initializing SB data.");
            SkyblockScoreboard.initPlayer(handler.getPlayer());
            StatsHandler.recalculateStats(handler.getPlayer());
        });
    }
}
