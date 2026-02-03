package com.sujal.skyblock.client;

import com.sujal.skyblock.core.stats.StatManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class SkyBlockHUD implements HudRenderCallback {
    
    private static final Identifier ICONS = new Identifier("skyblock", "textures/gui/icons.png");

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        PlayerEntity player = client.player;
        TextRenderer font = client.textRenderer;
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        // Stats
        double health = StatManager.INSTANCE.getHealth(player);
        double maxHealth = StatManager.INSTANCE.getMaxHealth(player);
        double defense = StatManager.INSTANCE.getDefense(player);
        double mana = StatManager.INSTANCE.getMana(player);
        double maxMana = StatManager.INSTANCE.getMaxMana(player);

        // Draw Health (Red) - Bottom Leftish
        String hpText = String.format("%.0f/%.0f❤", health, maxHealth);
        context.drawText(font, hpText, width / 2 - 90, height - 40, 0xFF5555, true);

        // Draw Defense (Green) - Next to health
        if (defense > 0) {
            String defText = String.format("%.0f❈", defense);
            context.drawText(font, defText, width / 2 - 20, height - 40, 0x55FF55, true);
        }

        // Draw Mana (Blue) - Bottom Rightish
        String manaText = String.format("%.0f/%.0f✎", mana, maxMana);
        int manaWidth = font.getWidth(manaText);
        context.drawText(font, manaText, width / 2 + 90 - manaWidth, height - 40, 0x55FFFF, true);

        // Scoreboard logic is handled by vanilla scoreboard, but we populate it via API.
    }
}
