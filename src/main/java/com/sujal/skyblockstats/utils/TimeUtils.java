package com.sujal.skyblockstats.utils;

public class TimeUtils {
    
    private static final String[] MONTHS = {
        "Early Spring", "Spring", "Late Spring",
        "Early Summer", "Summer", "Late Summer",
        "Early Autumn", "Autumn", "Late Autumn",
        "Early Winter", "Winter", "Late Winter"
    };

    public static String getSkyBlockDate(long worldTime) {
        // 1 SkyBlock Day = 20 minutes (24000 ticks) - Same as vanilla
        // 1 SkyBlock Month = 31 Days (Arbitrary for mod)
        // 1 SkyBlock Year = 12 Months
        
        long totalDays = worldTime / 24000L;
        long dayOfMonth = (totalDays % 31) + 1;
        long monthIndex = (totalDays / 31) % 12;
        long year = (totalDays / (31 * 12)) + 1;

        String month = MONTHS[(int) monthIndex];
        
        // Suffix (st, nd, rd, th)
        String suffix = "th";
        if (dayOfMonth % 10 == 1 && dayOfMonth != 11) suffix = "st";
        else if (dayOfMonth % 10 == 2 && dayOfMonth != 12) suffix = "nd";
        else if (dayOfMonth % 10 == 3 && dayOfMonth != 13) suffix = "rd";

        return "§7" + month + " " + dayOfMonth + suffix;
    }

    public static String getTimeOfDay(long worldTime) {
        long time = worldTime % 24000;
        int hours = (int) ((time / 1000 + 6) % 24);
        int minutes = (int) ((time % 1000) * 60 / 1000); 
        return String.format("%02d:%02d", hours, minutes);
    }
}
