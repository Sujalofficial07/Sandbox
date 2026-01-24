package com.sujal.skyblock.core.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.sujal.skyblock.core.api.StatType;
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
        // Core Commands (Data Only)
        dispatcher.register(literal("sb")
            .then(literal("profile")
                .requires(source -> source.hasPermissionLevel(2)) // OP Only
                .then(literal("set")
                    .then(argument("stat", StringArgumentType.word())
                        .then(argument("value", DoubleArgumentType.doubleArg())
                            .executes(SkyblockAdminCommand::setStat))))
                .then(literal("reset")
                     .executes(SkyblockAdminCommand::resetProfile)))
            .then(literal("coins")
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("add")
                    .then(argument("amount", DoubleArgumentType.doubleArg())
                        .executes(SkyblockAdminCommand::addCoins))))
        );
    }

    private static int setStat(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
            String statName = StringArgumentType.getString(context, "stat").toUpperCase();
            double value = DoubleArgumentType.getDouble(context, "value");
            
            ProfileManager pm = ProfileManager.getServerInstance(player.getServer().getOverworld());
            SkyblockProfile profile = pm.getProfile(player);
            
            try {
                StatType type = StatType.valueOf(statName);
                profile.setStatBonus(type, value);
                pm.markDirty();
                context.getSource().sendFeedback(() -> Text.of("§aSet " + statName + " to " + value), false);
            } catch (IllegalArgumentException e) {
                context.getSource().sendFeedback(() -> Text.of("§cInvalid Stat Name! Use: HEALTH, STRENGTH, DEFENSE, INTELLIGENCE"), false);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static int addCoins(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
            double amount = DoubleArgumentType.getDouble(context, "amount");
            
            ProfileManager pm = ProfileManager.getServerInstance(player.getServer().getOverworld());
            SkyblockProfile profile = pm.getProfile(player);
            
            profile.addCoins(amount);
            pm.markDirty();
            context.getSource().sendFeedback(() -> Text.of("§6Added " + (long)amount + " coins."), false);
            return 1;
        } catch (Exception e) { return 0; }
    }
    
    private static int resetProfile(CommandContext<ServerCommandSource> context) {
         // Reset Logic Placeholder
         context.getSource().sendFeedback(() -> Text.of("§cProfile reset logic pending."), false);
         return 1;
    }
}
