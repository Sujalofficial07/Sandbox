package com.sujal.skyblock.items.event;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.core.util.CooldownManager;
import com.sujal.skyblock.core.util.SBItemUtils;
import com.sujal.skyblock.items.registry.SkyblockRegistry;
import com.sujal.skyblock.items.api.SBItem;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypedActionResult;

public class SkyblockAbilityHandler {

    public static void register() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (world.isClient) return TypedActionResult.pass(stack);

            if (!SBItemUtils.isSkyblockItem(stack)) return TypedActionResult.pass(stack);
            
            String id = SBItemUtils.getString(stack, "SBID");
            SBItem sbItem = SkyblockRegistry.getById(id);

            if (sbItem != null && sbItem.getAbility() != null) {
                String abilityName = sbItem.getAbility().getName();
                
                // 1. Check Cooldown
                if (CooldownManager.isOnCooldown(player, abilityName)) {
                    CooldownManager.sendCooldownMessage(player, abilityName);
                    return TypedActionResult.fail(stack);
                }

                ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) world);
                SkyblockProfile profile = pm.getProfile(player);
                
                // 2. Check Mana
                int cost = sbItem.getAbility().getManaCost();
                if (profile.getCurrentMana() >= cost) {
                    // Consume & Execute
                    profile.consumeMana(cost);
                    sbItem.getAbility().onActivate(player, world);
                    
                    // Set Cooldown
                    CooldownManager.setCooldown(player, abilityName, sbItem.getAbility().getCooldown());
                    
                    // Success Message
                    CooldownManager.sendAbilityMessage(player, abilityName, cost);
                } else {
                    CooldownManager.sendManaMessage(player, cost);
                }
                
                return TypedActionResult.success(stack);
            }

            return TypedActionResult.pass(stack);
        });
    }
}
