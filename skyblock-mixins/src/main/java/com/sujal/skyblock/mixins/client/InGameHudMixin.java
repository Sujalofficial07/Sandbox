package com.sujal.skyblock.mixins.client;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    // Intercept when the HUD checks for Max Health
    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getMaxHealth()F"))
    private float fakeMaxHealth(PlayerEntity player) {
        // Visually, the player always has 20 health (10 hearts) max
        return 20.0f;
    }

    // Intercept when the HUD checks for Current Health
    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getHealth()F"))
    private float fakeHealth(PlayerEntity player) {
        float realMax = player.getMaxHealth();
        float realCurrent = player.getHealth();

        // If health is huge (e.g., 500/1000), scale it to fit 20 (e.g., 10/20)
        if (realMax > 20) {
            return (realCurrent / realMax) * 20.0f;
        }
        return realCurrent;
    }
}
