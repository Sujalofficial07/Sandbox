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
public class MenuClickMixin {

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        // Check if the open screen is our Custom Menu (checking by Title is risky but simple for now)
        // In production, use a custom ScreenHandler class check.
        
        // Note: We can't easily check Title here directly without casting.
        // A safer way is checking if the inventory is the one we created.
        
        // For this code to work strictly:
        // We will just protect the item if the player is holding SHIFT (Quick Move) 
        // or ensure we only copy items in the Skyblock Menu.
        
        // Since this Mixin affects ALL inventories, we must be careful.
        // I will provide the Event approach in the MenuHandler if using a Library, 
        // BUT for vanilla Fabric, implementing a custom ScreenHandler is best.
    }
}
