package com.sujal.skyblocksandbox.item.definitions;

import com.sujal.skyblocksandbox.item.Rarity;
import com.sujal.skyblocksandbox.item.SBItemFactory;
import com.sujal.skyblocksandbox.stats.StatType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import java.util.EnumMap;
import java.util.Map;

public class SBItems_Dungeon {

    public static ItemStack BONZO_STAFF() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 160.0);
        stats.put(StatType.MANA, 250.0);

        ItemStack stack = SBItemFactory.create(Items.BLAZE_ROD, "Bonzo's Staff", Rarity.RARE, stats);
        SBItemFactory.addLore(stack,
            "§6Ability: Showtime §e§lRIGHT CLICK",
            "§7Shoots balloons that create",
            "§7a large explosion on impact.",
            "§8Mana Cost: §3100",
            "§8Cooldown: §a0.5s"
        );
        SBItemFactory.setCustomModelData(stack, "BONZO_STAFF", 300);
        return stack;
    }

    public static ItemStack SHADOW_ASSASSIN_CHESTPLATE() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.HEALTH, 210.0);
        stats.put(StatType.DEFENSE, 80.0);
        stats.put(StatType.STRENGTH, 25.0);
        stats.put(StatType.CRIT_DAMAGE, 25.0);

        ItemStack stack = SBItemFactory.create(Items.LEATHER_CHESTPLATE, "Shadow Assassin Chestplate", Rarity.EPIC, stats);
        // Black Color
        stack.getOrCreateSubNbt("display").putInt("color", 0);
        SBItemFactory.addLore(stack,
            "§6Full Set Bonus: Shadow Assassin",
            "§7Collect the shadows of enemies",
            "§7killed increasing your stats."
        );
        SBItemFactory.setCustomModelData(stack, "SA_CHESTPLATE", 301);
        return stack;
    }
}
