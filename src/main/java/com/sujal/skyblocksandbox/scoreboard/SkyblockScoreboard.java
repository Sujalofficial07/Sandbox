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
        // Initial setup handled in update loop for simplicity in this prototype, 
        // but typically you set the objective once here.
        updateScoreboard(player);
    }

    public static void updateScoreboard(ServerPlayerEntity player) {
        Scoreboard scoreboard = player.getScoreboard();
        ScoreboardObjective objective = scoreboard.getObjective(OBJECTIVE_NAME);

        if (objective == null) {
            objective = scoreboard.addObjective(
                    OBJECTIVE_NAME,
                    ScoreboardCriterion.DUMMY,
                    Text.literal("SKYBLOCK").formatted(Formatting.YELLOW, Formatting.BOLD),
                    ScoreboardCriterion.RenderType.INTEGER
            );
        }
        
        // Ensure it's displayed in the sidebar (slot 1)
        scoreboard.setObjectiveSlot(1, objective);

        // Clear old scores (naive approach for prototype - better to update existing scores using team prefix/suffix for flicker-free experience)
        // For this foundation, we will just set scores directly. 
        // Note: In a real advanced mod, use Packets or Team Suffixes to prevent flickering.
        
        // Using a simple method to just push lines. 
        // 15 lines max.
        
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yy"));
        double coins = CurrencyManager.getBalance(player);
        
        setScore(scoreboard, objective, "§7" + dateStr + " §8m120D", 10);
        setScore(scoreboard, objective, "§1", 9); // Blank
        setScore(scoreboard, objective, "§fRank: §aDefault", 8);
        setScore(scoreboard, objective, "§2", 7); // Blank
        setScore(scoreboard, objective, "§fCoins: §6" + (int)coins, 6);
        setScore(scoreboard, objective, "§3", 5); // Blank
        setScore(scoreboard, objective, "§fLocation:", 4);
        setScore(scoreboard, objective, "§7Hub", 3); // Placeholder
        setScore(scoreboard, objective, "§4", 2); // Blank
        setScore(scoreboard, objective, "§ehyperion.net", 1);
    }
    
    private static void setScore(Scoreboard sb, ScoreboardObjective obj, String text, int scoreVal) {
        ScoreboardPlayerScore score = sb.getPlayerScore(text, obj);
        score.setScore(scoreVal);
    }
}
