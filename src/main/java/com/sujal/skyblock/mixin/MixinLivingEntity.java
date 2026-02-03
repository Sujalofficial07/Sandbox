package com.sujal.skyblock.mixin;

import com.sujal.skyblock.core.stats.StatManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    /**
     * Override vanilla damage calculation with SkyBlock Defense formula.
     */
    @ModifyVariable(method = "applyDamage", at = @At("HEAD"), argsOnly = true)
    private float modifyIncomingDamage(float amount) {
        if ((Object) this instanceof PlayerEntity player) {
            double defense = StatManager.INSTANCE.getDefense(player);
            return StatManager.INSTANCE.calculateDamageReduction(amount, defense);
        }
        return amount;
    }
    
    /**
     * Override vanilla health logic partially or handle death events customly.
     */
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // Here we can intercept damage completely to handle Custom Health
        // For now, we allow vanilla flow but with modified values
    }
}
