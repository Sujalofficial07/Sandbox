package com.sujal.skyblock.core.mixin;

import com.sujal.skyblock.core.api.stats.SkyBlockStats;
import com.sujal.skyblock.core.api.stats.SkyBlockStatsAPI;
import com.sujal.skyblock.core.internal.protection.VanillaOverrideHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    
    @ModifyVariable(
        method = "damage",
        at = @At("HEAD"),
        argsOnly = true
    )
    private float modifyDamage(float amount, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        if (!(entity instanceof ServerPlayerEntity player)) {
            return amount;
        }
        
        if (!VanillaOverrideHandler.shouldOverrideDamage()) {
            return amount;
        }
        
        Optional<SkyBlockStats> statsOpt = SkyBlockStatsAPI.getStats(player);
        if (statsOpt.isEmpty()) {
            return amount;
        }
        
        SkyBlockStats stats = statsOpt.get();
        double reducedDamage = SkyBlockStatsAPI.calculateDefenseReduction(player, amount);
        
        stats.setHealth(stats.getHealth() - reducedDamage);
        
        return 0.0f;
    }
    
    @Inject(method = "getHealth", at = @At("RETURN"), cancellable = true)
    private void onGetHealth(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        if (!(entity instanceof ServerPlayerEntity player)) {
            return;
        }
        
        if (!VanillaOverrideHandler.shouldCancelVanillaHealth()) {
            return;
        }
        
        Optional<SkyBlockStats> statsOpt = SkyBlockStatsAPI.getStats(player);
        statsOpt.ifPresent(stats -> cir.setReturnValue((float) stats.getHealth()));
    }
    
    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
    private void onGetMaxHealth(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        if (!(entity instanceof ServerPlayerEntity player)) {
            return;
        }
        
        if (!VanillaOverrideHandler.shouldCancelVanillaHealth()) {
            return;
        }
        
        Optional<SkyBlockStats> statsOpt = SkyBlockStatsAPI.getStats(player);
        statsOpt.ifPresent(stats -> cir.setReturnValue((float) stats.getMaxHealth()));
    }
}
