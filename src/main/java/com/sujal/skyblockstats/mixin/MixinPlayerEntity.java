package com.sujal.skyblockstats.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {

    /**
     * Hypixel SkyBlock mein Hunger se Health regen nahi hoti.
     * Health regen ek alag stat hai (jo humne Component mein handle kiya hai).
     * Isliye hum Vanilla "Food Healing" ko disable kar rahe hain.
     */
    @Inject(method = "canFoodHeal", at = @At("HEAD"), cancellable = true)
    private void disableVanillaRegen(CallbackInfoReturnable<Boolean> cir) {
        // Always return false: Food will NOT heal the player.
        // Hunger bar will still act as "fuel" for sprinting, but not for healing.
        cir.setReturnValue(false);
    }
}
