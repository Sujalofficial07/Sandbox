package com.sujal.skyblock.mixins;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.economy.BankService;
import com.sujal.skyblock.ui.menu.BankMenu;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public abstract class MenuClickMixin {

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        // 1. Basic Safety Checks
        ScreenHandler handler = (ScreenHandler) (Object) this;
        if (slotIndex < 0 || slotIndex >= handler.slots.size()) return;
        if (player.getWorld().isClient) return; // Server-side logic only

        Slot slot = handler.slots.get(slotIndex);
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

        // -----------------------------------------------------------
        // LOGIC 1: BANK MENU (Size 27 - SimpleInventory)
        // -----------------------------------------------------------
        if (slot.inventory instanceof net.minecraft.inventory.SimpleInventory && slot.inventory.size() == 27) {
            // Cancel ALL clicks in top inventory to prevent taking items
            if (slot.inventory != player.getInventory()) {
                ci.cancel(); 

                // Handle Buttons
                ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) player.getWorld());
                SkyblockProfile profile = pm.getProfile(player);
                boolean isRightClick = (button == 1);

                // Slot 11: Deposit (Gold Block)
                if (slotIndex == 11) {
                    double amount = isRightClick ? profile.getCoins() / 2 : profile.getCoins();
                    if (amount > 0) {
                        BankService.deposit(serverPlayer, amount);
                        // Refresh the menu to update lore
                        BankMenu.open(serverPlayer);
                    }
                }

                // Slot 15: Withdraw (Dispenser)
                else if (slotIndex == 15) {
                    double amount = isRightClick ? profile.getBankBalance() / 2 : profile.getBankBalance();
                    if (amount > 0) {
                        BankService.withdraw(serverPlayer, amount);
                        // Refresh the menu to update lore
                        BankMenu.open(serverPlayer);
                    }
                }

                // Slot 22: Close (Barrier)
                else if (slotIndex == 22) {
                    player.closeHandledScreen();
                }
            }
        }

        // -----------------------------------------------------------
        // LOGIC 2: ITEM MENU / CHEAT MENU (Size 54 - SimpleInventory)
        // -----------------------------------------------------------
        else if (slot.inventory instanceof net.minecraft.inventory.SimpleInventory && slot.inventory.size() == 54) {
            // Only apply logic if clicking Top Inventory
            if (slot.inventory != player.getInventory()) {
                // Allow COPYING the item (Give to player)
                if (slot.hasStack()) {
                    player.getInventory().offerOrDrop(slot.getStack().copy());
                }
                
                // Cancel the actual move event so the item stays in the menu
                ci.cancel();
            }
        }
    }
}
