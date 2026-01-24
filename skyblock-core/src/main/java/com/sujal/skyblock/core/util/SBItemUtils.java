package com.sujal.skyblock.core.util;

import com.sujal.skyblock.core.api.ItemType;
import com.sujal.skyblock.core.api.Rarity;
import com.sujal.skyblock.core.api.StatType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class SBItemUtils {
    private static final String SB_TAG = "SkyBlockData";

    public static NbtCompound getSBData(ItemStack stack) {
        return stack.getOrCreateSubNbt(SB_TAG);
    }

    public static boolean isSkyblockItem(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().contains(SB_TAG);
    }

    // --- MISSING METHOD RESTORED ---
    public static void setString(ItemStack stack, String key, String value) {
        getSBData(stack).putString(key, value);
    }

    public static String getString(ItemStack stack, String key) {
        if (!isSkyblockItem(stack)) return "";
        return getSBData(stack).getString(key);
    }
    // --------------------------------

    public static void setStat(ItemStack stack, StatType stat, double value) {
        getSBData(stack).putDouble(stat.name(), value);
    }

    public static double getStat(ItemStack stack, StatType stat) {
        NbtCompound tag = getSBData(stack);
        return tag.contains(stat.name()) ? tag.getDouble(stat.name()) : 0.0;
    }

    public static void setRarity(ItemStack stack, Rarity rarity) {
        getSBData(stack).putString("Rarity", rarity.name());
    }

    public static Rarity getRarity(ItemStack stack) {
        if (!isSkyblockItem(stack)) return Rarity.COMMON;
        String r = getSBData(stack).getString("Rarity");
        try { return Rarity.valueOf(r); } catch (Exception e) { return Rarity.COMMON; }
    }

    public static void setType(ItemStack stack, ItemType type) {
        getSBData(stack).putString("Type", type.name());
    }

    public static ItemType getType(ItemStack stack) {
        if (!isSkyblockItem(stack)) return ItemType.ITEM;
        String t = getSBData(stack).getString("Type");
        try { return ItemType.valueOf(t); } catch (Exception e) { return ItemType.ITEM; }
    }

    public static void setAbility(ItemStack stack, String name, String desc) {
        NbtCompound tag = getSBData(stack);
        tag.putString("AbilityName", name);
        tag.putString("AbilityDesc", desc);
    }

    public static String getAbilityName(ItemStack stack) {
        return getSBData(stack).getString("AbilityName");
    }

    public static String getAbilityDesc(ItemStack stack) {
        return getSBData(stack).getString("AbilityDesc");
    }
}
