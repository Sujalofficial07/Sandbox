package com.sujal.skyblockstats.damage;

import com.sujal.skyblockstats.api.SkyBlockAPI;
import com.sujal.skyblockstats.stats.SkyBlockStatType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;

public class FerocityHandler {

    public static void handleFerocity(PlayerEntity attacker, LivingEntity target, float damagePerHit) {
        double ferocity = SkyBlockAPI.getStats(attacker).getStatValue(SkyBlockStatType.FEROCITY);
        
        if (ferocity <= 0) return;

        int extraStrikes = (int) (ferocity / 100.0);
        double chanceForOneMore = ferocity % 100.0;

        if (Math.random() * 100 < chanceForOneMore) {
            extraStrikes++;
        }

        if (extraStrikes > 0) {
            final int hits = extraStrikes;
            // In a real game tick loop, this should be delayed. 
            // For this engine, we apply immediately but with sound cues.
            for (int i = 0; i < hits; i++) {
                if (target.isDead()) break;
                
                // Ferocity hits usually deal the same damage but don't proc ferocity again
                target.damage(attacker.getWorld().getDamageSources().playerAttack(attacker), damagePerHit);
                
                attacker.getWorld().playSound(null, target.getX(), target.getY(), target.getZ(), 
                    SoundEvents.ENTITY_IRON_GOLEM_ATTACK, SoundCategory.PLAYERS, 1.0f, 2.0f);
            }
        }
    }
}
