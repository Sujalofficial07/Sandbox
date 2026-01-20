package com.sujal.skyblock.core.api;

public enum StatType {
    HEALTH("Health", "❤", 100.0),
    DEFENSE("Defense", "❈", 0.0),
    STRENGTH("Strength", "❁", 0.0),
    INTELLIGENCE("Intelligence", "✎", 0.0),
    CRIT_CHANCE("Crit Chance", "☣", 30.0),
    CRIT_DAMAGE("Crit Damage", "☠", 50.0),
    ATTACK_SPEED("Bonus Attack Speed", "⚔", 0.0),
    SPEED("Speed", "✦", 100.0),
    MAGIC_FIND("Magic Find", "✯", 0.0),
    MINING_FORTUNE("Mining Fortune", "☘", 0.0),
    FARMING_FORTUNE("Farming Fortune", "☘", 0.0),
    FORAGING_FORTUNE("Foraging Fortune", "☘", 0.0);

    private final String name;
    private final String icon;
    private final double baseValue;

    StatType(String name, String icon, double baseValue) {
        this.name = name;
        this.icon = icon;
        this.baseValue = baseValue;
    }

    public String getName() { return name; }
    public String getIcon() { return icon; }
    public double getBaseValue() { return baseValue; }
}
