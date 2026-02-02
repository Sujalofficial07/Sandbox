package com.sujal.skyblock.ui.menu;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.text.DecimalFormat;

public class BankMenu {
    
    private static final DecimalFormat FORMAT = new DecimalFormat("#,###");

    // --- MAIN MENU ---
    public static void openMain(ServerPlayerEntity player) {
        SimpleInventory inv = createBaseInventory("Coop Bank Account");
        SkyblockProfile profile = getProfile(player);

        // Slot 11: Deposit (Chest)
        ItemStack deposit = createItem(Items.CHEST, "§aDeposit Coins",
            "§7Current balance: §6" + FORMAT.format(profile.getBankBalance()),
            "§7Store coins in the bank to keep",
            "§7them safe while you go on",
            "§7adventures!",
            "",
            "§7You will earn §b2% §7interest every",
            "§7season for your first §e10 million",
            "§7banked coins.",
            "",
            "§eClick to make a deposit!"
        );
        inv.setStack(11, deposit);

        // Slot 12: Withdraw (Dispenser) - Video position
        ItemStack withdraw = createItem(Items.DISPENSER, "§aWithdraw Coins",
            "§7Current balance: §6" + FORMAT.format(profile.getBankBalance()),
            "§7Take your coins out of the bank",
            "§7in order to spend them.",
            "",
            "§eClick to withdraw coins!"
        );
        inv.setStack(12, withdraw);

        // Slot 13: Recent Transactions (Paper)
        ItemStack history = createItem(Items.PAPER, "§aRecent transactions",
            "§7View your recent transaction",
            "§7history.",
            "",
            "§eClick to view!"
        );
        inv.setStack(13, history);

        // Slot 31: Close (Barrier)
        inv.setStack(31, createItem(Items.BARRIER, "§cClose", "§eClick to close!"));

        // Slot 45-53: Fill with Black Glass (Hypixel Style Footer)
        ItemStack blackGlass = createItem(Items.BLACK_STAINED_GLASS_PANE, " ");
        for (int i = 45; i < 54; i++) inv.setStack(i, blackGlass);

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInv, p) -> GenericContainerScreenHandler.createGeneric9x6(syncId, playerInv, inv),
            Text.literal("Coop Bank Account")
        ));
    }

    // --- DEPOSIT MENU ---
    public static void openDeposit(ServerPlayerEntity player) {
        SimpleInventory inv = createBaseInventory("Bank Deposit");
        SkyblockProfile profile = getProfile(player);

        // Slot 11: Whole Purse
        ItemStack whole = createItem(Items.CHEST, "§aYour whole purse",
            "§7Bank deposit",
            "",
            "§7Current balance: §6" + FORMAT.format(profile.getBankBalance()),
            "§7Amount to deposit: §6" + FORMAT.format(profile.getCoins()),
            "",
            "§eClick to deposit coins!"
        );
        inv.setStack(11, whole);

        // Slot 12: Half Purse
        ItemStack half = createItem(Items.CHEST, "§aHalf your purse",
            "§7Bank deposit",
            "",
            "§7Current balance: §6" + FORMAT.format(profile.getBankBalance()),
            "§7Amount to deposit: §6" + FORMAT.format(profile.getCoins() / 2),
            "",
            "§eClick to deposit coins!"
        );
        inv.setStack(12, half);

        // Slot 31: Back Arrow
        inv.setStack(31, createItem(Items.ARROW, "§aGo Back", "§7To Bank Account"));

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInv, p) -> GenericContainerScreenHandler.createGeneric9x6(syncId, playerInv, inv),
            Text.literal("Bank Deposit")
        ));
    }

    // --- WITHDRAW MENU ---
    public static void openWithdraw(ServerPlayerEntity player) {
        SimpleInventory inv = createBaseInventory("Bank Withdraw");
        SkyblockProfile profile = getProfile(player);
        double bal = profile.getBankBalance();

        // Slot 11: Everything
        inv.setStack(11, createItem(Items.DISPENSER, "§aEverything in the account",
            "§7Bank Withdraw", "",
            "§7Current balance: §6" + FORMAT.format(bal),
            "§7Amount to withdraw: §6" + FORMAT.format(bal), "", "§eClick to withdraw coins!"
        ));

        // Slot 12: Half
        inv.setStack(12, createItem(Items.DISPENSER, "§aHalf the account",
            "§7Bank Withdraw", "",
            "§7Current balance: §6" + FORMAT.format(bal),
            "§7Amount to withdraw: §6" + FORMAT.format(bal / 2), "", "§eClick to withdraw coins!"
        ));

        // Slot 13: 20%
        inv.setStack(13, createItem(Items.DISPENSER, "§a20% of the account",
            "§7Bank Withdraw", "",
            "§7Current balance: §6" + FORMAT.format(bal),
            "§7Amount to withdraw: §6" + FORMAT.format(bal * 0.2), "", "§eClick to withdraw coins!"
        ));

        // Slot 31: Back Arrow
        inv.setStack(31, createItem(Items.ARROW, "§aGo Back", "§7To Bank Account"));

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInv, p) -> GenericContainerScreenHandler.createGeneric9x6(syncId, playerInv, inv),
            Text.literal("Bank Withdraw")
        ));
    }

    // --- HELPERS ---
    private static SimpleInventory createBaseInventory(String title) {
        SimpleInventory inv = new SimpleInventory(54);
        ItemStack glass = createItem(Items.GRAY_STAINED_GLASS_PANE, " ");
        for(int i=0; i<54; i++) inv.setStack(i, glass);
        return inv;
    }

    private static ItemStack createItem(net.minecraft.item.Item item, String name, String... lore) {
        ItemStack stack = new ItemStack(item);
        stack.setCustomName(Text.literal(name));
        NbtCompound display = stack.getOrCreateSubNbt("display");
        NbtList loreList = new NbtList();
        for(String line : lore) loreList.add(NbtString.of(line));
        display.put("Lore", loreList);
        return stack;
    }

    private static SkyblockProfile getProfile(ServerPlayerEntity player) {
        return ProfileManager.getServerInstance((ServerWorld)player.getWorld()).getProfile(player);
    }
}
