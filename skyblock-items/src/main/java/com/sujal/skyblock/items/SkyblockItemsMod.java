package com.sujal.skyblock.items;

import com.sujal.skyblock.items.menu.MenuHandler;
import net.fabricmc.api.ModInitializer;

public class SkyblockItemsMod implements ModInitializer {
    @Override
    public void onInitialize() {
        Items.register(); // Load all items like Hyperion
        MenuHandler.register(); // Enable /sbitems command
    }
}
