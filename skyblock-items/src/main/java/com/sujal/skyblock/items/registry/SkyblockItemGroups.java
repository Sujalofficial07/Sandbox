package com.sujal.skyblock.items.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SkyblockItemGroups {
    public static final RegistryKey<ItemGroup> SKYBLOCK_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("skyblock-items", "general"));

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, SKYBLOCK_GROUP_KEY, FabricItemGroup.builder()
                .icon(() -> new ItemStack(Items.DIAMOND_SWORD))
                .displayName(Text.literal("SkyBlock Items"))
                .entries((context, entries) -> {
                    // Automatically add all registered SBItems to the creative menu
                    SkyblockItems.getAllItems().forEach(sbItem -> {
                        entries.add(sbItem.createItemStack());
                    });
                })
                .build());
    }
}
