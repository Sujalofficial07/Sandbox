package com.sujal.skyblocksandbox.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.sujal.skyblocksandbox.item.definitions.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SBItemCommand {

    // Global List of All Items
    private static final List<ItemStack> ALL_ITEMS = new ArrayList<>();

    // Initialize list once
    static {
        // Page 1: Melee
        ALL_ITEMS.add(SBItems_Melee.HYPERION());
        ALL_ITEMS.add(SBItems_Melee.VALKYRIE());
        ALL_ITEMS.add(SBItems_Melee.SCYLLA());
        ALL_ITEMS.add(SBItems_Melee.ASTRAEA());
        ALL_ITEMS.add(SBItems_Melee.GIANTS_SWORD());
        ALL_ITEMS.add(SBItems_Melee.LIVID_DAGGER());
        ALL_ITEMS.add(SBItems_Melee.SHADOW_FURY());
        ALL_ITEMS.add(SBItems_Melee.EMERALD_BLADE());
        ALL_ITEMS.add(SBItems_Melee.ASPECT_OF_THE_END());
        ALL_ITEMS.add(SBItems_Melee.ASPECT_OF_THE_DRAGONS());

        // Page 1: Ranged & Mage
        ALL_ITEMS.add(SBItems_RangedArmor.TERMINATOR());
        ALL_ITEMS.add(SBItems_RangedArmor.JUJU_SHORTBOW());
        ALL_ITEMS.add(SBItems_RangedArmor.MOSQUITO_BOW());
        ALL_ITEMS.add(SBItems_Mage.MIDAS_STAFF());
        ALL_ITEMS.add(SBItems_Mage.SPIRIT_SCEPTRE());
        ALL_ITEMS.add(SBItems_Mage.YETI_SWORD());
        ALL_ITEMS.add(SBItems_Mage.FIRE_VEIL_WAND());
        ALL_ITEMS.add(SBItems_Mage.ICE_SPRAY_WAND());
        ALL_ITEMS.add(SBItems_Dungeon.BONZO_STAFF());

        // Page 2: Armor
        ALL_ITEMS.add(SBItems_RangedArmor.WARDEN_HELMET());
        ALL_ITEMS.add(SBItems_RangedArmor.NECRON_CHESTPLATE());
        ALL_ITEMS.add(SBItems_RangedArmor.SUPERIOR_DRAGON_HELMET());
        ALL_ITEMS.add(SBItems_RangedArmor.SUPERIOR_DRAGON_CHESTPLATE());
        ALL_ITEMS.add(SBItems_RangedArmor.SUPERIOR_DRAGON_LEGGINGS());
        ALL_ITEMS.add(SBItems_RangedArmor.SUPERIOR_DRAGON_BOOTS());
        ALL_ITEMS.add(SBItems_Dungeon.SHADOW_ASSASSIN_CHESTPLATE());

        // Page 2: Tools & Slayer
        ALL_ITEMS.add(SBItems_Tools.DIVANS_DRILL());
        ALL_ITEMS.add(SBItems_Tools.TITANIUM_PICKAXE_X555());
        ALL_ITEMS.add(SBItems_Tools.STONK());
        ALL_ITEMS.add(SBItems_Tools.TREECAPITATOR());
        ALL_ITEMS.add(SBItems_Tools.GRAPPLING_HOOK());
        ALL_ITEMS.add(SBItems_Tools.ASPECT_OF_THE_VOID());
        ALL_ITEMS.add(SBItems_Slayer.OVERFLUX_POWER_ORB());
        ALL_ITEMS.add(SBItems_Slayer.WAND_OF_ATONEMENT());
        ALL_ITEMS.add(SBItems_Slayer.VOIDEDGE_KATANA());

        // Page 3: Accessories
        ALL_ITEMS.add(SBItems_Accessories.HEGEMONY_ARTIFACT());
        ALL_ITEMS.add(SBItems_Accessories.ENDER_ARTIFACT());
        ALL_ITEMS.add(SBItems_Accessories.WITHER_RELIC());
        ALL_ITEMS.add(SBItems_Accessories.SEAL_OF_THE_FAMILY());
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("sbitem")
                .executes(SBItemCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        try {
            PlayerEntity player = context.getSource().getPlayerOrThrow();
            openPagedGui(player, 0); // Open Page 0
            return Command.SINGLE_SUCCESS;
        } catch (Exception e) {
            return 0;
        }
    }

    private static void openPagedGui(PlayerEntity player, int pageIndex) {
        NamedScreenHandlerFactory factory = new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return Text.literal("Item Menu - Page " + (pageIndex + 1));
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                // 54 Slots
                SimpleInventory inventory = new SimpleInventory(54);
                
                // Define Area for Items (0 to 44)
                int itemsPerPage = 45;
                int start = pageIndex * itemsPerPage;
                int end = Math.min(start + itemsPerPage, ALL_ITEMS.size());

                for (int i = start; i < end; i++) {
                    inventory.setStack(i - start, ALL_ITEMS.get(i).copy());
                }

                // --- CONTROLS (Bottom Row) ---
                
                // Filler Glass
                ItemStack filler = new ItemStack(Items.GRAY_STAINED_GLASS_PANE);
                filler.setCustomName(Text.empty());
                for (int i = 45; i < 54; i++) {
                    inventory.setStack(i, filler);
                }

                // Previous Page Button (Slot 45)
                if (pageIndex > 0) {
                    ItemStack prev = new ItemStack(Items.ARROW);
                    prev.setCustomName(Text.literal("§a< Previous Page"));
                    inventory.setStack(45, prev);
                    // Note: Click logic requires valid Packet Handling/ScreenHandler, 
                    // for "Simple" command GUI, we usually just tell player to use /sbitem 2
                    // But for now, we just display items. 
                }

                // Next Page Button (Slot 53)
                if (end < ALL_ITEMS.size()) {
                    ItemStack next = new ItemStack(Items.ARROW);
                    next.setCustomName(Text.literal("§aNext Page >"));
                    inventory.setStack(53, next);
                }

                return GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory, inventory);
            }
        };

        player.openHandledScreen(factory);
    }
}
