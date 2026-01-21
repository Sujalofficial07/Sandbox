package com.sujal.skyblock.core.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public class SkyblockAdminCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("sb")
            .requires(source -> source.hasPermissionLevel(2)) // OP only
            .then(literal("coins")
                .then(literal("add")
                    .then(argument("amount", DoubleArgumentType.doubleArg())
                        .executes(SkyblockAdminCommand::addCoins))))
            .then(literal("profile")
                .then(literal("reset")
                    .executes(SkyblockAdminCommand::resetProfile)))
        );
    }

    private static int addCoins(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
            double amount = DoubleArgumentType.getDouble(context, "amount");
            
            ProfileManager pm = ProfileManager.getServerInstance(player.getServer().getOverworld());
            SkyblockProfile profile = pm.getProfile(player);
            
            profile.addCoins(amount);
            pm.markDirty();
            
            context.getSource().sendFeedback(() -> Text.of("§aAdded " + amount + " coins."), false);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private static int resetProfile(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
            // In a real database, we would delete row. Here we just wipe data in memory.
            ProfileManager pm = ProfileManager.getServerInstance(player.getServer().getOverworld());
            // Logic to clear profile data would go here (e.g., pm.removeProfile(player.getUuid()))
            context.getSource().sendFeedback(() -> Text.of("§cProfile reset logic triggered (Impl pending)."), false);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
