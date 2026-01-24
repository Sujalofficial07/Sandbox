package com.sujal.skyblock.items.factory;

import com.sujal.skyblock.core.api.ItemType;
import com.sujal.skyblock.core.api.Rarity;
import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.util.SBItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.Map;

public class ItemGenerator {

    public static ItemStack createItem(Item vanillaItem, String name, Rarity rarity, ItemType type, Map<StatType, Double> stats, String abilityName, String abilityDesc) {
        ItemStack stack = new ItemStack(vanillaItem);
        
        // 1. Set Name with Rarity Color
        stack.setCustomName(Text.literal(name).formatted(rarity.getColor()));
        
        // 2. Set Core SkyBlock Data
        SBItemUtils.setRarity(stack, rarity);
        SBItemUtils.setType(stack, type);
        
        // 3. Set Stats
        if (stats != null) {
            stats.forEach((stat, val) -> SBItemUtils.setStat(stack, stat, val));
        }

        // 4. Set Ability
        if (abilityName != null && !abilityName.isEmpty()) {
            SBItemUtils.setAbility(stack, abilityName, abilityDesc);
        }
        
        // Note: We DO NOT generate lore here anymore. 
        // The ItemStackTooltipMixin handles rendering dynamically.
        
        return stack;
    }
}
