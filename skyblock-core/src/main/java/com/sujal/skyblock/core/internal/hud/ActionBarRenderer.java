package com.sujal.skyblock.core.internal.hud;

import com.sujal.skyblock.core.api.stats.SkyBlockStats;
import com.sujal.skyblock.core.api.stats.SkyBlockStatsAPI;
import com.sujal.skyblock.core.api.util.TextUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Optional;

public class ActionBarRenderer {
    private final ServerPlayerEntity player;
    private int tickCounter = 0;
    
    public ActionBarRenderer(ServerPlayerEntity player) {
        this.player = player;
    }
    
    public void update() {
        tickCounter++;
        
        if (tickCounter % 10 != 0) {
            return;
        }
        
        Optional<SkyBlockStats> statsOpt = SkyBlockStatsAPI.getStats(player);
        if (statsOpt.isEmpty()) {
            return;
        }
        
        SkyBlockStats stats = statsOpt.get();
        
        String healthBar = buildBar(stats.getHealth(), stats.getMaxHealth(), "&c", "❤");
        String manaBar = buildBar(stats.getMana(), stats.getMaxMana(), "&b", "✎");
        
        Text actionBarText = TextUtils.parseColorCodes(healthBar + "     " + manaBar);
        player.sendMessage(actionBarText, true);
    }
    
    private String buildBar(double current, double max, String color, String symbol) {
        int percentage = (int) ((current / max) * 100);
        int filledBars = percentage / 10;
        
        StringBuilder bar = new StringBuilder(color);
        bar.append(String.format("%.0f", current)).append("/").append(String.format("%.0f", max));
        bar.append(" ").append(symbol).append(" ");
        
        for (int i = 0; i < 10; i++) {
            if (i < filledBars) {
                bar.append("█");
            } else {
                bar.append("&7█");
            }
        }
        
        return bar.toString();
    }
}
