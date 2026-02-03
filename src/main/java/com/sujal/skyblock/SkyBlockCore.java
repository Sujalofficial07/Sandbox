package com.sujal.skyblock;

import com.sujal.skyblock.core.stats.StatManager;
import com.sujal.skyblock.core.data.DataHolder;
import com.sujal.skyblock.core.data.PlayerProfileData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class SkyBlockCore implements ModInitializer {
    
    public static final String MOD_ID = "skyblock-core";

    @Override
    public void onInitialize() {
        System.out.println("Initializing SkyBlock Core - Foundation of the World");

        // Global Mana Regen Tick
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                regenMana(player);
                regenHealth(player);
            }
        });
    }

    private void regenMana(ServerPlayerEntity player) {
        PlayerProfileData data = ((DataHolder) player).getSkyBlockData();
        double maxMana = StatManager.INSTANCE.getMaxMana(player);
        double current = data.getCurrentMana();
        
        if (current < maxMana) {
            // 2% per second approx logic (simplified)
            double regen = maxMana * 0.02 / 20.0; 
            data.setCurrentMana(Math.min(maxMana, current + regen));
        }
    }

    private void regenHealth(ServerPlayerEntity player) {
        PlayerProfileData data = ((DataHolder) player).getSkyBlockData();
        double maxHp = StatManager.INSTANCE.getMaxHealth(player);
        double current = data.getCurrentHealth();

        if (current < maxHp) {
            // Base regen + scaling
            double regen = (maxHp * 0.01 / 20.0) + 0.05; 
            data.setCurrentHealth(Math.min(maxHp, current + regen));
            
            // Sync vanilla health for visual shake reduction if needed
            // player.setHealth((float) (current / maxHp * 20)); 
        }
    }
}
