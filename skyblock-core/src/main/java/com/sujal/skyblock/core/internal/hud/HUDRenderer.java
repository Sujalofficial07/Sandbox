package com.sujal.skyblock.core.internal.hud;

import com.sujal.skyblock.core.api.profile.SkyBlockProfile;
import com.sujal.skyblock.core.api.profile.SkyBlockProfileAPI;
import com.sujal.skyblock.core.api.stats.SkyBlockStats;
import com.sujal.skyblock.core.api.stats.SkyBlockStatsAPI;
import com.sujal.skyblock.core.api.util.TextUtils;
import com.sujal.skyblock.core.api.zone.SkyBlockZoneAPI;
import com.sujal.skyblock.core.api.zone.Zone;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HUDRenderer {
    private final Map<UUID, ScoreboardRenderer> scoreboardRenderers;
    private final Map<UUID, ActionBarRenderer> actionBarRenderers;
    
    public HUDRenderer() {
        this.scoreboardRenderers = new ConcurrentHashMap<>();
        this.actionBarRenderers = new ConcurrentHashMap<>();
    }
    
    public void initializePlayer(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        scoreboardRenderers.put(uuid, new ScoreboardRenderer(player));
        actionBarRenderers.put(uuid, new ActionBarRenderer(player));
    }
    
    public void removePlayer(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        ScoreboardRenderer scoreboard = scoreboardRenderers.remove(uuid);
        if (scoreboard != null) {
            scoreboard.clear();
        }
        actionBarRenderers.remove(uuid);
    }
    
    public void updateAll(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            update(player);
        }
    }
    
    public void update(ServerPlayerEntity player) {
        ScoreboardRenderer scoreboard = scoreboardRenderers.get(player.getUuid());
        if (scoreboard != null) {
            scoreboard.update();
        }
        
        ActionBarRenderer actionBar = actionBarRenderers.get(player.getUuid());
        if (actionBar != null) {
            actionBar.update();
        }
    }
    
    public List<Text> buildScoreboardLines(ServerPlayerEntity player) {
        List<Text> lines = new ArrayList<>();
        
        Optional<SkyBlockProfile> profileOpt = SkyBlockProfileAPI.getProfile(player);
        Optional<SkyBlockStats> statsOpt = SkyBlockStatsAPI.getStats(player);
        
        if (profileOpt.isEmpty() || statsOpt.isEmpty()) {
            return lines;
        }
        
        SkyBlockProfile profile = profileOpt.get();
        SkyBlockStats stats = statsOpt.get();
        
        lines.add(TextUtils.parseColorCodes("&6&lSKYBLOCK"));
        lines.add(Text.empty());
        
        Optional<Zone> zoneOpt = SkyBlockZoneAPI.getPlayerZone(player);
        if (zoneOpt.isPresent()) {
            lines.add(TextUtils.parseColorCodes("&7" + zoneOpt.get().getDisplayName()));
        } else {
            lines.add(TextUtils.parseColorCodes("&7Unknown"));
        }
        
        lines.add(Text.empty());
        
        lines.add(TextUtils.parseColorCodes("&aProfile: &7" + profile.getProfileName()));
        lines.add(Text.empty());
        
        lines.add(TextUtils.parseColorCodes("&6Purse: &7" + TextUtils.formatNumber(profile.getCoins()) + " coins"));
        lines.add(TextUtils.parseColorCodes("&6Bank: &7" + TextUtils.formatNumber(profile.getBankCoins()) + " coins"));
        lines.add(Text.empty());
        
        lines.add(TextUtils.parseColorCodes("&cHealth: &7" + 
            String.format("%.0f/%.0f", stats.getHealth(), stats.getMaxHealth()) + " ❤"));
        lines.add(TextUtils.parseColorCodes("&bMana: &7" + 
            String.format("%.0f/%.0f", stats.getMana(), stats.getMaxMana()) + " ✎"));
        lines.add(Text.empty());
        
        lines.add(TextUtils.parseColorCodes("&7SkyBlock Level: &b" + profile.getSkyBlockLevel()));
        lines.add(Text.empty());
        
        lines.add(TextUtils.parseColorCodes("&ewww.hypixel.net"));
        
        return lines;
    }
          }
