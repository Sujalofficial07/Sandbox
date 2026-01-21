package com.sujal.skyblock.core.util;

import com.sujal.skyblock.core.api.Rarity;
import com.sujal.skyblock.core.api.StatType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class SBItemUtils {
    private static final String SB_TAG = "SkyBlockData";

    public static NbtCompound getSBData(ItemStack stack) {
        return stack.getOrCreateSubNbt(SB_TAG);
    }

    public static void setString(ItemStack stack, String key, String value) {
        getSBData(stack).putString(key, value);
    }

    public static String getString(ItemStack stack, String key) {
        if (!stack.hasNbt() || !stack.getNbt().contains(SB_TAG)) return "";
        return getSBData(stack).getString(key);
    }

    public static void setStat(ItemStack stack, StatType stat, double value) {
        getSBData(stack).putDouble(stat.name(), value);
    }

    public static double getStat(ItemStack stack, StatType stat) {
        NbtCompound tag = getSBData(stack);
        return tag.contains(stat.name()) ? tag.getDouble(stat.name()) : 0.0;
    }

    public static void setRarity(ItemStack stack, Rarity rarity) {
        setString(stack, "Rarity", rarity.name());
    }

    public static Rarity getRarity(ItemStack stack) {
        String r = getString(stack, "Rarity");
        try {
            return Rarity.valueOf(r);
        } catch (Exception e) {
            return Rarity.COMMON; // Default
        }
    }
    }
