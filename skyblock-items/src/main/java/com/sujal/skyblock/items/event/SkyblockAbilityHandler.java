package com.sujal.skyblock.items.event;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.core.util.SBItemUtils;
import com.sujal.skyblock.items.registry.SkyblockRegistry;
import com.sujal.skyblock.items.api.SBItem;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.TypedActionResult;

public class SkyblockAbilityHandler {

    public static void register() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            
            // 1. Check if it's a SkyBlock Item
            if (!SBItemUtils.isSkyblockItem(stack)) return TypedActionResult.pass(stack);

            // 2. Get Item ID
            String id = SBItemUtils.getString(stack, "SBID");
            SBItem sbItem = SkyblockRegistry.getById(id);

            if (sbItem != null && sbItem.getAbility() != null) {
                if (!world.isClient) {
                    // 3. Mana Check
                    ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) world);
                    SkyblockProfile profile = pm.getProfile(player);
                    
                    int cost = sbItem.getAbility().getManaCost();
                    if (profile.getCurrentMana() >= cost) {
                        // Consume Mana
                        profile.consumeMana(cost);
                        
                        // Execute Ability
                        sbItem.getAbility().onActivate(player, world);
                        
                        // Notify User (Action Bar)
                        player.sendMessage(Text.of("§b-" + cost + " Mana (§6" + sbItem.getAbility().getName() + "§b)"), true);
                    } else {
                        player.sendMessage(Text.of("§cNot enough Mana!"), true);
                    }
                }
                return TypedActionResult.success(stack);
            }

            return TypedActionResult.pass(stack);
        });
    }
}
