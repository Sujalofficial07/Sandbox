package com.sujal.skyblocksandbox.scoreboard;

import com.sujal.skyblocksandbox.economy.CurrencyManager;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SkyblockScoreboard {

    private static final String OBJECTIVE_NAME = "SB_Sidebar";

    public static void initPlayer(ServerPlayerEntity player) {
        // Force a clear on join to ensure clean state
        Scoreboard scoreboard = player.getScoreboard();
        if (scoreboard.getObjective(OBJECTIVE_NAME) != null) {
            scoreboard.removeObjective(scoreboard.getObjective(OBJECTIVE_NAME));
        }
        updateScoreboard(player);
    }

    public static void updateScoreboard(ServerPlayerEntity player) {
        Scoreboard scoreboard = player.getScoreboard();
        ScoreboardObjective objective = scoreboard.getObjective(OBJECTIVE_NAME);

        // Create if missing
        if (objective == null) {
            objective = scoreboard.addObjective(
                    OBJECTIVE_NAME,
                    ScoreboardCriterion.DUMMY,
                    Text.literal("SKYBLOCK").formatted(Formatting.YELLOW, Formatting.BOLD),
                    ScoreboardCriterion.RenderType.INTEGER
            );
            scoreboard.setObjectiveSlot(1, objective); // Slot 1 = Sidebar
        }
        
        // Data Calculation
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yy"));
        double coins = CurrencyManager.getBalance(player);
        String coinsFormatted = String.format("%.1f", coins); // Clean number format

        // --- SET SCORES ---
        // We use specific scores (15 down to 1) to keep order.
        // To prevent flickering, usually we use Teams, but for Sandbox 
        // we will just overwrite. The flicker comes from removing the objective.
        // DO NOT remove the objective here. Just set scores.

        setLine(scoreboard, objective, "§7" + dateStr + " §8m120D", 12);
        setLine(scoreboard, objective, "§1", 11); // Spacer
        setLine(scoreboard, objective, "§fRank: §aDefault", 10); // Dynamic Rank later
        setLine(scoreboard, objective, "§2", 9); // Spacer
        setLine(scoreboard, objective, "§fCoins: §6" + coinsFormatted, 8);
        setLine(scoreboard, objective, "§3", 7); // Spacer
        setLine(scoreboard, objective, "§fLocation:", 6);
        setLine(scoreboard, objective, "§7High Level", 5);
        setLine(scoreboard, objective, "§4", 4); // Spacer
        setLine(scoreboard, objective, "§ehyperion.net", 3);
    }
    
    private static void setLine(Scoreboard sb, ScoreboardObjective obj, String text, int score) {
        // In vanilla 1.20, duplicate scores override. 
        // Ideally, we clean up old scores, but for this simpler setup:
        ScoreboardPlayerScore playerScore = sb.getPlayerScore(text, obj);
        playerScore.setScore(score);
    }
}
