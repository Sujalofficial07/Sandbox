package com.sujal.skyblocksandbox.mixin;

import com.sujal.skyblocksandbox.combat.DamageCalculator;
import com.sujal.skyblocksandbox.combat.DamageIndicator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    // Variable to hold our calculated damage temporarily
    private DamageCalculator.DamageResult lastCalculation;

    // 1. Calculate damage BEFORE the attack logic runs
    @Inject(method = "attack", at = @At("HEAD"))
    private void onAttackHead(Entity target, CallbackInfo ci) {
        if (target instanceof LivingEntity) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            // Calculate Custom Damage
            this.lastCalculation = DamageCalculator.calculateDamage(player);
        }
    }

    // 2. Override the damage value used in the attack
    // In Vanilla, 'float f' is the base damage (Strength effect included).
    // We replace 'f' with our calculated value.
    @ModifyVariable(method = "attack", at = @At(value = "STORE"), ordinal = 0)
    private float modifyDamage(float originalDamage) {
        if (this.lastCalculation != null) {
            return (float) this.lastCalculation.damage;
        }
        return originalDamage;
    }

    // 3. Spawn Indicator AFTER attack succeeds
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void onDamageApplied(Entity target, CallbackInfo ci) {
        if (this.lastCalculation != null) {
            DamageIndicator.spawn(target, this.lastCalculation.damage, this.lastCalculation.isCrit);
            // Reset for safety
            this.lastCalculation = null;
        }
    }
}
