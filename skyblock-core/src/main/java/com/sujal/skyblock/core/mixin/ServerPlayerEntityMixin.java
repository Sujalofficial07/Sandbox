package com.sujal.skyblock.core.mixin;

import com.mojang.authlib.GameProfile;
import com.sujal.skyblock.core.SkyBlockCore;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        
        if (player.getServer() != null && player.age % 20 == 0) {
            SkyBlockCore core = SkyBlockCore.getInstance();
            if (core != null && core.getZoneManager() != null) {
                core.getZoneManager().updatePlayerZone(player);
            }
        }
    }
}
