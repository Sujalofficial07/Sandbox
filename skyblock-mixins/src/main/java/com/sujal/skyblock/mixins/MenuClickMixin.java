package com.sujal.skyblock.mixins;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.economy.BankService;
import com.sujal.skyblock.ui.menu.BankMenu;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public abstract class MenuClickMixin {

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        ScreenHandler handler = (ScreenHandler) (Object) this;
        if (slotIndex < 0 || slotIndex >= handler.slots.size()) return;
        if (player.getWorld().isClient) return;

        Slot slot = handler.slots.get(slotIndex);
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

        // Check if it's a 54 Slot Menu (Bank or Items)
        if (slot.inventory instanceof net.minecraft.inventory.SimpleInventory && slot.inventory.size() == 54) {
            
            // Only handle clicks in the GUI (not player inventory)
            if (slot.inventory != player.getInventory()) {
                ci.cancel(); // Stop moving items

                ItemStack clickedItem = slot.getStack();
                if (clickedItem.isEmpty()) return;

                String itemName = clickedItem.getName().getString();
                ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) player.getWorld());
                SkyblockProfile profile = pm.getProfile(player);

                // --- SOUND EFFECT (Click) ---
                player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.MASTER, 1f, 1f);

                // --- 1. MAIN BANK MENU ---
                if (itemName.contains("Deposit Coins")) {
                    BankMenu.openDeposit(serverPlayer);
                }
                else if (itemName.contains("Withdraw Coins")) {
                    BankMenu.openWithdraw(serverPlayer);
                }
                else if (itemName.contains("Close")) {
                    serverPlayer.closeHandledScreen();
                }

                // --- 2. DEPOSIT MENU ---
                else if (itemName.contains("Your whole purse")) {
                    if (BankService.deposit(serverPlayer, profile.getCoins())) {
                        playSuccessSound(player);
                        BankMenu.openMain(serverPlayer); // Return to main
                    }
                }
                else if (itemName.contains("Half your purse")) {
                    if (BankService.deposit(serverPlayer, profile.getCoins() / 2)) {
                        playSuccessSound(player);
                        BankMenu.openMain(serverPlayer);
                    }
                }
                else if (itemName.contains("Go Back")) {
                    BankMenu.openMain(serverPlayer);
                }

                // --- 3. WITHDRAW MENU ---
                else if (itemName.contains("Everything in the account")) {
                    if (BankService.withdraw(serverPlayer, profile.getBankBalance())) {
                        playSuccessSound(player);
                        BankMenu.openMain(serverPlayer);
                    }
                }
                else if (itemName.contains("Half the account")) {
                    if (BankService.withdraw(serverPlayer, profile.getBankBalance() / 2)) {
                        playSuccessSound(player);
                        BankMenu.openMain(serverPlayer);
                    }
                }
                else if (itemName.contains("20% of the account")) {
                    if (BankService.withdraw(serverPlayer, profile.getBankBalance() * 0.2)) {
                        playSuccessSound(player);
                        BankMenu.openMain(serverPlayer);
                    }
                }
                
                // --- 4. ITEM MENU (Backup logic for /sbitems) ---
                // If it's none of the above specific bank names, allow copy
                // (Optional: You can add a check for "SkyBlock Items" title if needed)
                else if (clickedItem.hasNbt() && clickedItem.getNbt().contains("SkyBlockData")) {
                    player.getInventory().offerOrDrop(clickedItem.copy());
                }
            }
        }
    }

    private void playSuccessSound(PlayerEntity player) {
        // Hypixel "Pling" Sound
        player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.MASTER, 1f, 2.0f);
    }
}
