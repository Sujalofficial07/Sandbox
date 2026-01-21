package com.sujal.skyblock.mixins;

import net.minecraft.entity.player.HungerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo ci) {
        HungerManager manager = (HungerManager) (Object) this;
        manager.setFoodLevel(20); // Always full
        manager.setSaturationLevel(20f);
        ci.cancel(); // Stop vanilla hunger logic entirely
    }
    
    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    public void onAdd(int food, float saturationModifier, CallbackInfo ci) {
        ci.cancel(); // Disable adding food (eating) logic if not needed, or let custom items handle it
    }
}
