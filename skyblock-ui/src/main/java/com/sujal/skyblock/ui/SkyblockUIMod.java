package com.sujal.skyblock.ui;

import com.sujal.skyblock.ui.hud.ScoreboardRenderer;
import com.sujal.skyblock.ui.menu.ProfileMenu;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

public class SkyblockUIMod implements ModInitializer, ClientModInitializer {
    
    // Server-side Logic
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            dispatcher.register(CommandManager.literal("sbmenu")
                .executes(ctx -> {
                    ProfileMenu.open(ctx.getSource().getPlayerOrThrow());
                    return 1;
                }));
        });
    }

    // Client-side Logic (Renderer)
    @Override
    public void onInitializeClient() {
        // Register the Scoreboard
        HudRenderCallback.EVENT.register(new ScoreboardRenderer());
    }
}
