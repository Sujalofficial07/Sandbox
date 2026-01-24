package com.sujal.skyblock.stats.engine;

import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.core.util.SBItemUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class StatSyncHandler {

    public static void register() {
        // Sync stats every tick
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                updatePlayerStats(player);
            }
        });
    }

    private static void updatePlayerStats(ServerPlayerEntity player) {
        ServerWorld world = (ServerWorld) player.getWorld();
        ProfileManager pm = ProfileManager.getServerInstance(world);
        SkyblockProfile profile = pm.getProfile(player);

        // 1. Calculate Totals (Base + Armor + Hand)
        double totalHealth = StatType.HEALTH.getBaseValue() + profile.getStatBonus(StatType.HEALTH);
        double totalSpeed = StatType.SPEED.getBaseValue() + profile.getStatBonus(StatType.SPEED);
        double totalIntel = StatType.INTELLIGENCE.getBaseValue() + profile.getStatBonus(StatType.INTELLIGENCE);

        // Check Armor/Hand Items for stats
        for (ItemStack stack : player.getArmorItems()) {
            if (SBItemUtils.isSkyblockItem(stack)) {
                totalHealth += SBItemUtils.getStat(stack, StatType.HEALTH);
                totalSpeed += SBItemUtils.getStat(stack, StatType.SPEED);
                totalIntel += SBItemUtils.getStat(stack, StatType.INTELLIGENCE);
            }
        }
        ItemStack hand = player.getMainHandStack();
        if (SBItemUtils.isSkyblockItem(hand)) {
            totalHealth += SBItemUtils.getStat(hand, StatType.HEALTH);
            totalSpeed += SBItemUtils.getStat(hand, StatType.SPEED);
            totalIntel += SBItemUtils.getStat(hand, StatType.INTELLIGENCE);
        }

        // 2. Apply to Vanilla Attributes
        
        // HEALTH: Update Max Health attribute
        var healthAttr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (healthAttr != null) {
            // Only update if changed to avoid lag
            if (Math.abs(healthAttr.getBaseValue() - totalHealth) > 0.1) {
                healthAttr.setBaseValue(totalHealth);
                // Heal player if max health increased significantly to prevent empty hearts
                if (player.getHealth() > totalHealth) player.setHealth((float) totalHealth);
            }
        }

        // SPEED: Vanilla Walk Speed is 0.1 default. Hypixel 100 Speed = 0.1.
        // Formula: Vanilla = Speed / 1000.0
        var speedAttr = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (speedAttr != null) {
            double vanillaSpeed = totalSpeed / 1000.0; 
            if (Math.abs(speedAttr.getBaseValue() - vanillaSpeed) > 0.001) {
                speedAttr.setBaseValue(vanillaSpeed);
            }
        }
        
        // MANA REGEN (Logic moved here for central control)
        profile.regenMana(totalIntel);
    }
}
