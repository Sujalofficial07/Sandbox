package com.sujal.skyblocksandbox.mixin;

import com.sujal.skyblocksandbox.gui.SkyblockMenus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {

    @Shadow public abstract void setCursorStack(ItemStack stack);
    @Shadow public abstract ItemStack getCursorStack();

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        ScreenHandler handler = (ScreenHandler) (Object) this;
        String title = "";
        
        // Try to get window title if possible, or check our internal tracking
        // For this sandbox, we check if the player is viewing one of our specific menus
        if (SkyblockMenus.isSkyblockMenu(player)) {
            
            // Allow clicking outside (dropping) or weird clicks to be safe, but cancel inside clicks
            if (slotIndex >= 0 && slotIndex < handler.slots.size()) {
                Slot slot = handler.slots.get(slotIndex);
                
                // Handle Button Clicks
                if (slot.hasStack()) {
                    SkyblockMenus.handleButtonClick(player, slot.getStack());
                }

                // ALWAYS Cancel the interaction so they can't take the item
                ci.cancel(); 
                
                // Force sync to stop "ghost items"
                player.getInventory().markDirty();
            }
        }
    }
}
