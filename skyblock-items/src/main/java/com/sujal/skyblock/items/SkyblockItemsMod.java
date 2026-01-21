package com.sujal.skyblock.items;

import com.sujal.skyblock.items.factory.ItemDefinitions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class SkyblockItemsMod implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("sbitems")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("hyperion")
                    .executes(context -> {
                        context.getSource().getPlayerOrThrow().getInventory().insertStack(ItemDefinitions.getHyperion());
                        context.getSource().sendFeedback(() -> Text.of("§aGave Hyperion!"), false);
                        return 1;
                    }))
            );
        });
    }
}
