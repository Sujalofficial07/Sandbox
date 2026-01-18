package com.sujal.skyblockstats.registry;

import com.sujal.skyblockstats.damage.DamageCalculator;
import com.sujal.skyblockstats.damage.FerocityHandler;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public class DamageRegistry {
    public static void init() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClient && entity instanceof LivingEntity target) {
                
                // 1. Calculate Custom Damage
                float customDamage = DamageCalculator.calculateDamage(player, target);
                
                // 2. Apply Damage (Bypassing vanilla strength logic slightly)
                target.damage(world.getDamageSources().playerAttack(player), customDamage);
                
                // 3. Handle Ferocity (Extra hits)
                FerocityHandler.handleFerocity(player, target, customDamage);
                
                // Cancel vanilla attack to avoid double hits, or return PASS if you want vanilla effects (knockback)
                // Returning PASS usually works best if we modified the damage source, 
                // but since we called damage() manually, we suppress vanilla damage logic.
                return ActionResult.SUCCESS; 
            }
            return ActionResult.PASS;
        });
    }
}
