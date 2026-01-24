package com.sujal.skyblock.mixins;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.stats.engine.DamageCalculator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At("HEAD"))
    public void onAttack(Entity target, CallbackInfo ci) {
        // Ensure we are on the server side and attacking a valid LivingEntity
        if (!this.getWorld().isClient && target instanceof LivingEntity) {
            
            ServerWorld world = (ServerWorld) this.getWorld();
            PlayerEntity player = (PlayerEntity) (Object) this;

            // 1. Get Player Profile
            ProfileManager profileManager = ProfileManager.getServerInstance(world);
            SkyblockProfile profile = profileManager.getProfile(player);

            // 2. Check for Critical Hit
            boolean isCrit = DamageCalculator.checkCrit(player, profile);

            // 3. Calculate Final Damage using Hypixel Formula
            // FIXED: Method name updated to 'calculateMeleeDamage'
            float finalDamage = DamageCalculator.calculateMeleeDamage(player, profile, isCrit);

            // 4. Override Vanilla Attribute
            // We set the base value of GENERIC_ATTACK_DAMAGE to our calculated value.
            this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(finalDamage);
        }
    }
}
