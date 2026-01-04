package com.sujal.skyblocksandbox.item.definitions;

import com.sujal.skyblocksandbox.item.Rarity;
import com.sujal.skyblocksandbox.item.SBItemFactory;
import com.sujal.skyblocksandbox.stats.StatType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import java.util.EnumMap;
import java.util.Map;

public class SBItems_Accessories {

    public static ItemStack HEGEMONY_ARTIFACT() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.ATTACK_SPEED, 10.0);
        stats.put(StatType.CRIT_DAMAGE, 5.0);
        stats.put(StatType.STRENGTH, 5.0);
        stats.put(StatType.MANA, 10.0);

        ItemStack stack = SBItemFactory.create(Items.PLAYER_HEAD, "Hegemony Artifact", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack, "§7Double stats if you have a", "§7Hegemony subscription.");
        SBItemFactory.setCustomModelData(stack, "HEGEMONY_ARTIFACT", 100);
        return stack;
    }

    public static ItemStack ENDER_ARTIFACT() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        ItemStack stack = SBItemFactory.create(Items.PLAYER_HEAD, "Ender Artifact", Rarity.EPIC, stats);
        SBItemFactory.addLore(stack, "§7Reduces damage taken from", "§7Endermen by §a20%§7.");
        SBItemFactory.setCustomModelData(stack, "ENDER_ARTIFACT", 101);
        return stack;
    }

    public static ItemStack WITHER_RELIC() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        ItemStack stack = SBItemFactory.create(Items.WITHER_SKELETON_SKULL, "Wither Relic", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack, "§7Reduces damage taken from", "§7Withers by §a20%§7.");
        SBItemFactory.setCustomModelData(stack, "WITHER_RELIC", 102);
        return stack;
    }
    
    public static ItemStack SEAL_OF_THE_FAMILY() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        ItemStack stack = SBItemFactory.create(Items.CLOCK, "Seal of the Family", Rarity.EPIC, stats);
        SBItemFactory.addLore(stack, "§7Enjoy a §a3% §7discount on", "§7most shops!");
        SBItemFactory.setCustomModelData(stack, "SEAL_OF_THE_FAMILY", 103);
        return stack;
    }
}
