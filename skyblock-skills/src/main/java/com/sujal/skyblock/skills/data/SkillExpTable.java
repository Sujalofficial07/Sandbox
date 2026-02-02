package com.sujal.skyblock.skills.data;

public class SkillExpTable {
    // Standard Hypixel Curve (simplified for example, can hold 60 values)
    private static final int[] XP_TABLE = {
        0, 50, 175, 375, 675, 1175, 1925, 2925, 4425, 6425, 9925, 14925, 
        22425, 32425, 47425, 67425, 97425, 147425, 222425, 322425, 522425, 
        822425, 1222425, 1722425, 2322425, 3022425 // ... extends to lvl 60
    };

    public static int getLevel(double xp) {
        for (int i = 0; i < XP_TABLE.length - 1; i++) {
            if (xp < XP_TABLE[i + 1]) return i;
        }
        return XP_TABLE.length - 1; // Max Level
    }

    public static double getXpForNextLevel(int currentLevel) {
        if (currentLevel >= XP_TABLE.length - 1) return 0;
        return XP_TABLE[currentLevel + 1]; // Target XP
    }
    
    public static double getProgress(double currentXp, int currentLevel) {
        if (currentLevel >= XP_TABLE.length - 1) return 100.0;
        double startXp = XP_TABLE[currentLevel];
        double endXp = XP_TABLE[currentLevel + 1];
        return ((currentXp - startXp) / (endXp - startXp)) * 100.0;
    }
}
