package com.sujal.skyblockstats.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class SkyblockStatsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Run Scoreboard logic every tick on client
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null && client.player != null) {
                ScoreboardHandler.updateScoreboard();
            }
        });
    }
}
