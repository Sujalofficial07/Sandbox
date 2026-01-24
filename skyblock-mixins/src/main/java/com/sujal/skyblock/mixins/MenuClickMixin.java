package com.sujal.skyblock.mixins;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public abstract class MenuClickMixin {

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        ScreenHandler handler = (ScreenHandler) (Object) this;
        
        // Safety check: Slot index must be valid
        if (slotIndex < 0 || slotIndex >= handler.slots.size()) return;
        
        Slot slot = handler.slots.get(slotIndex);
        
        // Check if the slot has an item
        if (slot.hasStack()) {
            ItemStack stack = slot.getStack();
            
            // LOGIC: Check if this is our Custom Menu
            // Humne MenuHandler mein 'SimpleInventory' use kiya tha jiska size 54 hai.
            // Vanilla chests bhi yehi use karte hain, lekin Skyblock items ke saath yeh safe guess hai.
            if (slot.inventory instanceof net.minecraft.inventory.SimpleInventory && slot.inventory.size() == 54) {
                 
                 // Agar player Top Inventory (Chest) mein click kar raha hai
                 if (slot.inventory != player.getInventory()) {
                     
                     // Item Copy Logic (Give to player)
                     // Button 0 = Left Click, Button 1 = Right Click
                     if (actionType == SlotActionType.PICKUP || actionType == SlotActionType.QUICK_MOVE) {
                         player.getInventory().offerOrDrop(stack.copy());
                     }
                     
                     // IMPORTANT: Cancel the event so the original item stays in the menu
                     ci.cancel();
                 }
            }
        }
    }
}
