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

    public static ItemStack create(Item material, String name, Rarity rarity, Map<StatType, Double> stats) {
        ItemStack stack = new ItemStack(material);

        // 1. Name: Use properly formatted text component
        stack.setCustomName(Text.literal(name).formatted(rarity.getColor()));

        // 2. Stats NBT
        NbtCompound root = stack.getOrCreateNbt();
        NbtCompound statsTag = new NbtCompound();
        stats.forEach((stat, value) -> statsTag.putDouble(stat.name(), value));
        root.put("skyblock:stats", statsTag);

        // 3. Hide Flags (Attributes, Unbreakable, etc.)
        root.putInt("HideFlags", 255); // Hide ALL flags (Enchants, Attributes, Unbreakable)
        root.putBoolean("Unbreakable", true);

        // 4. Build Initial Lore
        buildLore(stack, rarity, stats);

        return stack;
    }

    private static void buildLore(ItemStack stack, Rarity rarity, Map<StatType, Double> stats) {
        List<Text> lore = new ArrayList<>();

        // Add Stats
        stats.forEach((stat, value) -> {
            if (value != 0) {
                // Gray Name: +Value Symbol
                String line = String.format("§7%s: §a+%.0f%s", stat.getName(), value, stat.getIcon());
                if (stat == StatType.CRIT_CHANCE || stat == StatType.CRIT_DAMAGE) {
                     line = String.format("§7%s: §9+%.0f%%%s", stat.getName(), value, stat.getIcon()); // Blue for Crit
                }
                lore.add(Text.literal(line));
            }
        });

        lore.add(Text.empty());

        // Rarity Line (Bold and Colored)
        // e.g., "LEGENDARY SWORD"
        String type = "ITEM";
        if (stack.getItem().toString().contains("sword")) type = "SWORD";
        if (stack.getItem().toString().contains("bow")) type = "BOW";
        if (stack.getItem().toString().contains("chestplate")) type = "CHESTPLATE";
        // ... (Add more logic for types if needed)

        String rarityLine = rarity.getColor() + "§l" + rarity.name() + " " + type; 
        lore.add(Text.literal(rarityLine));

        writeLoreToNbt(stack, lore);
    }

    public static void addLore(ItemStack stack, String... lines) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound display = nbt.getCompound("display");
        NbtList existingLore = display.getList("Lore", 8);

        // We need to convert existing strings back to a list to insert, or just create new list
        // Easier strategy: Rebuild lore. But for adding abilities:
        
        // 1. Parse existing
        List<String> rawJsonList = new ArrayList<>();
        for(int i=0; i<existingLore.size(); i++) rawJsonList.add(existingLore.getString(i));

        // 2. Find insert position (Before the last line which is Rarity)
        int insertPos = Math.max(0, rawJsonList.size() - 1);

        // 3. Insert new lines
        for (String line : lines) {
            rawJsonList.add(insertPos, Text.Serializer.toJson(Text.literal(line)));
            insertPos++;
        }

        // 4. Write back
        NbtList newLoreList = new NbtList();
        for (String json : rawJsonList) newLoreList.add(NbtString.of(json));
        
        display.put("Lore", newLoreList);
        nbt.put("display", display);
    }

    // Helper to standardize writing Lore
    private static void writeLoreToNbt(ItemStack stack, List<Text> textLines) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound display = nbt.getCompound("display");
        NbtList loreList = new NbtList();

        for (Text t : textLines) {
            loreList.add(NbtString.of(Text.Serializer.toJson(t)));
        }

        display.put("Lore", loreList);
        nbt.put("display", display);
    }

    public static void setCustomModelData(ItemStack stack, String skyblockId, int modelData) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound extra = nbt.getCompound("ExtraAttributes"); // Hypixel Standard
        extra.putString("id", skyblockId);
        nbt.put("ExtraAttributes", extra);
        nbt.putInt("CustomModelData", modelData);
    }
}
