package com.sujal.skyblock.core.internal.stats;

import com.sujal.skyblock.core.api.stats.SkyBlockStats;
import com.sujal.skyblock.core.api.util.MathUtils;

public class StatsCalculator {
    
    public double calculateDamage(SkyBlockStats stats, double baseWeaponDamage, boolean isCrit) {
        double strength = stats.getStrength();
        
        double baseDamage = MathUtils.calculateBaseDamage(baseWeaponDamage, strength);
        
        if (isCrit) {
            double critMultiplier = MathUtils.calculateCritMultiplier(stats.getCritDamage());
            baseDamage *= critMultiplier;
        }
        
        int ferocity = (int) stats.getFerocity();
        if (ferocity > 0) {
            int extraHits = ferocity / 100;
            double remainderChance = (ferocity % 100) / 100.0;
            
            if (Math.random() < remainderChance) {
                extraHits++;
            }
            
            baseDamage *= (1 + extraHits);
        }
        
        return baseDamage;
    }
    
    public double calculateDefenseReduction(SkyBlockStats stats, double incomingDamage) {
        double defense = stats.getDefense();
        double trueDefense = stats.getTrueDef();
        
        double defenseReduction = MathUtils.calculateDefenseReduction(defense);
        
        double afterDefense = incomingDamage * (1.0 - defenseReduction);
        
        double finalDamage = Math.max(0, afterDefense - trueDefense);
        
        return finalDamage;
    }
}
