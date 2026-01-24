package com.sujal.skyblock.items;

import com.sujal.skyblock.items.api.SBItem;
import com.sujal.skyblock.items.weapons.Hyperion;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Items {
    private static final Map<String, SBItem> REGISTRY = new HashMap<>();

    public static void register() {
        registerItem(new Hyperion());
        // Add other items here:
        // registerItem(new Terminator());
    }

    private static void registerItem(SBItem item) {
        REGISTRY.put(item.getId(), item);
    }

    public static SBItem get(String id) {
        return REGISTRY.get(id);
    }
    
    public static ItemStack getStack(String id) {
        SBItem item = get(id);
        if (item != null) return item.createItemStack();
        return null;
    }

    public static Collection<SBItem> getAll() {
        return REGISTRY.values();
    }
}
