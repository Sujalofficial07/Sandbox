package com.sujal.skyblocksandbox.registry;

import com.sujal.skyblocksandbox.SkyblockSandbox;
import com.sujal.skyblocksandbox.ability.AbilityListener; // Import this
import com.sujal.skyblocksandbox.command.SBItemCommand;
import com.sujal.skyblocksandbox.economy.CurrencyManager;
import com.sujal.skyblocksandbox.scoreboard.SkyblockScoreboard;
import com.sujal.skyblocksandbox.stats.StatsHandler;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class SkyblockRegistries {

    public static void initialize() {
        initSystems();
        registerCommands();
        registerEvents();
        SkyblockSandbox.LOGGER.info("Registries initialized successfully.");
    }

    private static void initSystems() {
        CurrencyManager.init();
    }
    
    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            SBItemCommand.register(dispatcher);
        });
    }

    private static void registerEvents() {
        // --- NEW: Register Ability Listener ---
        AbilityListener.register();
        // --------------------------------------

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTicks() % 20 == 0) {
                server.getPlayerManager().getPlayerList().forEach(player -> {
                    SkyblockScoreboard.updateScoreboard(player);
                    StatsHandler.regenerateMana(player);
                });
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            SkyblockScoreboard.initPlayer(handler.getPlayer());
            StatsHandler.recalculateStats(handler.getPlayer());
        });
    }
}
