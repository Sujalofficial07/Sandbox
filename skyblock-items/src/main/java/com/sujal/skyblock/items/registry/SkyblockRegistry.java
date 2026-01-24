package com.sujal.skyblock.items.registry;

import com.sujal.skyblock.core.api.ItemType;
import com.sujal.skyblock.core.api.Rarity;
import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.items.factory.ItemGenerator;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SkyblockRegistry {

    // Define the Creative Tab Key
    public static final RegistryKey<ItemGroup> SKYBLOCK_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("skyblock-items", "general"));

    public static void register() {
        // Register the Group
        Registry.register(Registries.ITEM_GROUP, SKYBLOCK_GROUP_KEY, FabricItemGroup.builder()
                .icon(() -> new ItemStack(Items.DIAMOND_SWORD))
                .displayName(Text.literal("SkyBlock Items"))
                .entries((context, entries) -> {
                    
                    // --- ADD ALL ITEMS HERE ---
                    
                    // 1. Hyperion
                    entries.add(createHyperion());
                    
                    // 2. Terminator (Example Bow)
                    entries.add(createTerminator());
                    
                    // 3. Shadow Assassin Chestplate (Example Armor)
                    entries.add(createSAArmor());

                    // Add more...
                })
                .build());
    }

    // --- ITEM DEFINITIONS ---

    private static ItemStack createHyperion() {
        Map<StatType, Double> stats = new HashMap<>();
        stats.put(StatType.STRENGTH, 150.0);
        stats.put(StatType.INTELLIGENCE, 400.0);
        stats.put(StatType.CRIT_DAMAGE, 50.0);
        return ItemGenerator.createItem(Items.IRON_SWORD, "Hyperion", Rarity.LEGENDARY, ItemType.SWORD, stats, "Wither Impact", "Teleports you 10 blocks.");
    }

    private static ItemStack createTerminator() {
        Map<StatType, Double> stats = new HashMap<>();
        stats.put(StatType.CRIT_CHANCE, 100.0);
        stats.put(StatType.CRIT_DAMAGE, 250.0);
        return ItemGenerator.createItem(Items.BOW, "Terminator", Rarity.MYTHIC, ItemType.BOW, stats, "Salvation", "Shoots 3 arrows at once.");
    }
    
    private static ItemStack createSAArmor() {
        Map<StatType, Double> stats = new HashMap<>();
        stats.put(StatType.DEFENSE, 150.0);
        stats.put(StatType.STRENGTH, 25.0);
        stats.put(StatType.SPEED, 10.0);
        return ItemGenerator.createItem(Items.LEATHER_CHESTPLATE, "Shadow Assassin Chestplate", Rarity.EPIC, ItemType.CHESTPLATE, stats, "", "");
    }
}
