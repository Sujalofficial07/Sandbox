package com.sujal.skyblock.items.registry;

import com.sujal.skyblock.items.api.SBItem;
import com.sujal.skyblock.items.weapons.Hyperion;

import java.util.ArrayList;
import java.util.List;

public class SkyblockItems {
    
    // Define Items Here
    public static final SBItem HYPERION = new Hyperion();
    
    // Add more items here...
    // public static final SBItem TERMINATOR = new Terminator();

    // Helper to get all items for the menu
    public static List<SBItem> getAllItems() {
        List<SBItem> items = new ArrayList<>();
        items.add(HYPERION);
        // items.add(TERMINATOR);
        return items;
    }
}
