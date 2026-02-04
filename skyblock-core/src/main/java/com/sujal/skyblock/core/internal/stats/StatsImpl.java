package com.sujal.skyblock.core.internal.stats;

import com.sujal.skyblock.core.api.stats.SkyBlockStats;
import com.sujal.skyblock.core.api.stats.StatModifier;
import com.sujal.skyblock.core.api.stats.StatType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StatsImpl implements SkyBlockStats {
    private final Map<StatType, Double> baseStats;
    private final Map<StatType, Double> calculatedStats;
    private final Map<String, StatModifier> modifiers;
    
    private double currentHealth;
    private double currentMana;
    
    public StatsImpl() {
        this.baseStats = new EnumMap<>(StatType.class);
        this.calculatedStats = new EnumMap<>(StatType.class);
        this.modifiers = new ConcurrentHashMap<>();
        
        initializeDefaults();
    }
    
    private void initializeDefaults() {
        setBaseStat(StatType.HEALTH, 100.0);
        setBaseStat(StatType.MANA, 100.0);
        setBaseStat(StatType.DEFENSE, 0.0);
        setBaseStat(StatType.STRENGTH, 0.0);
        setBaseStat(StatType.CRIT_CHANCE, 30.0);
        setBaseStat(StatType.CRIT_DAMAGE, 50.0);
        setBaseStat(StatType.ATTACK_SPEED, 0.0);
        setBaseStat(StatType.SPEED, 100.0);
        setBaseStat(StatType.INTELLIGENCE, 0.0);
        
        this.currentHealth = 100.0;
        this.currentMana = 100.0;
        
        recalculate();
    }
    
    @Override
    public double getStat(StatType type) {
        return calculatedStats.getOrDefault(type, 0.0);
    }
    
    @Override
    public void setStat(StatType type, double value) {
        calculatedStats.put(type, value);
    }
    
    @Override
    public void addStat(StatType type, double value) {
        calculatedStats.merge(type, value, Double::sum);
    }
    
    @Override
    public void removeStat(StatType type, double value) {
        calculatedStats.merge(type, -value, Double::sum);
    }
    
    @Override
    public double getBaseStat(StatType type) {
        return baseStats.getOrDefault(type, 0.0);
    }
    
    @Override
    public void setBaseStat(StatType type, double value) {
        baseStats.put(type, value);
        recalculate();
    }
    
    @Override
    public void addModifier(String id, StatModifier modifier) {
        modifiers.put(id, modifier);
        recalculate();
    }
    
    @Override
    public void removeModifier(String id) {
        modifiers.remove(id);
        recalculate();
    }
    
    @Override
    public boolean hasModifier(String id) {
        return modifiers.containsKey(id);
    }
    
    @Override
    public StatModifier getModifier(String id) {
        return modifiers.get(id);
    }
    
    @Override
    public Map<String, StatModifier> getAllModifiers() {
        return new HashMap<>(modifiers);
    }
    
    @Override
    public void clearModifiers() {
        modifiers.clear();
        recalculate();
    }
    
    @Override
    public void recalculate() {
        calculatedStats.clear();
        
        for (StatType type : StatType.values()) {
            double base = baseStats.getOrDefault(type, 0.0);
            double flatBonus = 0.0;
            double percentBonus = 0.0;
            
            List<StatModifier> sortedModifiers = new ArrayList<>(modifiers.values());
            sortedModifiers.sort(Comparator.comparingInt(StatModifier::getPriority).reversed());
            
            for (StatModifier modifier : sortedModifiers) {
                flatBonus += modifier.getFlatBonus(type);
                percentBonus += modifier.getPercentBonus(type);
            }
            
            double total = (base + flatBonus) * (1.0 + percentBonus / 100.0);
            calculatedStats.put(type, total);
        }
        
        double maxHealth = getMaxHealth();
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
        
        double maxMana = getMaxMana();
        if (currentMana > maxMana) {
            currentMana = maxMana;
        }
    }
    
    @Override
    public double getHealth() {
        return currentHealth;
    }
    
    @Override
    public double getMaxHealth() {
        return getStat(StatType.HEALTH);
    }
    
    @Override
    public void setHealth(double health) {
        this.currentHealth = Math.max(0, Math.min(health, getMaxHealth()));
    }
    
    @Override
    public void setMaxHealth(double maxHealth) {
        setBaseStat(StatType.HEALTH, maxHealth);
    }
    
    @Override
    public double getMana() {
        return currentMana;
    }
    
    @Override
    public double getMaxMana() {
        double base = getStat(StatType.MANA);
        double intelligence = getStat(StatType.INTELLIGENCE);
        return base + intelligence;
    }
    
    @Override
    public void setMana(double mana) {
        this.currentMana = Math.max(0, Math.min(mana, getMaxMana()));
    }
    
    @Override
    public void setMaxMana(double maxMana) {
        setBaseStat(StatType.MANA, maxMana);
    }
    
    @Override
    public void regenerateMana(double amount) {
        setMana(currentMana + amount);
    }
    
    @Override
    public boolean consumeMana(double amount) {
        if (currentMana >= amount) {
            currentMana -= amount;
            return true;
        }
        return false;
    }
    
    @Override
    public double getDefense() {
        return getStat(StatType.DEFENSE);
    }
    
    @Override
    public double getStrength() {
        return getStat(StatType.STRENGTH);
    }
    
    @Override
    public double getCritChance() {
        return getStat(StatType.CRIT_CHANCE);
    }
    
    @Override
    public double getCritDamage() {
        return getStat(StatType.CRIT_DAMAGE);
    }
    
    @Override
    public double getAttackSpeed() {
        return getStat(StatType.ATTACK_SPEED);
    }
    
    @Override
    public double getSpeed() {
        return getStat(StatType.SPEED);
    }
    
    @Override
    public double getMagicFind() {
        return getStat(StatType.MAGIC_FIND);
    }
    
    @Override
    public double getPetLuck() {
        return getStat(StatType.PET_LUCK);
    }
    
    @Override
    public double getSeaCreatureChance() {
        return getStat(StatType.SEA_CREATURE_CHANCE);
    }
    
    @Override
    public double getFerocity() {
        return getStat(StatType.FEROCITY);
    }
    
    @Override
    public double getAbilityDamage() {
        return getStat(StatType.ABILITY_DAMAGE);
    }
    
    @Override
    public double getMiningSpeed() {
        return getStat(StatType.MINING_SPEED);
    }
    
    @Override
    public double getMiningFortune() {
        return getStat(StatType.MINING_FORTUNE);
    }
    
    @Override
    public double getFarmingFortune() {
        return getStat(StatType.FARMING_FORTUNE);
    }
    
    @Override
    public double getForagingFortune() {
        return getStat(StatType.FORAGING_FORTUNE);
    }
    
    @Override
    public double getTrueDef() {
        return getStat(StatType.TRUE_DEFENSE);
    }
}
