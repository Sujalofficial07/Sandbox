package com.sujal.skyblock.core.api;

public enum ItemType {
    SWORD("SWORD"),
    BOW("BOW"),
    HELMET("HELMET"),
    CHESTPLATE("CHESTPLATE"),
    LEGGINGS("LEGGINGS"),
    BOOTS("BOOTS"),
    ACCESSORY("ACCESSORY"),
    PICKAXE("PICKAXE"),
    AXE("AXE"),
    SHOVEL("SHOVEL"),
    HOE("HOE"),
    MATERIAL("MATERIAL"),
    ITEM("ITEM");

    private final String name;

    ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
