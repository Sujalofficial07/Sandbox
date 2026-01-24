package com.sujal.skyblock.items;

import com.sujal.skyblock.items.menu.SkyblockMenuHandler;
import com.sujal.skyblock.items.registry.SkyblockRegistry;
import net.fabricmc.api.ModInitializer;

public class SkyblockItemsMod implements ModInitializer {
    @Override
    public void onInitialize() {
        SkyblockRegistry.registerAll(); // Load Items & Groups
        SkyblockMenuHandler.register(); // Load Command
    }
}
