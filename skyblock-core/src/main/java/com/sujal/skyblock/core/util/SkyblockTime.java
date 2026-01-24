package com.sujal.skyblock.core.util;

import net.minecraft.world.World;

import java.text.DecimalFormat;

public class SkyblockTime {

    private static final String[] MONTHS = {
        "Early Spring", "Spring", "Late Spring",
        "Early Summer", "Summer", "Late Summer",
        "Early Autumn", "Autumn", "Late Autumn",
        "Early Winter", "Winter", "Late Winter"
    };

    private static final DecimalFormat COIN_FORMAT = new DecimalFormat("#,###.0");

    public static String getDate(World world) {
        long totalTime = world.getTimeOfDay();
        long totalDays = totalTime / 24000L;
        
        // 1 Month = 31 Days (Hypixel Style roughly)
        int dayOfMonth = (int) (totalDays % 31) + 1;
        int monthIndex = (int) ((totalDays / 31) % 12);
        
        return MONTHS[monthIndex] + " " + dayOfMonth + getDaySuffix(dayOfMonth);
    }

    public static String getTime(World world) {
        long time = world.getTimeOfDay() % 24000;
        
        // Convert 0-24000 to 12h format
        int hours = (int) ((time / 1000 + 6) % 24);
        int minutes = (int) ((time % 1000) * 60 / 1000);
        String suffix = (hours >= 12) ? "pm" : "am";
        
        if (hours > 12) hours -= 12;
        if (hours == 0) hours = 12;
        
        // Add Sun/Moon icon based on time
        String icon = (time > 13000 && time < 23000) ? "☽" : "☀";

        return String.format("%d:%02d%s %s", hours, minutes, suffix, icon);
    }

    public static String formatCoins(double coins) {
        if (coins == 0) return "0";
        return COIN_FORMAT.format(coins);
    }

    private static String getDaySuffix(int n) {
        if (n >= 11 && n <= 13) return "th";
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }
}
