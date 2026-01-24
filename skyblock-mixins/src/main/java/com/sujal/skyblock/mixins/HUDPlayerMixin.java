package com.sujal.skyblock.mixins;

import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.stats.engine.StatCalculator;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class HUDPlayerMixin {

    @Inject(method = "playerTick", at = @At("TAIL"))
    private void updateSkyblockHUD(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (player.age % 5 != 0) return; // Update every 5 ticks to reduce lag

        ServerWorld world = (ServerWorld) player.getWorld();
        ProfileManager pm = ProfileManager.getServerInstance(world);
        SkyblockProfile profile = pm.getProfile(player);

        // Calculate Max Stats
        double maxHealth = StatCalculator.getFinalStat(player, profile, StatType.HEALTH);
        double defense = StatCalculator.getFinalStat(player, profile, StatType.DEFENSE);
        double maxMana = StatCalculator.getFinalStat(player, profile, StatType.INTELLIGENCE);
        
        // Regen Logic (Simple implementation here)
        profile.regenMana(maxMana);

        // Format: ❤ Health   ❈ Defense   ✎ Mana
        String hudText = String.format("§c❤ %.0f/%.0f   §a❈ %.0f Def   §b✎ %.0f/%.0f Mana",
                player.getHealth(), maxHealth,
                defense,
                profile.getCurrentMana(), maxMana);

        // Send to Action Bar
        player.sendMessage(Text.of(hudText), true);
    }
}
