package com.sujal.skyblocksandbox.item;

import com.sujal.skyblocksandbox.stats.StatType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SBItemFactory {

    /**
     * Core Factory Method
     */
    public static ItemStack create(String skyblockId, Item material, String name, Rarity rarity, Map<StatType, Double> stats) {
        ItemStack stack = new ItemStack(material);

        // 1. Display Name
        stack.setCustomName(Text.literal(name).formatted(rarity.getColor()));

        // 2. Stats & Flags
        NbtCompound root = stack.getOrCreateNbt();
        NbtCompound statsTag = new NbtCompound();
        if (stats != null) {
            stats.forEach((stat, value) -> statsTag.putDouble(stat.name(), value));
        }
        root.put("skyblock:stats", statsTag);

        root.putInt("HideFlags", 255); // Hide everything vanilla
        root.putBoolean("Unbreakable", true);

        // 3. Hypixel ExtraAttributes (Crucial for API/Mods)
        NbtCompound extra = new NbtCompound();
        extra.putString("id", skyblockId);
        extra.putString("uuid", UUID.randomUUID().toString()); // Unique per item instance
        root.put("ExtraAttributes", extra);

        // 4. Initial Lore
        buildLore(stack, rarity, stats);

        return stack;
    }

    /**
     * For Skull Items (Talismans, Dragon Heads, etc.)
     */
    public static ItemStack createSkull(String skyblockId, String name, Rarity rarity, Map<StatType, Double> stats, String textureUrl) {
        ItemStack stack = create(skyblockId, Items.PLAYER_HEAD, name, rarity, stats);
        
        NbtCompound root = stack.getOrCreateNbt();
        NbtCompound skullOwner = new NbtCompound();
        skullOwner.putString("Id", UUID.randomUUID().toString()); // Random UUID for texture
        skullOwner.putString("Name", "SkyblockItem");
        
        NbtCompound properties = new NbtCompound();
        NbtList textures = new NbtList();
        NbtCompound textureNode = new NbtCompound();
        textureNode.putString("Value", textureUrl); // Base64 Texture
        textures.add(textureNode);
        properties.put("textures", textures);
        skullOwner.put("Properties", properties);
        
        root.put("SkullOwner", skullOwner);
        return stack;
    }

    private static void buildLore(ItemStack stack, Rarity rarity, Map<StatType, Double> stats) {
        List<Text> lore = new ArrayList<>();
        
        if (stats != null) {
            stats.forEach((stat, value) -> {
                if (value != 0) {
                    lore.add(Text.literal(String.format("§7%s: %s%.0f%s", stat.getName(), stat.getColor(), value, stat.getIcon())));
                }
            });
        }
        
        lore.add(Text.empty());
        lore.add(Text.literal(rarity.getColor() + "§l" + rarity.name()));

        NbtCompound display = stack.getOrCreateSubNbt("display");
        NbtList loreList = new NbtList();
        lore.forEach(t -> loreList.add(NbtString.of(Text.Serializer.toJson(t))));
        display.put("Lore", loreList);
    }
    
    public static void addLore(ItemStack stack, String... lines) {
        NbtCompound display = stack.getOrCreateSubNbt("display");
        NbtList loreList = display.getList("Lore", 8);
        int index = Math.max(0, loreList.size() - 1); // Insert before rarity
        
        for (String line : lines) {
            loreList.add(index++, NbtString.of(Text.Serializer.toJson(Text.literal(line))));
        }
        display.put("Lore", loreList);
    }
}
