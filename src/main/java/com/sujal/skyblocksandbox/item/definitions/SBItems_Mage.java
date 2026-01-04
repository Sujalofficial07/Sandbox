package com.sujal.skyblocksandbox.item.definitions;

import com.sujal.skyblocksandbox.item.Rarity;
import com.sujal.skyblocksandbox.item.SBItemFactory;
import com.sujal.skyblocksandbox.stats.StatType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import java.util.EnumMap;
import java.util.Map;

public class SBItems_Mage {

    public static ItemStack MIDAS_STAFF() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 130.0);
        stats.put(StatType.STRENGTH, 50.0);
        stats.put(StatType.MANA, 500.0); // High Intel scaling base

        ItemStack stack = SBItemFactory.create(Items.GOLDEN_SHOVEL, "Midas Staff", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack,
            "§7Damage Bonus: §d+26,000",
            "",
            "§6Ability: Molten Wave §e§lRIGHT CLICK",
            "§7Cast a wave of molten gold",
            "§7in the direction you are facing!",
            "§7Deals §a32,000 §7damage to",
            "§7enemies hit.",
            "§8Mana Cost: §3500", // 100M Midas cost usually
            "§8Cooldown: §a1s"
        );
        SBItemFactory.setCustomModelData(stack, "MIDAS_STAFF", 26);
        return stack;
    }

    public static ItemStack SPIRIT_SCEPTRE() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 180.0);
        stats.put(StatType.MANA, 300.0); 

        // Uses Allium flower usually or custom texture
        ItemStack stack = SBItemFactory.create(Items.ALLIUM, "Spirit Sceptre", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack,
            "§6Ability: Guided Bat §e§lRIGHT CLICK",
            "§7Shoots a guided spirit bat",
            "§7that follows you and explodes",
            "§7on impact dealing §a2,000",
            "§7damage.",
            "§8Mana Cost: §3250",
            "",
            "§8Dungeon Item"
        );
        SBItemFactory.setCustomModelData(stack, "SPIRIT_SCEPTRE", 27);
        return stack;
    }

    public static ItemStack YETI_SWORD() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 150.0);
        stats.put(StatType.STRENGTH, 170.0);
        stats.put(StatType.MANA, 50.0);

        ItemStack stack = SBItemFactory.create(Items.IRON_SWORD, "Yeti Sword", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack,
            "§6Ability: Terrain Toss §e§lRIGHT CLICK",
            "§7Throws a chunk of terrain in the",
            "§7direction you are facing!",
            "§7Deals up to §a4,000 §7damage.",
            "§8Mana Cost: §3250",
            "§8Cooldown: §a1s"
        );
        SBItemFactory.setCustomModelData(stack, "YETI_SWORD", 28);
        return stack;
    }

    public static ItemStack FIRE_VEIL_WAND() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 60.0);
        stats.put(StatType.MANA, 200.0);

        ItemStack stack = SBItemFactory.create(Items.BLAZE_ROD, "Fire Veil Wand", Rarity.EPIC, stats);
        SBItemFactory.addLore(stack,
            "§6Ability: Fire Veil §e§lRIGHT CLICK",
            "§7Creates a ring of fire around",
            "§7you for §a5s§7, dealing",
            "§a1,000 §7damage per second to",
            "§7mobs within.",
            "§8Mana Cost: §3300",
            "§8Cooldown: §a1s"
        );
        SBItemFactory.setCustomModelData(stack, "FIRE_VEIL_WAND", 29);
        return stack;
    }

    public static ItemStack ICE_SPRAY_WAND() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 120.0);
        stats.put(StatType.MANA, 50.0);

        ItemStack stack = SBItemFactory.create(Items.STICK, "Ice Spray Wand", Rarity.EPIC, stats);
        SBItemFactory.addLore(stack,
            "§6Ability: Ice Spray §e§lRIGHT CLICK",
            "§7Shoots ice in front of you that",
            "§7freezes mobs for §a5s §7and",
            "§7makes them take §c10% §7increased",
            "§7damage!",
            "§8Mana Cost: §350",
            "§8Cooldown: §a5s"
        );
        SBItemFactory.setCustomModelData(stack, "ICE_SPRAY_WAND", 30);
        return stack;
    }
}
