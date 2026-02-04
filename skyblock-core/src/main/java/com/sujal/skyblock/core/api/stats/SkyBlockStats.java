package com.sujal.skyblock.core.api.stats;

import java.util.Map;

public interface SkyBlockStats {
    double getStat(StatType type);
    
    void setStat(StatType type, double value);
    
    void addStat(StatType type, double value);
    
    void removeStat(StatType type, double value);
    
    double getBaseStat(StatType type);
    
    void setBaseStat(StatType type, double value);
    
    void addModifier(String id, StatModifier modifier);
    
    void removeModifier(String id);
    
    boolean hasModifier(String id);
    
    StatModifier getModifier(String id);
    
    Map<String, StatModifier> getAllModifiers();
    
    void clearModifiers();
    
    void recalculate();
    
    double getHealth();
    
    double getMaxHealth();
    
    void setHealth(double health);
    
    void setMaxHealth(double maxHealth);
    
    double getMana();
    
    double getMaxMana();
    
    void setMana(double mana);
    
    void setMaxMana(double maxMana);
    
    void regenerateMana(double amount);
    
    boolean consumeMana(double amount);
    
    double getDefense();
    
    double getStrength();
    
    double getCritChance();
    
    double getCritDamage();
    
    double getAttackSpeed();
    
    double getSpeed();
    
    double getMagicFind();
    
    double getPetLuck();
    
    double getSeaCreatureChance();
    
    double getFerocity();
    
    double getAbilityDamage();
    
    double getMiningSpeed();
    
    double getMiningFortune();
    
    double getFarmingFortune();
    
    double getForagingFortune();
    
    double getTrueDef();
}
