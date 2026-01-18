package com.sujal.skyblockstats.client;

import com.sujal.skyblockstats.api.SkyBlockAPI;
import com.sujal.skyblockstats.stats.SkyBlockStatType;
import com.sujal.skyblockstats.utils.TimeUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
// Removed ScoreboardDisplaySlot import as it doesn't exist in 1.20.1
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardHandler {

    private static int tickCounter = 0;

    public static void updateScoreboard() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        tickCounter++;
        if (tickCounter % 5 != 0) return;

        Scoreboard scoreboard = client.world.getScoreboard();
        ScoreboardObjective objective = scoreboard.getObjective("SB_Stats");

        if (objective == null) {
            objective = scoreboard.addObjective("SB_Stats", ScoreboardCriterion.DUMMY, 
                Text.literal("§e§lSKYBLOCK"), ScoreboardCriterion.RenderType.INTEGER);
        }

        // FIX: Use integer constants for 1.20.1 instead of ScoreboardDisplaySlot enum
        // Scoreboard.SIDEBAR_DISPLAY_SLOT_ID is usually 1
        scoreboard.setObjectiveSlot(Scoreboard.SIDEBAR_DISPLAY_SLOT_ID, objective);

        IntegratedServer server = client.getServer();
        if (server == null) return;
        
        var serverPlayer = server.getPlayerManager().getPlayer(client.player.getUuid());
        if (serverPlayer == null) return;

        long worldTime = client.world.getTime();
        double currentHealth = serverPlayer.getHealth();
        double maxHealth = serverPlayer.getMaxHealth();
        double defense = SkyBlockAPI.getStatValue(serverPlayer, SkyBlockStatType.DEFENSE);
        double coins = SkyBlockAPI.getCoins(serverPlayer);
        
        List<String> lines = new ArrayList<>();
        lines.add("§7" + TimeUtils.getSkyBlockDate(worldTime));
        lines.add("§8----------------");
        lines.add("§f" + serverPlayer.getName().getString());
        lines.add("§fHealth: §c" + (int)currentHealth + "/" + (int)maxHealth + "❤");
        lines.add("§fDefense: §a" + (int)defense + "❈");
        lines.add("§fMana: §b" + (int)SkyBlockAPI.getStats(serverPlayer).getStatValue(SkyBlockStatType.MANA) + "✎");
        lines.add(" ");
        lines.add("§fCoins: §6" + (long)coins);
        lines.add(" ");
        lines.add("§e§lObjective");
        lines.add("§fExplore World");
        lines.add("§8----------------");
        lines.add("§ewww.hypixel.net"); 

        for (String entry : scoreboard.getKnownPlayers()) {
            scoreboard.resetPlayerScore(entry, objective);
        }

        int scoreIndex = 15;
        for (String line : lines) {
            scoreboard.getPlayerScore(line, objective).setScore(scoreIndex--);
        }
    }
}
