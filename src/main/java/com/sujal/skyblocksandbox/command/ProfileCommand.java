package com.sujal.skyblocksandbox.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.sujal.skyblocksandbox.stats.StatType;
import com.sujal.skyblocksandbox.stats.StatsHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class ProfileCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("profile")
                .executes(context -> run(context.getSource().getPlayerOrThrow())));
                
        // Alias /sbmenu
        dispatcher.register(CommandManager.literal("sbmenu")
                .executes(context -> run(context.getSource().getPlayerOrThrow())));
    }

    private static int run(PlayerEntity player) {
        openProfileGui(player);
        return Command.SINGLE_SUCCESS;
    }

    private static void openProfileGui(PlayerEntity player) {
        // Recalculate stats to ensure fresh data
        StatsHandler.recalculateStats(player);

        NamedScreenHandlerFactory factory = new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return Text.literal("Skyblock Profile");
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                // 54 Slots (6 Rows) like Hypixel
                SimpleInventory inventory = new SimpleInventory(54);

                // --- 1. PLAYER INFO HEAD (Slot 13 - Center Row 2) ---
                ItemStack skull = new ItemStack(Items.PLAYER_HEAD);
                NbtCompound nbt = skull.getOrCreateNbt();
                nbt.putString("SkullOwner", player.getName().getString());
                
                // Set Display Name
                skull.setCustomName(Text.literal("§aYour Skyblock Profile"));
                
                // Build Stats Lore
                List<Text> lore = new ArrayList<>();
                lore.add(Text.literal("§7View your equipment, stats,"));
                lore.add(Text.literal("§7and collections here."));
                lore.add(Text.empty());
                
                // Add All Stats dynamically
                for (StatType stat : StatType.values()) {
                    double val = StatsHandler.getStat(player, stat);
                    // Skip 0 values for obscure stats to keep it clean, but show important ones always
                    if (val == 0 && stat != StatType.DEFENSE && stat != StatType.STRENGTH) continue;
                    
                    String line = String.format("%s%s %s: %s%.0f", 
                        stat.getColor(), stat.getIcon(), stat.getName(), Formatting.WHITE, val);
                    lore.add(Text.literal(line));
                }
                
                // Apply Lore
                NbtCompound display = nbt.getCompound("display");
                NbtList loreList = new NbtList();
                for (Text t : lore) loreList.add(NbtString.of(Text.Serializer.toJson(t)));
                display.put("Lore", loreList);
                nbt.put("display", display);
                
                inventory.setStack(13, skull);


                // --- 2. ARMOR SLOTS (Visual only) ---
                // Hypixel puts armor on the left or bottom usually. Let's put them on the left side (10, 19, 28, 37)
                // Helmet
                inventory.setStack(10, player.getInventory().getArmorStack(3).isEmpty() 
                        ? createPlaceholder(Items.IRON_BARS, "§cNo Helmet") 
                        : player.getInventory().getArmorStack(3).copy());
                
                // Chestplate
                inventory.setStack(19, player.getInventory().getArmorStack(2).isEmpty() 
                        ? createPlaceholder(Items.IRON_BARS, "§cNo Chestplate") 
                        : player.getInventory().getArmorStack(2).copy());
                
                // Leggings
                inventory.setStack(28, player.getInventory().getArmorStack(1).isEmpty() 
                        ? createPlaceholder(Items.IRON_BARS, "§cNo Leggings") 
                        : player.getInventory().getArmorStack(1).copy());
                
                // Boots
                inventory.setStack(37, player.getInventory().getArmorStack(0).isEmpty() 
                        ? createPlaceholder(Items.IRON_BARS, "§cNo Boots") 
                        : player.getInventory().getArmorStack(0).copy());


                // --- 3. FILLER GLASS (Make it look professional) ---
                ItemStack filler = new ItemStack(Items.GRAY_STAINED_GLASS_PANE);
                filler.setCustomName(Text.empty());
                for (int i = 0; i < 54; i++) {
                    if (inventory.getStack(i).isEmpty()) {
                        inventory.setStack(i, filler);
                    }
                }
                
                // Close Button
                ItemStack close = new ItemStack(Items.BARRIER);
                close.setCustomName(Text.literal("§cClose"));
                inventory.setStack(49, close);

                return GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory, inventory);
            }
        };

        player.openHandledScreen(factory);
    }
    
    private static ItemStack createPlaceholder(net.minecraft.item.Item item, String name) {
        ItemStack stack = new ItemStack(item);
        stack.setCustomName(Text.literal(name));
        return stack;
    }
}
