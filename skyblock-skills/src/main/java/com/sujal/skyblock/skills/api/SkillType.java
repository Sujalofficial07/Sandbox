package com.sujal.skyblock.skills.api;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;

public enum SkillType {
    FARMING("Farming", Items.GOLDEN_HOE, Formatting.GREEN, "Farmhand"),
    MINING("Mining", Items.DIAMOND_PICKAXE, Formatting.GRAY, "Spelunker"),
    COMBAT("Combat", Items.STONE_SWORD, Formatting.RED, "Warrior"),
    FORAGING("Foraging", Items.JUNGLE_SAPLING, Formatting.DARK_GREEN, "Logger"),
    FISHING("Fishing", Items.FISHING_ROD, Formatting.AQUA, "Treasure Hunter"),
    ENCHANTING("Enchanting", Items.ENCHANTING_TABLE, Formatting.DARK_PURPLE, "Conjurer"),
    ALCHEMY("Alchemy", Items.BREWING_STAND, Formatting.DARK_PURPLE, "Brewer"),
    TAMING("Taming", Items.SPAWN_EGG, Formatting.DARK_GREEN, "Zoologist"), // Using generic egg as placeholder
    DUNGEONEERING("Dungeoneering", Items.WITHER_SKELETON_SKULL, Formatting.DARK_RED, "Catacombs"),
    CARPENTRY("Carpentry", Items.CRAFTING_TABLE, Formatting.GOLD, "Carpenter"),
    RUNECRAFTING("Runecrafting", Items.MAGMA_CREAM, Formatting.LIGHT_PURPLE, "Runecrafter"),
    SOCIAL("Social", Items.EMERALD, Formatting.GREEN, "Social");

    private final String name;
    private final Item icon;
    private final Formatting color;
    private final String perkName;

    SkillType(String name, Item icon, Formatting color, String perkName) {
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.perkName = perkName;
    }

    public String getName() { return name; }
    public Item getIcon() { return icon; }
    public Formatting getColor() { return color; }
    public String getPerkName() { return perkName; }
}
