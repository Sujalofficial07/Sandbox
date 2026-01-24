package com.sujal.skyblock.items.api;

import com.sujal.skyblock.core.api.ItemType;
import com.sujal.skyblock.core.api.Rarity;
import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.util.SBItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.Map;

public abstract class SBItem {

    public abstract String getId(); // Internal ID e.g. "HYPERION"
    public abstract String getDisplayName();
    public abstract Item getMaterial(); // Vanilla item e.g. IRON_SWORD
    public abstract Rarity getRarity();
    public abstract ItemType getType();
    public abstract Map<StatType, Double> getStats();
    
    // Optional Ability (Return null if none)
    public abstract Ability getAbility();

    // Creates the actual ItemStack
    public ItemStack createItemStack() {
        ItemStack stack = new ItemStack(getMaterial());
        
        // Visuals
        stack.setCustomName(Text.literal(getDisplayName()).formatted(getRarity().getColor()));
        
        // Core Data
        SBItemUtils.setString(stack, "SBID", getId());
        SBItemUtils.setRarity(stack, getRarity());
        SBItemUtils.setType(stack, getType());
        
        // Stats
        Map<StatType, Double> stats = getStats();
        if (stats != null) {
            stats.forEach((stat, val) -> SBItemUtils.setStat(stack, stat, val));
        }
        
        // Ability Data
        Ability ability = getAbility();
        if (ability != null) {
            SBItemUtils.setAbility(stack, ability.getName(), ability.getDescription());
        }

        return stack;
    }
}
