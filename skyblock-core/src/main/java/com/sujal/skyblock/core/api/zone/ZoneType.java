package com.sujal.skyblock.core.api.zone;

public enum ZoneType {
    HUB("Hub"),
    PRIVATE_ISLAND("Private Island"),
    GOLD_MINE("Gold Mine"),
    DEEP_CAVERNS("Deep Caverns"),
    SPIDERS_DEN("Spider's Den"),
    THE_END("The End"),
    CRIMSON_ISLE("Crimson Isle"),
    THE_PARK("The Park"),
    MUSHROOM_DESERT("Mushroom Desert"),
    BARN("Barn"),
    GARDEN("Garden"),
    DUNGEON("Dungeon"),
    CUSTOM("Custom");
    
    private final String displayName;
    
    ZoneType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
