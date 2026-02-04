package com.sujal.skyblock.core.internal.hud;

import net.minecraft.scoreboard.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

public class ScoreboardRenderer {
    private final ServerPlayerEntity player;
    private Scoreboard scoreboard;
    private ScoreboardObjective objective;
    
    public ScoreboardRenderer(ServerPlayerEntity player) {
        this.player = player;
        initialize();
    }
    
    private void initialize() {
        this.scoreboard = player.getServer().getScoreboard();
        
        String objectiveName = "skyblock_sb";
        this.objective = scoreboard.getNullableObjective(objectiveName);
        
        if (this.objective == null) {
            this.objective = scoreboard.addObjective(
                objectiveName,
                ScoreboardCriterion.DUMMY,
                Text.literal("SKYBLOCK"),
                ScoreboardCriterion.RenderType.INTEGER,
                true,
                null
            );
        }
        
        scoreboard.setObjectiveSlot(ScoreboardDisplaySlot.SIDEBAR, objective);
    }
    
    public void update() {
        if (objective == null) {
            initialize();
        }
        
        clear();
        
        List<Text> lines = player.getServer() != null ? 
            player.getServer().execute(() -> {
                var core = com.sujal.skyblock.core.SkyBlockCore.getInstance();
                if (core != null && core.getHudRenderer() != null) {
                    return core.getHudRenderer().buildScoreboardLines(player);
                }
                return List.<Text>of();
            }).join() : List.of();
        
        int score = lines.size();
        for (Text line : lines) {
            ScoreboardScore scoreboardScore = scoreboard.getOrCreateScore(
                ScoreHolder.fromName(line.getString().substring(0, Math.min(40, line.getString().length()))),
                objective
            );
            scoreboardScore.setScore(score--);
        }
    }
    
    public void clear() {
        if (objective != null) {
            scoreboard.getScoreHolderObjectives(objective)
                .forEach(scoreboard::removeScore);
        }
    }
}
