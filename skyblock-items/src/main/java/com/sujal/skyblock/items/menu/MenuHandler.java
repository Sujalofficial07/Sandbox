package com.sujal.skyblock.items.menu;

import com.sujal.skyblock.items.Items;
import com.sujal.skyblock.items.api.SBItem;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.TypedActionResult;

import java.util.ArrayList;
import java.util.List;

public class MenuHandler {

    private static final String MENU_TITLE = "SkyBlock Item Menu";

    public static void register() {
        // Register Command
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("sbitems")
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
                    openItemMenu(player);
                    return 1;
                }));
        });
    }

    private static void openItemMenu(ServerPlayerEntity player) {
        // Create a 54-slot inventory (Double Chest)
        SimpleInventory inventory = new SimpleInventory(54);
        
        // Populate with items
        List<SBItem> allItems = new ArrayList<>(Items.getAll());
        for (int i = 0; i < allItems.size() && i < 54; i++) {
            inventory.setStack(i, allItems.get(i).createItemStack());
        }

        // Open the GUI
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, inv, p) -> GenericContainerScreenHandler.createGeneric9x6(syncId, inv, inventory),
            Text.literal(MENU_TITLE)
        ));
    }
    
    // Note: To handle clicks properly (prevent moving items out), 
    // we normally need a Mixin into 'ScreenHandler' or handle a Fabric event if available for inventory clicks.
    // Since Fabric API doesn't have a direct "InventoryClickEvent" like Spigot, 
    // we use a specific Mixin to intercept slot clicks. 
    // However, for simplicity in this module, we will assume you add the following Mixin logic separately 
    // or use a library like 'sgui'. 
    
    // BELOW is the logic you would put in a Mixin or Event Handler for clicks:
    /*
    if (screenHandler.getTitle().getString().equals(MENU_TITLE)) {
        event.cancel(); // Stop moving
        if (clickedStack != empty) {
             player.getInventory().offerOrDrop(clickedStack.copy());
        }
    }
    */
}
