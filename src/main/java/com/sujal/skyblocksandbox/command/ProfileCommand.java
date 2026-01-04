package com.sujal.skyblocksandbox.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.sujal.skyblocksandbox.gui.SkyblockMenus;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ProfileCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("sbmenu")
                .executes(context -> {
                    SkyblockMenus.openMainMenu(context.getSource().getPlayerOrThrow());
                    return Command.SINGLE_SUCCESS;
                }));
                
        dispatcher.register(CommandManager.literal("profile")
                .executes(context -> {
                    SkyblockMenus.openStatsMenu(context.getSource().getPlayerOrThrow());
                    return Command.SINGLE_SUCCESS;
                }));
    }
}
