package com.sujal.skyblockstats.registry;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.sujal.skyblockstats.api.SkyBlockAPI;
import com.sujal.skyblockstats.stats.SkyBlockStatType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class CommandRegistry {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> register(dispatcher));
    }

    private static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("sb")
            // /sb stats
            .then(CommandManager.literal("stats")
                .executes(context -> {
                    var player = context.getSource().getPlayer();
                    StringBuilder msg = new StringBuilder("§eYour Stats:\n");
                    var stats = SkyBlockAPI.getStats(player);
                    
                    msg.append("§c❤ Health: ").append((int)stats.getStatValue(SkyBlockStatType.HEALTH)).append("\n");
                    msg.append("§a❈ Defense: ").append((int)stats.getStatValue(SkyBlockStatType.DEFENSE)).append("\n");
                    msg.append("§c❁ Strength: ").append((int)stats.getStatValue(SkyBlockStatType.STRENGTH)).append("\n");
                    msg.append("§f✦ Speed: ").append((int)stats.getStatValue(SkyBlockStatType.SPEED)).append("\n");
                    msg.append("§6Coins: ").append((long)SkyBlockAPI.getCoins(player));

                    context.getSource().sendMessage(Text.literal(msg.toString()));
                    return 1;
                })
            )
            // /sb givecoins <amount>
            .then(CommandManager.literal("givecoins")
                .then(CommandManager.argument("amount", DoubleArgumentType.doubleArg(0))
                    .executes(context -> {
                        double amount = DoubleArgumentType.getDouble(context, "amount");
                        SkyBlockAPI.addCoins(context.getSource().getPlayer(), amount);
                        context.getSource().sendMessage(Text.literal("§aAdded " + amount + " coins!"));
                        return 1;
                    })
                )
            )
            // /sb addstr <amount>
            .then(CommandManager.literal("addstr")
                .then(CommandManager.argument("amount", DoubleArgumentType.doubleArg())
                    .executes(context -> {
                        double amount = DoubleArgumentType.getDouble(context, "amount");
                        SkyBlockAPI.addStatBonus(context.getSource().getPlayer(), SkyBlockStatType.STRENGTH, amount);
                        context.getSource().sendMessage(Text.literal("§cStrength added!"));
                        return 1;
                    })
                )
            )
        );
    }
}
