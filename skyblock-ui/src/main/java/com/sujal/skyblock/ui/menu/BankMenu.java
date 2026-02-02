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

import java.text.DecimalFormat;

public class BankMenu {
    
    private static final DecimalFormat FORMAT = new DecimalFormat("#,###");

    public static void open(ServerPlayerEntity player) {
        SimpleInventory inv = new SimpleInventory(27); // 3 Rows
        
        ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) player.getWorld());
        SkyblockProfile profile = pm.getProfile(player);

        // 1. Background Glass
        ItemStack glass = new ItemStack(Items.GRAY_STAINED_GLASS_PANE);
        glass.setCustomName(Text.empty());
        for(int i=0; i<27; i++) inv.setStack(i, glass);

        // 2. Deposit Item (Gold Block) - Slot 11
        ItemStack deposit = new ItemStack(Items.GOLD_BLOCK);
        deposit.setCustomName(Text.literal("§aDeposit Coins"));
        setLore(deposit, 
            "§7Current purse: §6" + FORMAT.format(profile.getCoins()), 
            "", 
            "§eLeft-Click to Deposit All", 
            "§eRight-Click to Deposit Half"
        );
        inv.setStack(11, deposit);

        // 3. Info Item (Emerald) - Slot 13
        ItemStack info = new ItemStack(Items.EMERALD);
        info.setCustomName(Text.literal("§aBank Information"));
        setLore(info, 
            "§7Balance: §6" + FORMAT.format(profile.getBankBalance()), 
            "§7Interest: §e1% per season"
        );
        inv.setStack(13, info);

        // 4. Withdraw Item (Dispenser) - Slot 15
        ItemStack withdraw = new ItemStack(Items.DISPENSER);
        withdraw.setCustomName(Text.literal("§aWithdraw Coins"));
        setLore(withdraw, 
            "§7Bank Balance: §6" + FORMAT.format(profile.getBankBalance()), 
            "", 
            "§eLeft-Click to Withdraw All", 
            "§eRight-Click to Withdraw Half"
        );
        inv.setStack(15, withdraw);

        // 5. Close Button - Slot 22
        ItemStack close = new ItemStack(Items.BARRIER);
        close.setCustomName(Text.literal("§cClose"));
        inv.setStack(22, close);

        // Open GUI
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInv, p) -> GenericContainerScreenHandler.createGeneric9x3(syncId, playerInv, inv),
            Text.literal("Bank")
        ));
    }
    
    // --- FIXED LORE LOGIC ---
    private static void setLore(ItemStack stack, String... lines) {
        // Use getOrCreateSubNbt to ensure "display" tag exists and is linked
        NbtCompound display = stack.getOrCreateSubNbt("display");
        
        NbtList lore = new NbtList();
        for(String line : lines) {
            lore.add(NbtString.of(line));
        }
        
        display.put("Lore", lore);
    }
}
