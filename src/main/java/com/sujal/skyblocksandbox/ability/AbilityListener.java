package com.sujal.skyblocksandbox.ability;

import com.sujal.skyblocksandbox.combat.DamageCalculator;
import com.sujal.skyblocksandbox.combat.DamageIndicator;
import com.sujal.skyblocksandbox.stats.StatsHandler;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AbilityListener {

    public static void register() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (hand != Hand.MAIN_HAND || world.isClient) return TypedActionResult.pass(ItemStack.EMPTY);

            ItemStack stack = player.getMainHandStack();
            if (!stack.hasNbt()) return TypedActionResult.pass(stack);

            NbtCompound nbt = stack.getNbt();
            if (nbt.contains("ExtraAttributes")) {
                String id = nbt.getCompound("ExtraAttributes").getString("id");
                handleAbility(player, world, id, stack);
            }

            return TypedActionResult.pass(stack);
        });
    }

    private static void handleAbility(PlayerEntity player, World world, String id, ItemStack stack) {
        
        // --- HYPERION & VARIANTS (Wither Impact) ---
        if (id.equals("HYPERION") || id.equals("VALKYRIE") || id.equals("ASTRAEA") || id.equals("SCYLLA")) {
            if (CooldownManager.isOnCooldown(player, "WITHER_IMPACT")) return;
            
            if (StatsHandler.consumeMana(player, 300)) { // 300 Mana Cost
                // 1. Teleport Logic (10 Blocks)
                Vec3d look = player.getRotationVector();
                Vec3d dest = player.getPos().add(look.x * 10, look.y * 10, look.z * 10);
                
                // Simple Raycast check to not go through walls (Simplified)
                HitResult hit = player.raycast(10, 0, false);
                if (hit.getType() == HitResult.Type.BLOCK) {
                    dest = hit.getPos();
                }
                
                player.teleport(dest.x, dest.y, dest.z);
                player.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 2.0f);
                
                // 2. Damage Logic (Radius 6)
                double damage = 10000; // Base 10k
                // Scale with Intelligence in real skyblock (damage * (1 + mana/100))
                double scaling = 1 + (StatsHandler.getMaxMana(player) / 100.0);
                double finalDmg = damage * scaling;

                world.getEntitiesByClass(MobEntity.class, new Box(player.getBlockPos()).expand(6), e -> true)
                        .forEach(mob -> {
                            mob.damage(world.getDamageSources().magic(), (float) finalDmg);
                            DamageIndicator.spawn(mob, finalDmg, false); // Grey crit for magic usually
                        });

                // 3. Particles
                // Note: Server-side particle spawning requires ServerWorld casting
                ((net.minecraft.server.world.ServerWorld)world).spawnParticles(
                        ParticleTypes.EXPLOSION, dest.x, dest.y + 1, dest.z, 5, 1, 1, 1, 0.1);
                
                // No cooldown on Hyperion usually, but let's put 150ms to prevent packet spam
                CooldownManager.setCooldownMillis(player, "WITHER_IMPACT", 150);
            }
        }

        // --- ASPECT OF THE END / VOID ---
        if (id.equals("ASPECT_OF_THE_END") || id.equals("ASPECT_OF_THE_VOID")) {
            double range = id.equals("ASPECT_OF_THE_VOID") ? 10.0 : 8.0; // 8 blocks for AOTE, 10 for AOTV
            
            if (StatsHandler.consumeMana(player, 50)) {
                HitResult hit = player.raycast(range, 0, false);
                Vec3d target = hit.getPos();
                
                // Teleport
                player.teleport(target.x, target.y, target.z);
                player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                
                // Speed Effect (Implementation omitted for brevity, use player.addStatusEffect)
            }
        }

        // --- SHORTBOWS (Terminator / Juju) ---
        if (id.equals("TERMINATOR") || id.equals("JUJU_SHORTBOW")) {
            if (CooldownManager.isOnCooldown(player, "SHORTBOW")) return;
            
            // Shortbows shoot instantly
            int arrows = id.equals("TERMINATOR") ? 3 : 1;
            
            for (int i = 0; i < arrows; i++) {
                ArrowEntity arrow = new ArrowEntity(world, player);
                arrow.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 3.0F, 1.0F);
                
                // Spread for Terminator
                if (arrows > 1) {
                    arrow.setVelocity(arrow.getVelocity().rotateY((float) Math.toRadians((i - 1) * 10)));
                }

                // Critical Logic
                DamageCalculator.DamageResult dmg = DamageCalculator.calculateDamage(player);
                arrow.setDamage(dmg.damage / 5.0); // Arrows deal base damage * velocity usually
                if (dmg.isCrit) arrow.setCritical(true);
                
                world.spawnEntity(arrow);
            }
            
            player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);
            CooldownManager.setCooldownMillis(player, "SHORTBOW", id.equals("TERMINATOR") ? 300 : 500);
        }
        
        // --- BOOM BOOM SWORDS (Giant's Sword) ---
        if (id.equals("GIANTS_SWORD")) {
            if (CooldownManager.isOnCooldown(player, "GIANT_SLAM")) {
                CooldownManager.sendCooldownMessage(player);
                return;
            }
            
            if (StatsHandler.consumeMana(player, 100)) {
                player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0f, 0.5f);
                
                // Giant Slam Damage
                world.getEntitiesByClass(MobEntity.class, new Box(player.getBlockPos()).expand(5), e -> true)
                        .forEach(mob -> {
                            double dmg = 100000; // Big number
                            mob.damage(world.getDamageSources().generic(), (float)dmg);
                            DamageIndicator.spawn(mob, dmg, true);
                        });
                        
                CooldownManager.setCooldown(player, "GIANT_SLAM", 30);
            }
        }
    }
}
