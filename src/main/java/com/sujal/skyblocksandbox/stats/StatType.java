package com.sujal.skyblocksandbox.stats;

import net.minecraft.util.Formatting;

public enum StatType {
    // --- CORE STATS ---
    HEALTH("Health", "❤", 100.0, Formatting.RED),
    DEFENSE("Defense", "❈", 0.0, Formatting.GREEN),
    MANA("Intelligence", "✎", 100.0, Formatting.AQUA),
    SPEED("Speed", "✦", 100.0, Formatting.WHITE),

    // --- OFFENSIVE STATS ---
    STRENGTH("Strength", "❁", 0.0, Formatting.RED),
    CRIT_CHANCE("Crit Chance", "☣", 30.0, Formatting.BLUE),
    CRIT_DAMAGE("Crit Damage", "☠", 50.0, Formatting.BLUE),
    ATTACK_SPEED("Bonus Attack Speed", "⚔", 0.0, Formatting.YELLOW),
    FEROCITY("Ferocity", "⫽", 0.0, Formatting.RED),
    ABILITY_DAMAGE("Ability Damage", "๑", 0.0, Formatting.RED),

    // --- DEFENSIVE / MISC ---
    TRUE_DEFENSE("True Defense", "❂", 0.0, Formatting.WHITE),
    MAGIC_FIND("Magic Find", "✯", 0.0, Formatting.AQUA),
    PET_LUCK("Pet Luck", "♣", 0.0, Formatting.LIGHT_PURPLE),

    // --- MINING / GATHERING ---
    MINING_SPEED("Mining Speed", "⸕", 0.0, Formatting.GOLD),
    MINING_FORTUNE("Mining Fortune", "☘", 0.0, Formatting.GOLD),
    FORAGING_FORTUNE("Foraging Fortune", "☘", 0.0, Formatting.DARK_GREEN),
    FARMING_FORTUNE("Farming Fortune", "☘", 0.0, Formatting.YELLOW);

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
