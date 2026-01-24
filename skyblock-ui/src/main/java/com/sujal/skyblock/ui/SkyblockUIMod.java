package com.sujal.skyblock.ui;

import com.sujal.skyblock.ui.hud.ScoreboardRenderer;
import com.sujal.skyblock.ui.menu.BankMenu;
import com.sujal.skyblock.ui.menu.ProfileMenu;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

public class SkyblockUIMod implements ModInitializer, ClientModInitializer {
    
    // Server-side Logic (Commands)
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            dispatcher.register(CommandManager.literal("sb")
                // /sb menu OR /sb profile
                .then(CommandManager.literal("menu")
                    .executes(ctx -> {
                        ProfileMenu.open(ctx.getSource().getPlayerOrThrow());
                        return 1;
                    }))
                .then(CommandManager.literal("profile")
                    .executes(ctx -> {
                        ProfileMenu.open(ctx.getSource().getPlayerOrThrow());
                        return 1;
                    }))
                // /sb bank (Moved here from Economy module)
                .then(CommandManager.literal("bank")
                    .executes(ctx -> {
                        BankMenu.open(ctx.getSource().getPlayerOrThrow());
                        return 1;
                    }))
            );
        });
    }

    // Client-side Logic (Renderer)
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new ScoreboardRenderer());
    }
}
