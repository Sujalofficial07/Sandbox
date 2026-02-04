package com.sujal.skyblock.core.mixin;

import com.sujal.skyblock.core.SkyBlockCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
    
    @Inject(method = "onBreak", at = @At("HEAD"), cancellable = true)
    private void onBlockBreak(net.minecraft.world.World world, BlockPos pos, BlockState state, net.minecraft.entity.player.PlayerEntity player, CallbackInfo ci) {
        if (!(world instanceof ServerWorld serverWorld)) {
            return;
        }
        
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return;
        }
        
        SkyBlockCore core = SkyBlockCore.getInstance();
        if (core == null) {
            return;
        }
        
        boolean canBreak = core.getProtectionManager().canBreakBlock(serverPlayer, serverWorld, pos);
        if (!canBreak) {
            ci.cancel();
        }
    }
}
