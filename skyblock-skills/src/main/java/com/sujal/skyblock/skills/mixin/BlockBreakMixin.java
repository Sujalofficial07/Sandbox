package com.sujal.skyblock.skills.mixin;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.skills.api.SkillType;
import com.sujal.skyblock.skills.util.SkillUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class BlockBreakMixin {

    @Inject(method = "tryBreakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V"))
    private void onBlockBreak(BlockPos pos, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        ServerWorld world = player.getServerWorld();
        BlockState state = world.getBlockState(pos);

        ProfileManager pm = ProfileManager.getServerInstance(world);
        SkyblockProfile profile = pm.getProfile(player);

        // Farming Logic
        if (state.isOf(Blocks.WHEAT) || state.isOf(Blocks.CARROTS) || state.isOf(Blocks.POTATOES)) {
            SkillUtil.addXp(player, profile, SkillType.FARMING, 4.0);
        }
        else if (state.isOf(Blocks.PUMPKIN) || state.isOf(Blocks.MELON)) {
            SkillUtil.addXp(player, profile, SkillType.FARMING, 4.5);
        }

        // Mining Logic
        else if (state.isOf(Blocks.STONE) || state.isOf(Blocks.COBBLESTONE)) {
            SkillUtil.addXp(player, profile, SkillType.MINING, 1.0);
        }
        else if (state.isOf(Blocks.DIAMOND_ORE) || state.isOf(Blocks.DEEPSLATE_DIAMOND_ORE)) {
            SkillUtil.addXp(player, profile, SkillType.MINING, 10.0);
        }
        else if (state.isOf(Blocks.COAL_ORE)) {
            SkillUtil.addXp(player, profile, SkillType.MINING, 2.0);
        }

        // Foraging Logic
        else if (state.isIn(net.minecraft.registry.tag.BlockTags.LOGS)) {
            SkillUtil.addXp(player, profile, SkillType.FORAGING, 6.0);
        }
    }
}
