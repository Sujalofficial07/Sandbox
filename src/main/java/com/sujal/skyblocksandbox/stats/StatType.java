package com.sujal.skyblocksandbox.stats;

public enum StatType {
    HEALTH("Health", "❤", 100.0),
    DEFENSE("Defense", "❈", 0.0),
    MANA("Intelligence", "✎", 100.0),
    STRENGTH("Strength", "❁", 0.0),
    CRIT_CHANCE("Crit Chance", "☣", 30.0),
    CRIT_DAMAGE("Crit Damage", "☠", 50.0), // New Stat
    SPEED("Speed", "✦", 100.0);

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
