package com.sujal.skyblocksandbox.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.sujal.skyblocksandbox.item.Rarity;
import com.sujal.skyblocksandbox.item.SBItemFactory;
import com.sujal.skyblocksandbox.stats.StatType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.EnumMap;
import java.util.Map;

public class SBItemCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("sbitem")
                .executes(SBItemCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        try {
            PlayerEntity player = context.getSource().getPlayerOrThrow();
            openItemGui(player);
            return Command.SINGLE_SUCCESS;
        } catch (Exception e) {
            return 0;
        }
    }

    private static void openItemGui(PlayerEntity player) {
        // Create a Container Factory (Chest Interface 3 Rows)
        NamedScreenHandlerFactory factory = new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return Text.literal("Skyblock Items Creator");
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                // 27 slots = 3 rows
                SimpleInventory inventory = new SimpleInventory(27);
                
                // --- ADD ITEMS TO GUI ---
                
                // 1. Hyperion (Sword)
                Map<StatType, Double> hypeStats = new EnumMap<>(StatType.class);
                hypeStats.put(StatType.STRENGTH, 150.0);
                hypeStats.put(StatType.MANA, 350.0);
                hypeStats.put(StatType.CRIT_CHANCE, 50.0);
                
                inventory.setStack(11, SBItemFactory.create(
                        Items.IRON_SWORD, "Hyperion", Rarity.LEGENDARY, hypeStats
                ));

                // 2. Dragon Armor (Chestplate)
                Map<StatType, Double> armorStats = new EnumMap<>(StatType.class);
                armorStats.put(StatType.DEFENSE, 200.0);
                armorStats.put(StatType.HEALTH, 100.0);
                
                inventory.setStack(13, SBItemFactory.create(
                        Items.DIAMOND_CHESTPLATE, "Superior Dragon Chestplate", Rarity.LEGENDARY, armorStats
                ));
                
                 // 3. Rogue Sword (Speed)
                Map<StatType, Double> speedStats = new EnumMap<>(StatType.class);
                speedStats.put(StatType.SPEED, 100.0);
                
                inventory.setStack(15, SBItemFactory.create(
                        Items.GOLDEN_SWORD, "Rogue Sword", Rarity.COMMON, speedStats
                ));

                return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, inventory);
            }
        };

        player.openHandledScreen(factory);
    }
}
