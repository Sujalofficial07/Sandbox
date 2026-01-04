package com.sujal.skyblocksandbox.gui;

import com.sujal.skyblocksandbox.mixin.PlayerInvoker; // Import the Invoker
import com.sujal.skyblocksandbox.stats.StatType;
import com.sujal.skyblocksandbox.stats.StatsHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SkyblockMenus {

    // Track players who have our GUI open
    private static final Set<UUID> openMenuPlayers = new HashSet<>();

    public static boolean isSkyblockMenu(PlayerEntity player) {
        return openMenuPlayers.contains(player.getUuid());
    }

    // --- MAIN MENU (The Nether Star Menu) ---
    public static void openMainMenu(PlayerEntity player) {
        openMenuPlayers.add(player.getUuid());
        
        NamedScreenHandlerFactory factory = new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return Text.literal("Skyblock Menu");
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity p) {
                SimpleInventory gui = new SimpleInventory(54);
                fillBorder(gui);

                gui.setStack(13, createItem(Items.PLAYER_HEAD, "§aYour Skyblock Profile", 
                    "§7View your equipment, stats,", "§7and progress.", "", "§eClick to open!"));

                gui.setStack(19, createItem(Items.DIAMOND_SWORD, "§aYour Skills", 
                    "§7View your skill progression.", "", "§eClick to view!"));

                gui.setStack(20, createItem(Items.PAINTING, "§aCollections", 
                    "§7View your item collections.", "", "§eClick to view!"));
                    
                gui.setStack(21, createItem(Items.BOOK, "§aRecipe Book", 
                    "§7View unlocked recipes.", "", "§eClick to view!"));
                
                gui.setStack(22, createItem(Items.EMERALD, "§aTrades", 
                    "§7View your unlocked trades.", "", "§eClick to view!"));

                gui.setStack(49, createItem(Items.BARRIER, "§cClose"));

                return GenericContainerScreenHandler.createGeneric9x6(syncId, inv, gui);
            }
        };
        player.openHandledScreen(factory);
    }

    // --- PROFILE / STATS MENU ---
    public static void openStatsMenu(PlayerEntity player) {
        openMenuPlayers.add(player.getUuid());
        StatsHandler.recalculateStats(player);

        NamedScreenHandlerFactory factory = new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return Text.literal("Your Equipment and Stats");
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity p) {
                SimpleInventory gui = new SimpleInventory(54);
                fillBorder(gui);

                gui.setStack(10, getArmorCopy(player, 3, "Helmet"));
                gui.setStack(19, getArmorCopy(player, 2, "Chestplate"));
                gui.setStack(28, getArmorCopy(player, 1, "Leggings"));
                gui.setStack(37, getArmorCopy(player, 0, "Boots"));

                ItemStack skull = createItem(Items.PLAYER_HEAD, "§a" + player.getName().getString(), 
                    "§7Profile stats and info.");
                NbtCompound nbt = skull.getOrCreateNbt();
                nbt.putString("SkullOwner", player.getName().getString());
                gui.setStack(13, skull);

                gui.setStack(15, createStatItem(Items.GOLDEN_APPLE, "§cHealth", player, StatType.HEALTH, "§7Your maximum health."));
                gui.setStack(16, createStatItem(Items.IRON_CHESTPLATE, "§aDefense", player, StatType.DEFENSE, "§7Reduces damage taken."));
                gui.setStack(24, createStatItem(Items.BLAZE_POWDER, "§cStrength", player, StatType.STRENGTH, "§7Increases raw damage."));
                gui.setStack(25, createStatItem(Items.EXPERIENCE_BOTTLE, "§bIntelligence", player, StatType.MANA, "§7Increases max mana."));
                gui.setStack(33, createStatItem(Items.SKELETON_SKULL, "§9Crit Chance", player, StatType.CRIT_CHANCE, "§7Chance to deal double damage."));
                gui.setStack(34, createStatItem(Items.WITHER_SKELETON_SKULL, "§9Crit Damage", player, StatType.CRIT_DAMAGE, "§7Multiplier on critical hits."));
                    
                gui.setStack(48, createItem(Items.ARROW, "§aGo Back", "§7To Skyblock Menu"));
                gui.setStack(49, createItem(Items.BARRIER, "§cClose"));

                return GenericContainerScreenHandler.createGeneric9x6(syncId, inv, gui);
            }
        };
        player.openHandledScreen(factory);
    }

    // --- CLICK HANDLER ---
    public static void handleButtonClick(PlayerEntity player, ItemStack clickedItem) {
        String name = clickedItem.getName().getString();

        if (name.contains("Close")) {
            // FIXED: Use the Invoker to call the protected method
            ((PlayerInvoker) player).invokeCloseHandledScreen();
            
            openMenuPlayers.remove(player.getUuid());
        } 
        else if (name.contains("Your Skyblock Profile") || name.contains("Go Back")) {
            if (name.contains("Profile")) {
                openStatsMenu(player);
            } else {
                openMainMenu(player);
            }
        }
    }

    // --- HELPERS ---
    private static void fillBorder(SimpleInventory inv) {
        ItemStack pane = createItem(Items.BLACK_STAINED_GLASS_PANE, " ");
        for (int i = 0; i < inv.size(); i++) {
            if (inv.getStack(i).isEmpty()) {
                inv.setStack(i, pane);
            }
        }
    }

    private static ItemStack getArmorCopy(PlayerEntity player, int slot, String type) {
        ItemStack item = player.getInventory().getArmorStack(slot);
        if (item.isEmpty()) {
            return createItem(Items.RED_STAINED_GLASS_PANE, "§cNo " + type);
        }
        return item.copy();
    }

    private static ItemStack createItem(Item item, String name, String... lore) {
        ItemStack stack = new ItemStack(item);
        stack.setCustomName(Text.literal(name));
        
        if (lore.length > 0) {
            NbtCompound nbt = stack.getOrCreateNbt();
            NbtCompound display = nbt.getCompound("display");
            NbtList loreList = new NbtList();
            for (String l : lore) loreList.add(NbtString.of(Text.Serializer.toJson(Text.literal(l))));
            display.put("Lore", loreList);
            nbt.put("display", display);
        }
        return stack;
    }
    
    private static ItemStack createStatItem(Item item, String name, PlayerEntity player, StatType stat, String desc) {
        double val = StatsHandler.getStat(player, stat);
        String statLine = stat.getColor() + stat.getIcon() + " " + stat.getName() + ": " + Formatting.WHITE + (int)val;
        return createItem(item, name, desc, "", statLine);
    }
}
