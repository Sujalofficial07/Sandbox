package com.sujal.skyblock.mixin.client;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    // Hide Vanilla Health/Food Bars
    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void cancelVanillaBars(CallbackInfo ci) {
        // Cancel vanilla rendering completely
        ci.cancel(); 
    }
}
