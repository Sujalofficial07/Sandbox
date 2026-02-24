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
                ScoreboardCriterion.RenderType.INTEGER
            );
        }
        
        scoreboard.setObjectiveSlot(1, objective);
    }
    
    public void update() {
        if (objective == null) {
            initialize();
        }
        
        clear();
        
        var core = com.sujal.skyblock.core.SkyBlockCore.getInstance();
        if (core == null || core.getHudRenderer() == null) {
            return;
        }
        
        List<Text> lines = core.getHudRenderer().buildScoreboardLines(player);
        
        int score = lines.size();
        for (Text line : lines) {
            String playerName = line.getString();
            if (playerName.length() > 40) {
                playerName = playerName.substring(0, 40);
            }
            
            ScoreboardPlayerScore scoreboardScore = scoreboard.getPlayerScore(playerName, objective);
            scoreboardScore.setScore(score--);
        }
    }
    
    public void clear() {
        if (objective != null) {
            for (String playerName : scoreboard.getKnownPlayers()) {
                scoreboard.resetPlayerScore(playerName, objective);
            }
        }
    }
}
