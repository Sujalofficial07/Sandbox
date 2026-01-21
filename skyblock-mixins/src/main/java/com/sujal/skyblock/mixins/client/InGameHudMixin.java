package com.sujal.skyblock.mixins.client;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    // This modifies the health value the renderer SEES.
    // We scale it so 2000 HP looks like 20 HP (10 Hearts).
    @ModifyVariable(method = "renderHealthBar", at = @At("STORE"), ordinal = 0)
    private float modifyHealthForDisplay(float originalHealth, PlayerEntity player) {
        float maxHealth = player.getMaxHealth();
        // If we have massive health, scale it down visually to 20 (10 hearts)
        if (maxHealth > 20) {
            return (originalHealth / maxHealth) * 20.0f;
        }
        return originalHealth;
    }

    @ModifyVariable(method = "renderHealthBar", at = @At("STORE"), ordinal = 1)
    private float modifyMaxHealthForDisplay(float originalMaxHealth) {
        if (originalMaxHealth > 20) {
            return 20.0f; // Visual Max Health is always 10 hearts
        }
        return originalMaxHealth;
    }
}
