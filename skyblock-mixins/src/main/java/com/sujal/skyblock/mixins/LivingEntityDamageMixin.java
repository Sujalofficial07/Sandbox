package com.sujal.skyblock.mixins;

import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.stats.engine.StatCalculator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityDamageMixin {

    // Inject logic when damage is applied
    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    private float modifyDamageForDefense(float damage, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) this;

        // Only apply defense if the entity taking damage is a player
        if (entity instanceof PlayerEntity player && !entity.getWorld().isClient) {
            ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) entity.getWorld());
            SkyblockProfile profile = pm.getProfile(player);

            double defense = StatCalculator.getFinalStat(player, profile, StatType.DEFENSE);
            
            // Hypixel Formula: Damage / (1 + Defense/100)
            // Example: 100 Defense = 50% damage reduction.
            float reduction = (float) (1 + (defense / 100.0));
            return damage / reduction;
        }
        return damage;
    }
}
