package com.sujal.skyblock.core.api;

import net.minecraft.util.Formatting;

public enum StatType {
    // Primary Combat Stats
    DAMAGE("Damage", "❁", 0.0, Formatting.RED),
    STRENGTH("Strength", "❁", 0.0, Formatting.RED),
    CRIT_CHANCE("Crit Chance", "☣", 30.0, Formatting.BLUE),
    CRIT_DAMAGE("Crit Damage", "☠", 50.0, Formatting.BLUE),
    ATTACK_SPEED("Bonus Attack Speed", "⚔", 0.0, Formatting.YELLOW),

    // Defensive/Misc Stats
    HEALTH("Health", "❤", 100.0, Formatting.GREEN), // Hypixel often uses Green or Red for HP in tooltips
    DEFENSE("Defense", "❈", 0.0, Formatting.GREEN),
    INTELLIGENCE("Intelligence", "✎", 0.0, Formatting.GREEN),
    SPEED("Speed", "✦", 100.0, Formatting.WHITE),
    FEROCITY("Ferocity", "⫽", 0.0, Formatting.RED),
    
    // Skill Wisdom/Fortune
    MAGIC_FIND("Magic Find", "✯", 0.0, Formatting.AQUA),
    MINING_FORTUNE("Mining Fortune", "☘", 0.0, Formatting.GOLD),
    FARMING_FORTUNE("Farming Fortune", "☘", 0.0, Formatting.GOLD),
    FORAGING_FORTUNE("Foraging Fortune", "☘", 0.0, Formatting.GOLD);

    private final String name;
    private final String icon;
    private final double baseValue;
    private final Formatting color;

    StatType(String name, String icon, double baseValue, Formatting color) {
        this.name = name;
        this.icon = icon;
        this.baseValue = baseValue;
        this.color = color;
    }

    public String getName() { return name; }
    public String getIcon() { return icon; }
    public double getBaseValue() { return baseValue; }
    public Formatting getColor() { return color; }
}
