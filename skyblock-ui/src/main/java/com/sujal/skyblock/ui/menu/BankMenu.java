package com.sujal.skyblock.ui.menu;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.economy.BankService;
import net.minecraft.entity.player.PlayerEntity;
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
        deposit.setCustomName(Text.of("§aDeposit Coins"));
        setLore(deposit, "§7Current purse: §6" + FORMAT.format(profile.getCoins()), "", "§eLeft-Click to Deposit All", "§eRight-Click to Deposit Half");
        inv.setStack(11, deposit);

        // 3. Info Item (Emerald) - Slot 13
        ItemStack info = new ItemStack(Items.EMERALD);
        info.setCustomName(Text.of("§aBank Information"));
        setLore(info, "§7Balance: §6" + FORMAT.format(profile.getBankBalance()), "§7Interest: §e1% per season");
        inv.setStack(13, info);

        // 4. Withdraw Item (Dispenser) - Slot 15
        ItemStack withdraw = new ItemStack(Items.DISPENSER);
        withdraw.setCustomName(Text.of("§aWithdraw Coins"));
        setLore(withdraw, "§7Bank Balance: §6" + FORMAT.format(profile.getBankBalance()), "", "§eLeft-Click to Withdraw All", "§eRight-Click to Withdraw Half");
        inv.setStack(15, withdraw);

        // 5. Close Button - Slot 22
        ItemStack close = new ItemStack(Items.BARRIER);
        close.setCustomName(Text.of("§cClose"));
        inv.setStack(22, close);

        // Open GUI
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInv, p) -> GenericContainerScreenHandler.createGeneric9x3(syncId, playerInv, inv),
            Text.literal("Bank")
        ));
    }
    
    private static void setLore(ItemStack stack, String... lines) {
        NbtCompound tag = stack.getOrCreateNbt();
        NbtList lore = new NbtList();
        for(String line : lines) lore.add(NbtString.of(line));
        tag.getCompound("display").put("Lore", lore);
    }
    
    // Note: To make clicks work, we need to update the MenuClickMixin or Handler
    // to detect clicks in this specific menu title "Bank".
}
