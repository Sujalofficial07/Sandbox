package com.sujal.skyblock.mixins.client;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    // 1. Redirect Current Health
    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getHealth()F"))
    private float fakeHealth(PlayerEntity player) {
        float realMax = player.getMaxHealth();
        float realCurrent = player.getHealth();

        // If health is huge, scale it to 20 visually
        if (realMax > 20) {
            return (realCurrent / realMax) * 20.0f;
        }
        return realCurrent;
    }

    // 2. Redirect Max Health (Uses getAttributeValue in 1.20.1, not getMaxHealth)
    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"))
    private double fakeMaxHealth(PlayerEntity player, EntityAttribute attribute) {
        // Only fake the Max Health attribute
        if (attribute == EntityAttributes.GENERIC_MAX_HEALTH) {
            return 20.0;
        }
        // Return normal value for other attributes
        return player.getAttributeValue(attribute);
    }
}
