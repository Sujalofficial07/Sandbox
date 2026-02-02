package com.sujal.skyblock.skills.mixin;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.skills.api.SkillType;
import com.sujal.skyblock.skills.util.SkillUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(DamageSource source, CallbackInfo ci) {
        if (source.getAttacker() instanceof ServerPlayerEntity player) {
            LivingEntity target = (LivingEntity) (Object) this;
            
            ProfileManager pm = ProfileManager.getServerInstance(player.getServerWorld());
            SkyblockProfile profile = pm.getProfile(player);

            // Combat XP logic
            // In a real mod, XP depends on mob type/level.
            double xp = 5.0; // Default
            
            if (target.getType().getSpawnGroup() == net.minecraft.entity.SpawnGroup.MONSTER) {
                xp = 10.0;
            }
            if (target.getType().toString().contains("ender_dragon")) {
                xp = 500.0;
            }

            SkillUtil.addXp(player, profile, SkillType.COMBAT, xp);
        }
    }
}
