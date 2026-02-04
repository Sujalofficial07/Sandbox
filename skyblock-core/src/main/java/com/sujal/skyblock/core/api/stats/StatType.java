package com.sujal.skyblock.core.api.stats;

public enum StatType {
    HEALTH("Health", "❤", true),
    DEFENSE("Defense", "❈", true),
    STRENGTH("Strength", "❁", true),
    CRIT_CHANCE("Crit Chance", "☣", false),
    CRIT_DAMAGE("Crit Damage", "☠", false),
    ATTACK_SPEED("Bonus Attack Speed", "⚔", false),
    SPEED("Speed", "✦", false),
    MAGIC_FIND("Magic Find", "✯", false),
    PET_LUCK("Pet Luck", "♣", false),
    SEA_CREATURE_CHANCE("Sea Creature Chance", "α", false),
    FEROCITY("Ferocity", "⫽", true),
    ABILITY_DAMAGE("Ability Damage", "✦", false),
    MANA("Mana", "✎", true),
    INTELLIGENCE("Intelligence", "✎", true),
    TRUE_DEFENSE("True Defense", "❂", true),
    MINING_SPEED("Mining Speed", "⸕", true),
    MINING_FORTUNE("Mining Fortune", "☘", true),
    FARMING_FORTUNE("Farming Fortune", "☘", true),
    FORAGING_FORTUNE("Foraging Fortune", "☘", true),
    FISHING_SPEED("Fishing Speed", "☂", true),
    TROPHY_FISH_CHANCE("Trophy Fish Chance", "⚡", false);
    
    private final String displayName;
    private final String symbol;
    private final boolean showWhole;
    
    StatType(String displayName, String symbol, boolean showWhole) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.showWhole = showWhole;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public boolean isShowWhole() {
        return showWhole;
    }
    
    public String format(double value) {
        if (showWhole) {
            return symbol + " " + (int) value + " " + displayName;
        } else {
            return symbol + " " + String.format("%.1f", value) + "% " + displayName;
        }
    }
}
