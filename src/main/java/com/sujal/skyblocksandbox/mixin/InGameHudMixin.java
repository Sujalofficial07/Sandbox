package com.sujal.skyblocksandbox.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity; // Fixed: Added semicolon here
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    // Cancel Vanilla Health Rendering
    @Inject(method = "renderHealthBar", at = @At("HEAD"), cancellable = true)
    private void onRenderHealth(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        ci.cancel();
    }

    // Cancel Vanilla Food/Mount/Air Rendering (Status Bars)
    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void onRenderStatusBars(DrawContext context, CallbackInfo ci) {
        ci.cancel();
    }
    
    // Cancel Experience Bar (Optional)
    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    public void onRenderExperienceBar(DrawContext context, int x, CallbackInfo ci) {
        // ci.cancel(); // Uncomment this line if you want to hide the XP bar too
    }
}
