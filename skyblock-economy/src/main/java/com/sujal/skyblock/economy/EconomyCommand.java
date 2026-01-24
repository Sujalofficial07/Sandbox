package com.sujal.skyblock.economy;

import com.sujal.skyblock.ui.menu.BankMenu;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

public class EconomyCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            dispatcher.register(CommandManager.literal("sb")
                .then(CommandManager.literal("bank")
                    .executes(ctx -> {
                        BankMenu.open(ctx.getSource().getPlayerOrThrow());
                        return 1;
                    }))
            );
        });
    }
}
