package com.sujal.skyblocksandbox.client;

import com.sujal.skyblocksandbox.stats.StatType;
import com.sujal.skyblocksandbox.stats.StatsHandler;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;

public class SkyblockHud implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        PlayerEntity player = client.player;
        TextRenderer font = client.textRenderer;
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        // 1. Get Stats (Local check for visuals)
        // In a real server, you'd sync these, but for sandbox we calculate live.
        StatsHandler.recalculateStats(player); 
        
        double maxHealth = StatsHandler.getStat(player, StatType.HEALTH);
        double currentHealth = player.getHealth() * (maxHealth / player.getMaxHealth()); // Scale visual
        // Fix: If we set vanilla health to max in StatsHandler, just use current.
        if (player.getMaxHealth() > 20) currentHealth = player.getHealth(); 

        double defense = StatsHandler.getStat(player, StatType.DEFENSE);
        double maxMana = StatsHandler.getStat(player, StatType.MANA);
        double currentMana = StatsHandler.getCurrentMana(player);

        // Y Position: Just above the hotbar (Hotbar is at height - 22)
        int yPos = height - 40;

        // --- DRAW HEALTH (Left) ---
        String healthText = String.format("§c%.0f/%.0f❤", currentHealth, maxHealth);
        context.drawTextWithShadow(font, healthText, width / 2 - 91, yPos, 0xFFFFFF);

        // --- DRAW DEFENSE (Middle) ---
        if (defense > 0) {
            String defText = String.format("§a%.0f❈ Defense", defense);
            int defWidth = font.getWidth(defText);
            // Draw mostly centered, slightly shifted if needed
            context.drawTextWithShadow(font, defText, width / 2 - (defWidth / 2), yPos, 0xFFFFFF);
        }

        // --- DRAW MANA (Right) ---
        String manaText = String.format("§b%.0f/%.0f✎ Mana", currentMana, maxMana);
        int manaWidth = font.getWidth(manaText);
        // Align to the right side of the hotbar
        context.drawTextWithShadow(font, manaText, width / 2 + 91 - manaWidth, yPos, 0xFFFFFF);
    }
}
