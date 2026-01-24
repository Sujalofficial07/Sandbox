package com.sujal.skyblock.items.registry;

import com.sujal.skyblock.items.api.SBItem;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SkyblockRegistry {
    private static final Map<String, SBItem> ID_MAP = new HashMap<>();

    public static void registerAll() {
        // Register Items internally
        for (SBItem item : SkyblockItems.getAllItems()) {
            register(item);
        }
        
        // Register Creative Tab
        SkyblockItemGroups.register();
    }

    private static void register(SBItem item) {
        ID_MAP.put(item.getId(), item);
    }

    public static SBItem getById(String id) {
        return ID_MAP.get(id);
    }
}
