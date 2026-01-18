package com.sujal.skyblockstats.stats;

public enum SkyBlockStatType {
    HEALTH(100.0),
    DEFENSE(0.0),
    STRENGTH(0.0),
    CRIT_CHANCE(30.0),
    CRIT_DAMAGE(50.0),
    FEROCITY(0.0),
    INTELLIGENCE(0.0),
    MANA(100.0),
    ABILITY_DAMAGE(0.0),
    SPEED(100.0),
    MAGIC_FIND(0.0),
    PET_LUCK(0.0),
    MINING_SPEED(0.0),
    MINING_FORTUNE(0.0),
    FARMING_FORTUNE(0.0),
    FISHING_FORTUNE(0.0),
    TRUE_DEFENSE(0.0),
    TRUE_DAMAGE(0.0),
    SEA_CREATURE_CHANCE(0.0);

    private final double baseDefault;

    SkyBlockStatType(double baseDefault) {
        this.baseDefault = baseDefault;
    }

    public double getBaseDefault() {
        return baseDefault;
    }
}
