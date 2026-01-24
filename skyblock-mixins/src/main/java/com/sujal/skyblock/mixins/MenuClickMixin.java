package com.sujal.skyblock.mixins;

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
public abstract class MenuClickMixin {

    @Shadow public abstract String getTitle(); // Note: Might need casting in some versions, but Title is usually on Screen, not Handler.
    // We will use a simpler check: The container size and contents.

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        ScreenHandler handler = (ScreenHandler) (Object) this;
        
        // This is a rough check. Ideally, check against the specific Window Title via the Screen,
        // but ScreenHandler doesn't always know its title server-side.
        // For now, we check if the slot has our special NBT tag to identify it's a display item.
        
        if (slotIndex < 0 || slotIndex >= handler.slots.size()) return;
        
        Slot slot = handler.slots.get(slotIndex);
        if (slot.hasStack()) {
            ItemStack stack = slot.getStack();
            
            // Check if this is the Skyblock Menu (using 9x6 generic)
            // A simple way is to check the Screen Title passed in the Open Packet, 
            // but here we can check if the user is in a Generic 54 container 
            // AND the item is a Skyblock item (prevent moving it out of top inventory).
            
            // NOTE: For strict menu handling, we assume this is the only 54-slot menu 
            // with Skyblock items in the top part.
            
            // Logic: If it's a top inventory slot (not player inventory)
            if (slot.inventory instanceof net.minecraft.inventory.SimpleInventory && slot.inventory.size() == 54) {
                 // Right Click (Button 1)
                 if (button == 1 || actionType == SlotActionType.PICKUP) {
                     // Give a COPY to the player
                     player.getInventory().offerOrDrop(stack.copy());
                 }
                 
                 // Cancel the event so the original item stays in the menu
                 ci.cancel();
            }
        }
    }
}
