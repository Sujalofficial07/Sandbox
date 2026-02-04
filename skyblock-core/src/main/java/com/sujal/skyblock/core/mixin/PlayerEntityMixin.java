package com.sujal.skyblock.core.mixin;

import com.sujal.skyblock.core.SkyBlockCore;
import com.sujal.skyblock.core.api.stats.SkyBlockStats;
import com.sujal.skyblock.core.api.stats.SkyBlockStatsAPI;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    
    @Inject(method = "getAttributeValue", at = @At("RETURN"), cancellable = true)
    private void onGetAttributeValue(RegistryEntry<EntityAttribute> attribute, CallbackInfoReturnable<Double> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return;
        }
        
        Optional<SkyBlockStats> statsOpt = SkyBlockStatsAPI.getStats(serverPlayer);
        if (statsOpt.isEmpty()) {
            return;
        }
        
        SkyBlockStats stats = statsOpt.get();
        
        if (attribute.matches(EntityAttributes.GENERIC_MAX_HEALTH)) {
            cir.setReturnValue(stats.getMaxHealth());
        } else if (attribute.matches(EntityAttributes.GENERIC_MOVEMENT_SPEED)) {
            double speedStat = stats.getSpeed();
            double vanillaSpeed = 0.1 * (speedStat / 100.0);
            cir.setReturnValue(vanillaSpeed);
        } else if (attribute.matches(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
            cir.setReturnValue(stats.getStrength());
        }
    }
}
