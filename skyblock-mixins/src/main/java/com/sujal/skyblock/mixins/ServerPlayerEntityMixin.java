package com.sujal.skyblock.mixins;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.stats.engine.StatCalculator;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Inject(method = "playerTick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        
        // Only run logic every 20 ticks (1 second) to save performance
        if (player.age % 20 != 0) return;

        // Get Profile
        if (player.getWorld() instanceof ServerWorld serverWorld) {
            ProfileManager manager = ProfileManager.getServerInstance(serverWorld);
            SkyblockProfile profile = manager.getProfile(player);

            // Sync Health logic
            double maxHealth = StatCalculator.getFinalStat(player, profile, StatType.HEALTH);
            
            // Override vanilla Max Health attribute safely
            player.getAttributeInstance(net.minecraft.entity.attribute.EntityAttributes.GENERIC_MAX_HEALTH)
                  .setBaseValue(maxHealth);
            
            // Sync Speed
            double speed = StatCalculator.getFinalStat(player, profile, StatType.SPEED);
            // Default speed is 0.1, Skyblock speed 100 = 0.1 walk speed.
            player.getAttributeInstance(net.minecraft.entity.attribute.EntityAttributes.GENERIC_MOVEMENT_SPEED)
                  .setBaseValue(speed / 1000.0);
        }
    }
}
