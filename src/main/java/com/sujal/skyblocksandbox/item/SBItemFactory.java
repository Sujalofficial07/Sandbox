package com.sujal.skyblocksandbox.item;

import com.sujal.skyblocksandbox.stats.StatType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SBItemFactory {

    /**
     * Creates a custom Skyblock Item with Stats and Lore.
     * @param material Vanilla Item (e.g., Items.DIAMOND_SWORD)
     * @param name Display Name
     * @param rarity Item Rarity
     * @param stats Map of Stats (Damage, Strength, etc.)
     * @return Completed ItemStack
     */
    public static ItemStack create(Item material, String name, Rarity rarity, Map<StatType, Double> stats) {
        ItemStack stack = new ItemStack(material);

        // 1. Set Name
        stack.setCustomName(Text.literal(name).formatted(rarity.getColor()));

        // 2. Setup NBT Data for StatsHandler
        NbtCompound root = stack.getOrCreateNbt();
        NbtCompound statsTag = new NbtCompound();
        
        // Write stats to NBT
        stats.forEach((stat, value) -> {
            statsTag.putDouble(stat.name(), value);
        });
        
        root.put("skyblock:stats", statsTag);
        
        // 3. Hide Vanilla Attributes (Attack Damage, etc.) & Unbreakable
        // Flag 2 = Hide Attributes, Flag 4 = Hide Unbreakable
        root.putInt("HideFlags", 6); 
        root.putBoolean("Unbreakable", true);

        // 4. Generate Lore
        buildLore(stack, rarity, stats);

        return stack;
    }

    private static void buildLore(ItemStack stack, Rarity rarity, Map<StatType, Double> stats) {
        List<Text> lore = new ArrayList<>();
        
        // Add Stats to Lore
        stats.forEach((stat, value) -> {
            String line = String.format("§7%s: §a+%.0f%s", stat.getName(), value, stat.getIcon());
            lore.add(Text.literal(line));
        });

        lore.add(Text.empty()); // Spacer

        // Rarity Line at Bottom
        String rarityLine = String.format("%s§l%s", rarity.getColor(), rarity.name());
        lore.add(Text.literal(rarityLine));

        // Apply Lore
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound display = nbt.getCompound("display");
        NbtList loreList = new NbtList();
        
        for (Text t : lore) {
            loreList.add(NbtString.of(Text.Serializer.toJson(t)));
        }
        
        display.put("Lore", loreList);
        nbt.put("display", display);
    }
}
