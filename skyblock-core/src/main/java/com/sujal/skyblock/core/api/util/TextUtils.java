package com.sujal.skyblock.core.api.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
    private static final Pattern COLOR_CODE_PATTERN = Pattern.compile("&([0-9a-fk-or])");
    
    public static Text parseColorCodes(String text) {
        if (text == null || text.isEmpty()) {
            return Text.empty();
        }
        
        MutableText result = Text.empty();
        Matcher matcher = COLOR_CODE_PATTERN.matcher(text);
        int lastEnd = 0;
        Formatting currentFormat = Formatting.WHITE;
        
        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                result.append(Text.literal(text.substring(lastEnd, matcher.start())).formatted(currentFormat));
            }
            
            char code = matcher.group(1).charAt(0);
            Formatting format = getFormattingByCode(code);
            if (format != null) {
                currentFormat = format;
            }
            
            lastEnd = matcher.end();
        }
        
        if (lastEnd < text.length()) {
            result.append(Text.literal(text.substring(lastEnd)).formatted(currentFormat));
        }
        
        return result;
    }
    
    private static Formatting getFormattingByCode(char code) {
        return switch (code) {
            case '0' -> Formatting.BLACK;
            case '1' -> Formatting.DARK_BLUE;
            case '2' -> Formatting.DARK_GREEN;
            case '3' -> Formatting.DARK_AQUA;
            case '4' -> Formatting.DARK_RED;
            case '5' -> Formatting.DARK_PURPLE;
            case '6' -> Formatting.GOLD;
            case '7' -> Formatting.GRAY;
            case '8' -> Formatting.DARK_GRAY;
            case '9' -> Formatting.BLUE;
            case 'a' -> Formatting.GREEN;
            case 'b' -> Formatting.AQUA;
            case 'c' -> Formatting.RED;
            case 'd' -> Formatting.LIGHT_PURPLE;
            case 'e' -> Formatting.YELLOW;
            case 'f' -> Formatting.WHITE;
            case 'k' -> Formatting.OBFUSCATED;
            case 'l' -> Formatting.BOLD;
            case 'm' -> Formatting.STRIKETHROUGH;
            case 'n' -> Formatting.UNDERLINE;
            case 'o' -> Formatting.ITALIC;
            case 'r' -> Formatting.RESET;
            default -> null;
        };
    }
    
    public static String stripColorCodes(String text) {
        if (text == null) return "";
        return COLOR_CODE_PATTERN.matcher(text).replaceAll("");
    }
    
    public static Text rarityColor(String rarity, String text) {
        return switch (rarity.toUpperCase()) {
            case "COMMON" -> Text.literal(text).formatted(Formatting.WHITE);
            case "UNCOMMON" -> Text.literal(text).formatted(Formatting.GREEN);
            case "RARE" -> Text.literal(text).formatted(Formatting.BLUE);
            case "EPIC" -> Text.literal(text).formatted(Formatting.DARK_PURPLE);
            case "LEGENDARY" -> Text.literal(text).formatted(Formatting.GOLD);
            case "MYTHIC" -> Text.literal(text).formatted(Formatting.LIGHT_PURPLE);
            case "DIVINE" -> Text.literal(text).formatted(Formatting.AQUA);
            case "SPECIAL" -> Text.literal(text).formatted(Formatting.RED);
            case "VERY SPECIAL" -> Text.literal(text).formatted(Formatting.RED);
            default -> Text.literal(text).formatted(Formatting.GRAY);
        };
    }
    
    public static String formatNumber(long number) {
        if (number < 1000) return String.valueOf(number);
        if (number < 1000000) return String.format("%.1fk", number / 1000.0);
        if (number < 1000000000) return String.format("%.1fM", number / 1000000.0);
        return String.format("%.1fB", number / 1000000000.0);
    }
    
    public static String formatDecimal(double number, int decimals) {
        return String.format("%." + decimals + "f", number);
    }
}
