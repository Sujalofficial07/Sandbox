package com.sujal.skyblock.stats.engine;

import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.data.SkyblockProfile;
import net.minecraft.entity.player.PlayerEntity;

public class StatCalculator {

    public static double getFinalStat(PlayerEntity player, SkyblockProfile profile, StatType stat) {
        // 1. Base Value (e.g., 100 Health)
        double total = stat.getBaseValue();

        // 2. Add Profile Bonuses (from Skills/Collections stored in profile)
        total += profile.getStatBonus(stat);

        // 3. Add Armor/Held Item stats (Placeholder for Item Module integration)
        // In a real scenario, we would iterate player.getArmorItems() here
        // and check custom NBT data. For now, we leave this hook ready.
        
        // 4. Multipliers (e.g., Armor sets increasing stat by percentage)
        // double multiplier = 1.0 + getArmorSetBonus(player);
        // total *= multiplier;

        return total;
    }
}
