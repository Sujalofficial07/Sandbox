package com.sujal.skyblock.items.factory;

import com.sujal.skyblock.core.api.ItemType;
import com.sujal.skyblock.core.api.Rarity;
import com.sujal.skyblock.core.api.StatType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;

public class ItemDefinitions {
    public static ItemStack getHyperion() {
        Map<StatType, Double> stats = new HashMap<>();
        stats.put(StatType.STRENGTH, 150.0); // Base Damage in HB formula often derives from Str
        stats.put(StatType.CRIT_DAMAGE, 300.0);
        stats.put(StatType.INTELLIGENCE, 400.0);
        stats.put(StatType.MINING_FORTUNE, 0.0); 

        return ItemGenerator.createItem(
            Items.IRON_SWORD, 
            "Hyperion", 
            Rarity.LEGENDARY, 
            ItemType.SWORD,
            stats, 
            "Wither Impact", 
            "Teleport 10 blocks ahead and deal massive damage.\nAlso applies Absorption shielding."
        );
    }
}
