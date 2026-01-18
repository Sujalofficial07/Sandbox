package com.sujal.skyblockstats.client;

import com.sujal.skyblockstats.api.SkyBlockAPI;
import com.sujal.skyblockstats.stats.SkyBlockStatType;
import com.sujal.skyblockstats.utils.TimeUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardHandler {

    private static int tickCounter = 0;

    public static void updateScoreboard() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        // Optimization: Update every 5 ticks (not every single frame) to save FPS
        tickCounter++;
        if (tickCounter % 5 != 0) return;

        Scoreboard scoreboard = client.world.getScoreboard();
        ScoreboardObjective objective = scoreboard.getObjective("SB_Stats");

        // Create Objective if missing
        if (objective == null) {
            objective = scoreboard.addObjective("SB_Stats", ScoreboardCriterion.DUMMY, 
                Text.literal("§e§lSKYBLOCK"), ScoreboardCriterion.RenderType.INTEGER);
        }

        scoreboard.setObjectiveSlot(ScoreboardDisplaySlot.SIDEBAR, objective);

        // Fetch Data DIRECTLY from Server Player (Singleplayer only trick)
        // This avoids needing Packet Syncing for Coins/Mana
        IntegratedServer server = client.getServer();
        if (server == null) return; // Safety check
        
        var serverPlayer = server.getPlayerManager().getPlayer(client.player.getUuid());
        if (serverPlayer == null) return;

        // Gather Values
        long worldTime = client.world.getTime();
        double currentHealth = serverPlayer.getHealth();
        double maxHealth = serverPlayer.getMaxHealth();
        double defense = SkyBlockAPI.getStatValue(serverPlayer, SkyBlockStatType.DEFENSE);
        double coins = SkyBlockAPI.getCoins(serverPlayer);
        
        // Lines List (Top to Bottom)
        List<String> lines = new ArrayList<>();
        lines.add("§7" + TimeUtils.getSkyBlockDate(worldTime)); // Date
        lines.add("§8----------------");  // Spacer
        lines.add("§f" + serverPlayer.getName().getString()); // Player Name
        lines.add("§fHealth: §c" + (int)currentHealth + "/" + (int)maxHealth + "❤");
        lines.add("§fDefense: §a" + (int)defense + "❈");
        lines.add("§fMana: §b" + (int)SkyBlockAPI.getStats(serverPlayer).getStatValue(SkyBlockStatType.MANA) + "✎"); // Need Current Mana logic later
        lines.add(" "); // Empty Line
        lines.add("§fCoins: §6" + (long)coins);
        lines.add(" ");
        lines.add("§e§lObjective");
        lines.add("§fExplore World");
        lines.add("§8----------------");
        lines.add("§ewww.hypixel.net"); 

        // Apply to Scoreboard (Hack method: Clear and Rewrite)
        // For a perfect mod, use Teams/Prefixes, but this is standard for basic engines.
        for (String entry : scoreboard.getKnownPlayers()) {
            scoreboard.resetPlayerScore(entry, objective);
        }

        int scoreIndex = 15;
        for (String line : lines) {
            scoreboard.getPlayerScore(line, objective).setScore(scoreIndex--);
        }
    }
}
