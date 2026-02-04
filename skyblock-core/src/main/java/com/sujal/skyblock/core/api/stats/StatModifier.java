package com.sujal.skyblock.core.api.stats;

import java.util.EnumMap;
import java.util.Map;

public class StatModifier {
    private final String id;
    private final String source;
    private final int priority;
    private final Map<StatType, Double> flatBonuses;
    private final Map<StatType, Double> percentBonuses;
    
    public StatModifier(String id, String source, int priority) {
        this.id = id;
        this.source = source;
        this.priority = priority;
        this.flatBonuses = new EnumMap<>(StatType.class);
        this.percentBonuses = new EnumMap<>(StatType.class);
    }
    
    public String getId() {
        return id;
    }
    
    public String getSource() {
        return source;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public StatModifier addFlat(StatType type, double value) {
        flatBonuses.merge(type, value, Double::sum);
        return this;
    }
    
    public StatModifier addPercent(StatType type, double percent) {
        percentBonuses.merge(type, percent, Double::sum);
        return this;
    }
    
    public double getFlatBonus(StatType type) {
        return flatBonuses.getOrDefault(type, 0.0);
    }
    
    public double getPercentBonus(StatType type) {
        return percentBonuses.getOrDefault(type, 0.0);
    }
    
    public Map<StatType, Double> getAllFlatBonuses() {
        return new EnumMap<>(flatBonuses);
    }
    
    public Map<StatType, Double> getAllPercentBonuses() {
        return new EnumMap<>(percentBonuses);
    }
    
    public boolean isEmpty() {
        return flatBonuses.isEmpty() && percentBonuses.isEmpty();
    }
}
