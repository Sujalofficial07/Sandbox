package com.sujal.skyblock.ui.hud;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.core.util.SkyblockTime;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardRenderer implements HudRenderCallback {

    private static final int COLOR_GOLD = 0xFFAA00;
    private static final int COLOR_WHITE = 0xFFFFFF;
    private static final int COLOR_GRAY = 0xAAAAAA;
    private static final int COLOR_GREEN = 0x55FF55;
    private static final int COLOR_YELLOW = 0xFFFF55;

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;
        
        // Don't render in Debug screen (F3)
        if (client.options.debugEnabled) return;

        renderScoreboard(context, client);
    }

    private void renderScoreboard(DrawContext context, MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        
        // 1. Fetch Data
        // Note: In singleplayer, we can access ProfileManager via the IntegratedServer.
        // In a real multiplayer client, we would need Packet Syncing. 
        // Assuming SinglePlayer for now as per constraints:
        if (client.getServer() == null) return;
        
        var pm = ProfileManager.getServerInstance(client.getServer().getOverworld());
        SkyblockProfile profile = pm.getProfile(player);

        String date = SkyblockTime.getDate(client.world);
        String time = SkyblockTime.getTime(client.world);
        String coins = SkyblockTime.formatCoins(profile.getCoins());
        
        // Get Biome Name (Location)
        String location = "Village"; // Default
        var biomeEntry = client.world.getBiome(player.getBlockPos());
        if (biomeEntry.getKey().isPresent()) {
            String biomeId = biomeEntry.getKey().get().getValue().getPath();
            location = formatBiomeName(biomeId);
        }

        // 2. Build Lines
        List<String> lines = new ArrayList<>();
        lines.add("§7" + date);
        lines.add("§7" + time);
        lines.add("§7⏣ " + location);
        lines.add(""); // Spacer
        lines.add("§fPurse: §6" + coins);
        lines.add("§fBits: §b0"); // Placeholder
        lines.add(""); // Spacer
        lines.add("§eall items"); // Your custom footer or 'www.hypixel.net'

        // 3. Render Setup
        String title = "§e§lSKYBLOCK";
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        
        int sidebarWidth = client.textRenderer.getWidth(title) + 20; // Min width
        for (String line : lines) {
            sidebarWidth = Math.max(sidebarWidth, client.textRenderer.getWidth(line) + 10);
        }

        int x = screenWidth - sidebarWidth;
        int y = screenHeight / 2 - (lines.size() * 10) / 2; // Center Vertically
        
        // Background Color (Semi-transparent black like Hypixel)
        int backgroundColor = 0x40000000; // Low opacity
        // Or specific scoreboard background logic:
        // context.fill(x, y - 12, screenWidth, y + lines.size() * 9 + 4, backgroundColor);

        // 4. Draw Title
        int titleWidth = client.textRenderer.getWidth(title);
        context.drawText(client.textRenderer, Text.of(title), x + (sidebarWidth - titleWidth) / 2, y - 10, COLOR_WHITE, true);

        // 5. Draw Lines
        for (int i = 0; i < lines.size(); i++) {
            context.drawText(client.textRenderer, Text.of(lines.get(i)), x + 4, y + (i * 10), COLOR_WHITE, true);
        }
    }

    private String formatBiomeName(String id) {
        String[] parts = id.split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
