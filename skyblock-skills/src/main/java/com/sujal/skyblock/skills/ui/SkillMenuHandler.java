package com.sujal.skyblock.skills.ui;

import com.sujal.skyblock.skills.api.SkillType;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.TypedActionResult;

public class SkillMenuHandler {
    
    // Call this from your MenuClickMixin or register a similar mixin in this module
    public static boolean handleMenuClick(ServerPlayerEntity player, ItemStack clicked, String inventoryTitle) {
        
        if (inventoryTitle.equals("Your Skills")) {
            // Check which skill was clicked
            for (SkillType skill : SkillType.values()) {
                if (clicked.getName().getString().contains(skill.getName())) {
                    SkillMenu.openSkillDetail(player, skill);
                    return true;
                }
            }
            if (clicked.getItem() == Items.BARRIER) player.closeHandledScreen();
            return true;
        }
        
        if (inventoryTitle.endsWith("Skill")) { // e.g., "Farming Skill"
            if (clicked.getName().getString().contains("Go Back")) {
                SkillMenu.openMainMenu(player);
                return true;
            }
        }
        
        return false;
    }
}
