package com.sujal.skyblock.skills.util;

import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.skills.api.SkillType;
import com.sujal.skyblock.skills.data.SkillExpTable;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SkillUtil {

    public static void addXp(ServerPlayerEntity player, SkyblockProfile profile, SkillType skill, double amount) {
        double currentXp = profile.getSkillXp(skill.getName());
        int oldLevel = SkillExpTable.getLevel(currentXp);
        
        profile.addSkillXp(skill.getName(), amount);
        
        double newXp = profile.getSkillXp(skill.getName());
        int newLevel = SkillExpTable.getLevel(newXp);
        
        // Action Bar Notification
        player.sendMessage(Text.literal("§3+ " + amount + " " + skill.getName() + " (" + (int)newXp + "/" + (int)SkillExpTable.getXpForNextLevel(newLevel) + ")"), true);

        // Level Up Logic
        if (newLevel > oldLevel) {
            sendLevelUpMessage(player, skill, newLevel);
        }
    }

    private static void sendLevelUpMessage(ServerPlayerEntity player, SkillType skill, int level) {
        player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 1f);
        
        player.sendMessage(Text.literal("--------------------------------"), false);
        player.sendMessage(Text.literal("§6§lSKILL LEVEL UP §3" + skill.getName() + " §8" + (level-1) + "➜" + level), false);
        player.sendMessage(Text.literal(""), false);
        player.sendMessage(Text.literal("§a§lREWARDS"), false);
        player.sendMessage(Text.literal("  §e" + skill.getPerkName() + " " + toRoman(level)), false);
        // Add specific stats based on skill type (Simplified)
        if(skill == SkillType.COMBAT) player.sendMessage(Text.literal("  §c+1% Crit Chance"), false);
        if(skill == SkillType.FARMING) player.sendMessage(Text.literal("  §a+4 Farming Fortune"), false);
        player.sendMessage(Text.literal("  §6+ Coins"), false);
        player.sendMessage(Text.literal("--------------------------------"), false);
    }

    private static String toRoman(int num) {
        // Simplified roman numeral converter
        if (num == 1) return "I";
        if (num == 2) return "II";
        if (num == 3) return "III";
        if (num == 4) return "IV";
        if (num == 5) return "V";
        return String.valueOf(num);
    }
}
