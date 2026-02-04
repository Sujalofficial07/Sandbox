package com.sujal.skyblock.core.api.util;

import java.util.Random;

public class MathUtils {
    private static final Random RANDOM = new Random();
    
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
    
    public static boolean rollPercentage(double percent) {
        return RANDOM.nextDouble() * 100.0 < percent;
    }
    
    public static int randomRange(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
    
    public static double randomRange(double min, double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }
    
    public static double calculateDefenseReduction(double defense) {
        return defense / (defense + 100.0);
    }
    
    public static double calculateBaseDamage(double weaponDamage, double strength) {
        return (5.0 + weaponDamage + Math.floor(strength / 5.0)) * (1.0 + strength / 100.0);
    }
    
    public static double calculateCritMultiplier(double critDamage) {
        return 1.0 + (critDamage / 100.0);
    }
    
    public static int calculateFortuneDrops(double fortune) {
        int guaranteed = (int) Math.floor(fortune / 100.0);
        double remainder = fortune % 100.0;
        int bonus = rollPercentage(remainder) ? 1 : 0;
        return guaranteed + bonus;
    }
    
    public static long calculateSkillXP(int level) {
        int[] xpTable = {
            50, 125, 200, 300, 500,
            750, 1000, 1500, 2000, 3500,
            5000, 7500, 10000, 15000, 20000,
            30000, 50000, 75000, 100000, 200000,
            300000, 400000, 500000, 600000, 700000,
            800000, 900000, 1000000, 1100000, 1200000,
            1300000, 1400000, 1500000, 1600000, 1700000,
            1800000, 1900000, 2000000, 2100000, 2200000,
            2300000, 2400000, 2500000, 2600000, 2750000,
            2900000, 3100000, 3400000, 3700000, 4000000
        };
        
        if (level < 1 || level > 50) return 0;
        
        long total = 0;
        for (int i = 0; i < Math.min(level, xpTable.length); i++) {
            total += xpTable[i];
        }
        return total;
    }
    
    public static int getLevelFromXP(long xp) {
        int level = 0;
        long cumulative = 0;
        
        while (cumulative < xp && level < 50) {
            level++;
            cumulative += calculateSkillXP(level) - calculateSkillXP(level - 1);
        }
        
        return level;
    }
}
