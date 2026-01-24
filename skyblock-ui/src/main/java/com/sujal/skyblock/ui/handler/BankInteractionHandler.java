package com.sujal.skyblock.ui.handler;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.economy.BankService;
import com.sujal.skyblock.ui.menu.BankMenu;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class BankInteractionHandler {
    
    // This logic needs to be called from your Mixin or a Fabric Event for Inventory Clicks.
    // Since Fabric API doesn't have a direct "InventoryClickEvent", we use the Mixin we created earlier.
    
    public static void handleBankClick(ServerPlayerEntity player, int slotId, boolean isRightClick) {
        ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) player.getWorld());
        SkyblockProfile profile = pm.getProfile(player);
        
        // Slot 11: Deposit
        if (slotId == 11) {
            double amount = isRightClick ? profile.getCoins() / 2 : profile.getCoins();
            if (amount > 0) {
                BankService.deposit(player, amount);
                BankMenu.open(player); // Refresh Menu
            }
        }
        
        // Slot 15: Withdraw
        if (slotId == 15) {
            double amount = isRightClick ? profile.getBankBalance() / 2 : profile.getBankBalance();
            if (amount > 0) {
                BankService.withdraw(player, amount);
                BankMenu.open(player); // Refresh Menu
            }
        }
        
        // Slot 22: Close
        if (slotId == 22) {
            player.closeHandledScreen();
        }
    }
}
