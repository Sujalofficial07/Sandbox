package com.sujal.skyblock.core.api.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import java.util.ArrayList;
import java.util.List;

public class NBTUtils {
    public static final String SKYBLOCK_TAG = "SkyBlock";
    public static final String SKYBLOCK_ID = "id";
    public static final String SKYBLOCK_UUID = "uuid";
    public static final String SKYBLOCK_RARITY = "rarity";
    public static final String SKYBLOCK_STATS = "stats";
    public static final String SKYBLOCK_LORE = "lore";
    public static final String SKYBLOCK_ABILITIES = "abilities";
    public static final String SKYBLOCK_REFORGE = "reforge";
    
    public static NbtCompound getOrCreateSkyBlockTag(NbtCompound nbt) {
        if (!nbt.contains(SKYBLOCK_TAG, NbtElement.COMPOUND_TYPE)) {
            nbt.put(SKYBLOCK_TAG, new NbtCompound());
        }
        return nbt.getCompound(SKYBLOCK_TAG);
    }
    
    public static boolean hasSkyBlockTag(NbtCompound nbt) {
        return nbt.contains(SKYBLOCK_TAG, NbtElement.COMPOUND_TYPE);
    }
    
    public static String getSkyBlockId(NbtCompound nbt) {
        NbtCompound skyblock = getOrCreateSkyBlockTag(nbt);
        return skyblock.getString(SKYBLOCK_ID);
    }
    
    public static void setSkyBlockId(NbtCompound nbt, String id) {
        NbtCompound skyblock = getOrCreateSkyBlockTag(nbt);
        skyblock.putString(SKYBLOCK_ID, id);
    }
    
    public static String getSkyBlockUUID(NbtCompound nbt) {
        NbtCompound skyblock = getOrCreateSkyBlockTag(nbt);
        return skyblock.getString(SKYBLOCK_UUID);
    }
    
    public static void setSkyBlockUUID(NbtCompound nbt, String uuid) {
        NbtCompound skyblock = getOrCreateSkyBlockTag(nbt);
        skyblock.putString(SKYBLOCK_UUID, uuid);
    }
    
    public static String getRarity(NbtCompound nbt) {
        NbtCompound skyblock = getOrCreateSkyBlockTag(nbt);
        return skyblock.contains(SKYBLOCK_RARITY) ? skyblock.getString(SKYBLOCK_RARITY) : "COMMON";
    }
    
    public static void setRarity(NbtCompound nbt, String rarity) {
        NbtCompound skyblock = getOrCreateSkyBlockTag(nbt);
        skyblock.putString(SKYBLOCK_RARITY, rarity);
    }
    
    public static List<String> getLore(NbtCompound nbt) {
        NbtCompound skyblock = getOrCreateSkyBlockTag(nbt);
        List<String> lore = new ArrayList<>();
        
        if (skyblock.contains(SKYBLOCK_LORE, NbtElement.LIST_TYPE)) {
            NbtList loreList = skyblock.getList(SKYBLOCK_LORE, NbtElement.STRING_TYPE);
            for (int i = 0; i < loreList.size(); i++) {
                lore.add(loreList.getString(i));
            }
        }
        
        return lore;
    }
    
    public static void setLore(NbtCompound nbt, List<String> lore) {
        NbtCompound skyblock = getOrCreateSkyBlockTag(nbt);
        NbtList loreList = new NbtList();
        
        for (String line : lore) {
            loreList.add(NbtString.of(line));
        }
        
        skyblock.put(SKYBLOCK_LORE, loreList);
    }
    
    public static void addLoreLine(NbtCompound nbt, String line) {
        List<String> lore = getLore(nbt);
        lore.add(line);
        setLore(nbt, lore);
    }
    
    public static NbtCompound getStats(NbtCompound nbt) {
        NbtCompound skyblock = getOrCreateSkyBlockTag(nbt);
        if (!skyblock.contains(SKYBLOCK_STATS, NbtElement.COMPOUND_TYPE)) {
            skyblock.put(SKYBLOCK_STATS, new NbtCompound());
        }
        return skyblock.getCompound(SKYBLOCK_STATS);
    }
    
    public static void setStats(NbtCompound nbt, NbtCompound stats) {
        NbtCompound skyblock = getOrCreateSkyBlockTag(nbt);
        skyblock.put(SKYBLOCK_STATS, stats);
    }
    
    public static double getStatValue(NbtCompound nbt, String statName) {
        NbtCompound stats = getStats(nbt);
        return stats.contains(statName) ? stats.getDouble(statName) : 0.0;
    }
    
    public static void setStatValue(NbtCompound nbt, String statName, double value) {
        NbtCompound stats = getStats(nbt);
        stats.putDouble(statName, value);
    }
}
