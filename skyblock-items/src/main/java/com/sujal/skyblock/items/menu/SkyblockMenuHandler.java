package com.sujal.skyblock.items.menu;

import com.sujal.skyblock.items.api.SBItem;
import com.sujal.skyblock.items.registry.SkyblockItems;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

public class SkyblockMenuHandler {
    
    public static final String TITLE = "SkyBlock Item Menu";

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("sbitems")
                .executes(context -> {
                    openMenu(context.getSource().getPlayerOrThrow());
                    return 1;
                }));
        });
    }

    private static void openMenu(ServerPlayerEntity player) {
        SimpleInventory inventory = new SimpleInventory(54);
        List<SBItem> items = SkyblockItems.getAllItems();
        
        for (int i = 0; i < items.size() && i < 54; i++) {
            inventory.setStack(i, items.get(i).createItemStack());
        }

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, inv, p) -> GenericContainerScreenHandler.createGeneric9x6(syncId, inv, inventory),
            Text.literal(TITLE)
        ));
    }
}
