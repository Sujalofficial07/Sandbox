package com.sujal.skyblocksandbox.item;

import com.sujal.skyblocksandbox.stats.StatType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SBItemFactory {

    /**
     * Creates a custom Skyblock Item with Stats and Lore.
     * Arguments: Item Material, Display Name, Rarity, Stats Map
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

        // 3. Hide Vanilla Attributes & Unbreakable
        root.putInt("HideFlags", 6);
        root.putBoolean("Unbreakable", true);

        // 4. Generate Lore
        buildLore(stack, rarity, stats);

        return stack;
    }

    /**
     * Internal method to build initial lore.
     */
    private static void buildLore(ItemStack stack, Rarity rarity, Map<StatType, Double> stats) {
        List<Text> lore = new ArrayList<>();

        // Add Stats to Lore
        stats.forEach((stat, value) -> {
            // Only show non-zero stats or critical ones
            if (value != 0) {
                String line = String.format("§7%s: %s%.0f%s", stat.getName(), stat.getColor(), value, stat.getIcon());
                lore.add(Text.literal(line));
            }
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

    /**
     * Helper to append extra lore lines (Abilities).
     * Alias: addLore / appendLore (both included to prevent errors)
     */
    public static void addLore(ItemStack stack, String... lines) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound display = nbt.getCompound("display");
        NbtList loreList = display.getList("Lore", 8); // 8 = String Tag

        // Insert before Rarity (last line)
        int insertIndex = Math.max(0, loreList.size() - 1);

        for (String line : lines) {
            loreList.add(insertIndex, NbtString.of(Text.Serializer.toJson(Text.literal(line))));
            insertIndex++;
        }

        display.put("Lore", loreList);
        nbt.put("display", display);
    }
    
    // Alias method because some files call this
    public static void appendLore(ItemStack stack, String... lines) {
        addLore(stack, lines);
    }

    /**
     * Helper to set CustomModelData and ID.
     */
    public static void setCustomModelData(ItemStack stack, String skyblockId, int modelData) {
        NbtCompound nbt = stack.getOrCreateNbt();
        
        // Hypixel "ExtraAttributes" standard
        NbtCompound extra = nbt.getCompound("ExtraAttributes");
        extra.putString("id", skyblockId);
        nbt.put("ExtraAttributes", extra);

        // Texture Pack support
        nbt.putInt("CustomModelData", modelData);
    }
}
