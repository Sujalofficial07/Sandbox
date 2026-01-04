package com.sujal.skyblocksandbox.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.sujal.skyblocksandbox.item.definitions.SBItems_Mage;
import com.sujal.skyblocksandbox.item.definitions.SBItems_Melee;
import com.sujal.skyblocksandbox.item.definitions.SBItems_RangedArmor;
import com.sujal.skyblocksandbox.item.definitions.SBItems_Tools;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

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
        NamedScreenHandlerFactory factory = new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return Text.literal("Skyblock God Mode");
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                // 54 slots = Double Chest size (6 rows x 9 columns)
                SimpleInventory inventory = new SimpleInventory(54);

                // --- MELEE WEAPONS (Row 1) ---
                inventory.setStack(0, SBItems_Melee.HYPERION());
                inventory.setStack(1, SBItems_Melee.VALKYRIE());
                inventory.setStack(2, SBItems_Melee.SCYLLA());
                inventory.setStack(3, SBItems_Melee.ASTRAEA());
                inventory.setStack(4, SBItems_Melee.GIANTS_SWORD());
                inventory.setStack(5, SBItems_Melee.LIVID_DAGGER());
                inventory.setStack(6, SBItems_Melee.SHADOW_FURY());
                inventory.setStack(7, SBItems_Melee.ASPECT_OF_THE_DRAGONS());
                inventory.setStack(8, SBItems_Melee.ASPECT_OF_THE_END());

                // --- RANGED & MAGE (Row 2) ---
                inventory.setStack(9, SBItems_RangedArmor.TERMINATOR());
                inventory.setStack(10, SBItems_RangedArmor.JUJU_SHORTBOW());
                inventory.setStack(11, SBItems_RangedArmor.MOSQUITO_BOW());
                inventory.setStack(12, SBItems_Mage.MIDAS_STAFF());
                inventory.setStack(13, SBItems_Mage.SPIRIT_SCEPTRE());
                inventory.setStack(14, SBItems_Mage.YETI_SWORD());
                inventory.setStack(15, SBItems_Mage.FIRE_VEIL_WAND());
                inventory.setStack(16, SBItems_Mage.ICE_SPRAY_WAND());

                // --- ARMOR (Row 3) ---
                inventory.setStack(18, SBItems_RangedArmor.WARDEN_HELMET());
                inventory.setStack(19, SBItems_RangedArmor.NECRON_CHESTPLATE());
                inventory.setStack(20, SBItems_RangedArmor.SUPERIOR_DRAGON_HELMET());
                inventory.setStack(21, SBItems_RangedArmor.SUPERIOR_DRAGON_CHESTPLATE());
                inventory.setStack(22, SBItems_RangedArmor.SUPERIOR_DRAGON_LEGGINGS());
                inventory.setStack(23, SBItems_RangedArmor.SUPERIOR_DRAGON_BOOTS());

                // --- TOOLS & MISC (Row 4) ---
                inventory.setStack(27, SBItems_Tools.DIVANS_DRILL());
                inventory.setStack(28, SBItems_Tools.TITANIUM_PICKAXE_X555());
                inventory.setStack(29, SBItems_Tools.STONK());
                inventory.setStack(30, SBItems_Tools.TREECAPITATOR());
                inventory.setStack(31, SBItems_Tools.GRAPPLING_HOOK());
                inventory.setStack(32, SBItems_Tools.ASPECT_OF_THE_VOID());
                inventory.setStack(33, SBItems_Melee.EMERALD_BLADE());

                // Use Generic 9x6 for Double Chest size
                return GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory, inventory);
            }
        };

        player.openHandledScreen(factory);
    }
}
