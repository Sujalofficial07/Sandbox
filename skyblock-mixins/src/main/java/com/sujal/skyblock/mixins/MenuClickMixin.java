package com.sujal.skyblock.mixins;

import com.sujal.skyblock.ui.handler.BankInteractionHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
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
        
        // Determine Menu Type via Title is tricky in Mixin, 
        // so we check inventory size (27 for Bank, 54 for Items/Profile).
        // A better way is to check the ScreenHandler type if we registered custom ones.
        
        Slot slot = handler.slots.get(slotIndex);
        
        // CHECK FOR BANK MENU (Size 27 and SimpleInventory)
        if (slot.inventory instanceof net.minecraft.inventory.SimpleInventory && slot.inventory.size() == 27) {
            // Cancel taking items
            ci.cancel();
            
            if (!player.getWorld().isClient && player instanceof ServerPlayerEntity serverPlayer) {
                boolean isRightClick = (button == 1);
                // Call our logic handler (using reflection or direct call if module visible)
                // Assuming classes are on classpath:
                 try {
                     Class.forName("com.sujal.skyblock.ui.handler.BankInteractionHandler")
                          .getMethod("handleBankClick", ServerPlayerEntity.class, int.class, boolean.class)
                          .invoke(null, serverPlayer, slotIndex, isRightClick);
                 } catch (Exception ignored) {
                     // Fallback if class not found (modular separation issue)
                 }
            }
        }
        
        // ... (Existing /sbitems logic for Size 54) ...
    }
}
