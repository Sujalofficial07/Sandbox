package com.sujal.skyblock.core.mixin;

import com.sujal.skyblock.core.api.stats.SkyBlockStats;
import com.sujal.skyblock.core.api.stats.SkyBlockStatsAPI;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    
    @Inject(method = "getMovementSpeed", at = @At("RETURN"), cancellable = true)
    private void onGetMovementSpeed(CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return;
        }
        
        Optional<SkyBlockStats> statsOpt = SkyBlockStatsAPI.getStats(serverPlayer);
        if (statsOpt.isEmpty()) {
            return;
        }
        
        SkyBlockStats stats = statsOpt.get();
        double speedStat = stats.getSpeed();
        float vanillaSpeed = (float) (0.1 * (speedStat / 100.0));
        cir.setReturnValue(vanillaSpeed);
    }
}
