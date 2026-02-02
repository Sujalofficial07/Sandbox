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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public abstract class MenuClickMixin {

    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("SkyblockClick");

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        ScreenHandler handler = (ScreenHandler) (Object) this;
        if (slotIndex < 0 || slotIndex >= handler.slots.size()) return;
        if (player.getWorld().isClient) return; // Server only

        Slot slot = handler.slots.get(slotIndex);
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

        // --- DEBUG LOG (Remove later) ---
        // Uncomment to see what inventory you are clicking in console
        // LOGGER.info("Clicked Slot: " + slotIndex + " | Inv Size: " + slot.inventory.size());

        // -----------------------------------------------------------
        // BANK MENU (Size 27)
        // -----------------------------------------------------------
        if (slot.inventory instanceof net.minecraft.inventory.SimpleInventory && slot.inventory.size() == 27) {
            // Prevent taking items from top inventory
            if (slot.inventory != player.getInventory()) {
                ci.cancel(); // Stop item pickup

                // Logic
                ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) player.getWorld());
                SkyblockProfile profile = pm.getProfile(player);
                boolean isRightClick = (button == 1);

                // Slot 11: Deposit (Gold Block)
                if (slotIndex == 11) {
                    double amount = isRightClick ? profile.getCoins() / 2 : profile.getCoins();
                    if (amount > 0) {
                        boolean success = BankService.deposit(serverPlayer, amount);
                        if(success) BankMenu.open(serverPlayer); // Refresh only on success
                    }
                }

                // Slot 15: Withdraw (Dispenser)
                else if (slotIndex == 15) {
                    double amount = isRightClick ? profile.getBankBalance() / 2 : profile.getBankBalance();
                    if (amount > 0) {
                        boolean success = BankService.withdraw(serverPlayer, amount);
                        if(success) BankMenu.open(serverPlayer);
                    }
                }

                // Slot 22: Close
                else if (slotIndex == 22) {
                    serverPlayer.closeHandledScreen();
                }
            }
        }

        // -----------------------------------------------------------
        // ITEMS MENU (Size 54)
        // -----------------------------------------------------------
        else if (slot.inventory instanceof net.minecraft.inventory.SimpleInventory && slot.inventory.size() == 54) {
            if (slot.inventory != player.getInventory()) {
                if (slot.hasStack()) {
                    player.getInventory().offerOrDrop(slot.getStack().copy());
                }
                ci.cancel();
            }
        }
    }
}
