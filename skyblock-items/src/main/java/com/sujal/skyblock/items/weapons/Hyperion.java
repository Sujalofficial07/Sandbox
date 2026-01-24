package com.sujal.skyblock.items.weapons;

import com.sujal.skyblock.core.api.ItemType;
import com.sujal.skyblock.core.api.Rarity;
import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.items.api.Ability;
import com.sujal.skyblock.items.api.SBItem;
import com.sujal.skyblock.stats.engine.DamageCalculator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hyperion extends SBItem {

    @Override
    public String getId() { return "HYPERION"; }

    @Override
    public String getDisplayName() { return "Hyperion"; }

    @Override
    public Item getMaterial() { return Items.IRON_SWORD; }

    @Override
    public Rarity getRarity() { return Rarity.LEGENDARY; }

    @Override
    public ItemType getType() { return ItemType.SWORD; }

    @Override
    public Map<StatType, Double> getStats() {
        Map<StatType, Double> stats = new HashMap<>();
        // Stats based on "Clean Hyperion" (No reforges)
        stats.put(StatType.DAMAGE, 260.0);
        stats.put(StatType.STRENGTH, 150.0);
        stats.put(StatType.INTELLIGENCE, 350.0);
        stats.put(StatType.FEROCITY, 30.0);
        return stats;
    }

    @Override
    public Ability getAbility() {
        return new Ability("Wither Impact", 
            "Teleport 10 blocks ahead and deal massive damage\nto nearby enemies.\nAlso grants an Absorption shield.", 
            300, 0) { // 300 Mana, 0 Cooldown (Hypixel style)
            
            @Override
            public void onActivate(PlayerEntity player, World world) {
                if (world.isClient) return;

                // 1. Teleport Logic (Raycast to avoid walls)
                Vec3d start = player.getCameraPosVec(1.0f);
                Vec3d direction = player.getRotationVector();
                double range = 10.0;
                Vec3d end = start.add(direction.multiply(range));

                HitResult hit = world.raycast(new RaycastContext(
                        start, end, 
                        RaycastContext.ShapeType.COLLIDER, 
                        RaycastContext.FluidHandling.NONE, 
                        player));

                Vec3d dest = hit.getPos();
                
                // Move slightly back from the wall to prevent suffocation
                if (hit.getType() != HitResult.Type.MISS) {
                    dest = dest.subtract(direction.multiply(0.5));
                }

                // Play Sound & Particles at OLD location
                world.playSound(null, player.getX(), player.getY(), player.getZ(), 
                        SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);

                // Teleport
                player.teleport(dest.x, dest.y, dest.z);
                
                // Play Sound & Particles at NEW location
                world.playSound(null, player.getX(), player.getY(), player.getZ(), 
                        SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                ((ServerWorld) world).spawnParticles(ParticleTypes.EXPLOSION, dest.x, dest.y + 1, dest.z, 1, 0, 0, 0, 0);

                // 2. Damage Logic (AoE)
                double radius = 7.0;
                List<Entity> targets = world.getOtherEntities(player, 
                        new Box(dest.x - radius, dest.y - radius, dest.z - radius, 
                                dest.x + radius, dest.y + radius, dest.z + radius));

                ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) world);
                SkyblockProfile profile = pm.getProfile(player);
                
                // Wither Impact Base Damage = 10,000 * Ability Scaling
                double baseDmg = 10000.0;
                double scaling = 0.3; // 0.3 scaling per 100 Intel
                float magicDmg = DamageCalculator.calculateMagicDamage(player, profile, baseDmg, scaling);

                for (Entity entity : targets) {
                    if (entity instanceof LivingEntity livingTarget && entity.distanceTo(player) <= radius) {
                        livingTarget.damage(world.getDamageSources().magic(), magicDmg);
                    }
                }

                // 3. Shield Logic (Wither Shield)
                // Grants 150% of Crit Damage as Absorption for 5 seconds
                // For simplicity here, we give a fixed amount or rely on stats
                float absorptionAmount = 10.0f; // 5 Hearts
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1)); // 5 seconds
                player.setAbsorptionAmount(Math.min(player.getAbsorptionAmount() + absorptionAmount, 20.0f)); // Cap at 10 hearts
            }
        };
    }
}
