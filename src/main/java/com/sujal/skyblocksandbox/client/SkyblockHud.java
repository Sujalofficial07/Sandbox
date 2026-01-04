package com.sujal.skyblocksandbox.client;

import com.sujal.skyblocksandbox.stats.StatType;
import com.sujal.skyblocksandbox.stats.StatsHandler;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class SkyblockHud implements HudRenderCallback {

    private static final Identifier ICONS = new Identifier("minecraft", "textures/gui/icons.png");

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        PlayerEntity player = client.player;
        TextRenderer font = client.textRenderer;
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        // 1. Calculate Stats (Client-side sync logic needed ideally, assuming local calculation for sandbox)
        // In a real server env, you'd sync these via packets. 
        // For this local sandbox, we call StatsHandler directly.
        StatsHandler.recalculateStats(player); 
        
        double maxHealth = StatsHandler.getStat(player, StatType.HEALTH);
        double currentHealth = player.getHealth() * (maxHealth / 20.0); // Scale vanilla health to Custom Health
        // Or if you implemented custom health logic, use that.
        
        double defense = StatsHandler.getStat(player, StatType.DEFENSE);
        double maxMana = StatsHandler.getStat(player, StatType.MANA);
        double currentMana = StatsHandler.getCurrentMana(player);

        // 2. Draw Health Bar (Red)
        // Vanilla health bar is usually hidden or overlaid. Let's draw text above hotbar.
        String healthText = String.format("§c%.0f/%.0f❤", currentHealth, maxHealth);
        int healthX = width / 2 - 91;
        int healthY = height - 39; // Just above hotbar
        
        // Draw Text Shadow
        context.drawTextWithShadow(font, healthText, healthX, healthY, 0xFFFFFF);

        // 3. Draw Defense (Green)
        String defText = String.format("§a%.0f❈ Defense", defense);
        int defX = width / 2 + 10; // Slightly right or middle
        // Center the defense text relative to health and mana if possible, or put strictly right
        // Hypixel puts defense in the middle of the health and mana bars
        
        // 4. Draw Mana Bar (Blue)
        String manaText = String.format("§b%.0f/%.0f✎ Mana", currentMana, maxMana);
        int manaWidth = font.getWidth(manaText);
        int manaX = width / 2 + 91 - manaWidth;
        
        context.drawTextWithShadow(font, manaText, manaX, healthY, 0xFFFFFF);
        
        // Drawing Defense in middle of those two
        int defWidth = font.getWidth(defText);
        context.drawTextWithShadow(font, defText, width / 2 - defWidth / 2, healthY, 0xFFFFFF);
        
        // Optional: Cancel Vanilla Health/Food rendering by returning specific events
        // But for now, we just overlay the text info.
    }
}
