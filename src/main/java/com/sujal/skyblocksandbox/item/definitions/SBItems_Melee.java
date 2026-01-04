package com.sujal.skyblocksandbox.item.definitions;

import com.sujal.skyblocksandbox.item.Rarity;
import com.sujal.skyblocksandbox.item.SBItemFactory;
import com.sujal.skyblocksandbox.stats.StatType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.EnumMap;
import java.util.Map;

public class SBItems_Melee {

    public static ItemStack HYPERION() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 260.0);
        stats.put(StatType.STRENGTH, 150.0);
        stats.put(StatType.MANA, 350.0);
        stats.put(StatType.FEROCITY, 30.0);

        ItemStack stack = SBItemFactory.create(Items.IRON_SWORD, "Hyperion", Rarity.LEGENDARY, stats);
        
        SBItemFactory.addLore(stack, 
            "§6Ability: Wither Impact §e§lRIGHT CLICK",
            "§7Teleport §a10 blocks §7ahead and",
            "§7implode dealing §c10,000 §7damage",
            "§7to nearby enemies. Also applies",
            "§7the wither shield scroll effect.",
            "§8Mana Cost: §3300",
            "",
            "§8This item can be reforged!",
            "§4☠ §cRequires Catacombs Floor VII Completion."
        );
        SBItemFactory.setCustomModelData(stack, "HYPERION", 1); 
        return stack;
    }

    public static ItemStack VALKYRIE() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 270.0);
        stats.put(StatType.STRENGTH, 145.0);
        stats.put(StatType.MANA, 60.0);
        stats.put(StatType.FEROCITY, 60.0);

        ItemStack stack = SBItemFactory.create(Items.IRON_SWORD, "Valkyrie", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack, "§6Ability: Wither Impact §e§lRIGHT CLICK", "§7Deals massive damage to nearby", "§7enemies.", "§8Mana Cost: §3300");
        SBItemFactory.setCustomModelData(stack, "VALKYRIE", 2);
        return stack;
    }

    public static ItemStack ASTRAEA() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 270.0);
        stats.put(StatType.STRENGTH, 150.0);
        stats.put(StatType.DEFENSE, 250.0);
        stats.put(StatType.MANA, 100.0);

        ItemStack stack = SBItemFactory.create(Items.IRON_SWORD, "Astraea", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack, "§6Ability: Wither Impact §e§lRIGHT CLICK", "§7Deals damage and grants defense.", "§8Mana Cost: §3300");
        SBItemFactory.setCustomModelData(stack, "ASTRAEA", 3);
        return stack;
    }

    public static ItemStack SCYLLA() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 270.0);
        stats.put(StatType.STRENGTH, 150.0);
        stats.put(StatType.CRIT_CHANCE, 12.0);
        stats.put(StatType.CRIT_DAMAGE, 35.0);

        ItemStack stack = SBItemFactory.create(Items.IRON_SWORD, "Scylla", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack, "§6Ability: Wither Impact §e§lRIGHT CLICK", "§7Deals damage and heals you.", "§8Mana Cost: §3300");
        SBItemFactory.setCustomModelData(stack, "SCYLLA", 4);
        return stack;
    }

    public static ItemStack ASPECT_OF_THE_END() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 100.0);
        stats.put(StatType.STRENGTH, 100.0);

        ItemStack stack = SBItemFactory.create(Items.DIAMOND_SWORD, "Aspect of the End", Rarity.RARE, stats);
        SBItemFactory.addLore(stack, 
            "§6Ability: Instant Transmission §e§lRIGHT CLICK",
            "§7Teleport §a8 blocks §7ahead of",
            "§7you and gain §a+50 §f✦ Speed",
            "§7for §a3 seconds§7.",
            "§8Mana Cost: §350"
        );
        SBItemFactory.setCustomModelData(stack, "ASPECT_OF_THE_END", 5);
        return stack;
    }

    public static ItemStack ASPECT_OF_THE_DRAGONS() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 225.0);
        stats.put(StatType.STRENGTH, 100.0);

        ItemStack stack = SBItemFactory.create(Items.DIAMOND_SWORD, "Aspect of the Dragons", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack, 
            "§6Ability: Dragon Rage §e§lRIGHT CLICK",
            "§7All Monsters in front of you",
            "§7take §a12,000 §7damage. Hit",
            "§7monsters take large knockback.",
            "§8Mana Cost: §3100"
        );
        SBItemFactory.setCustomModelData(stack, "ASPECT_OF_THE_DRAGONS", 6);
        return stack;
    }

    public static ItemStack GIANTS_SWORD() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 500.0);

        ItemStack stack = SBItemFactory.create(Items.IRON_SWORD, "Giant's Sword", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack, 
            "§6Ability: Giant Slam §e§lRIGHT CLICK",
            "§7Slam your sword into the ground",
            "§7dealing §c100,000 §7damage to",
            "§7nearby enemies.",
            "§8Mana Cost: §3100",
            "§8Cooldown: §a30s"
        );
        SBItemFactory.setCustomModelData(stack, "GIANTS_SWORD", 7);
        return stack;
    }

    public static ItemStack LIVID_DAGGER() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 210.0);
        stats.put(StatType.STRENGTH, 60.0);
        stats.put(StatType.CRIT_CHANCE, 100.0); 
        stats.put(StatType.CRIT_DAMAGE, 50.0);
        stats.put(StatType.ATTACK_SPEED, 100.0); 

        ItemStack stack = SBItemFactory.create(Items.IRON_SWORD, "Livid Dagger", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack, 
            "§6Ability: Throw §e§lRIGHT CLICK",
            "§7Throw your dagger at your enemies!",
            "§8Mana Cost: §3150",
            "§8Cooldown: §a5s",
            "",
            "§7Your crits deal §9150% §7more",
            "§7damage if you hit a mob from",
            "§7behind."
        );
        SBItemFactory.setCustomModelData(stack, "LIVID_DAGGER", 8);
        return stack;
    }

    public static ItemStack SHADOW_FURY() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 300.0);
        stats.put(StatType.STRENGTH, 125.0);
        stats.put(StatType.SPEED, 30.0);

        ItemStack stack = SBItemFactory.create(Items.DIAMOND_SWORD, "Shadow Fury", Rarity.LEGENDARY, stats);
        SBItemFactory.addLore(stack, 
            "§6Ability: Shadowstep §e§lRIGHT CLICK",
            "§7Rapidly teleport to up to §c5",
            "§7enemies within §a12 §7blocks",
            "§7rooting each for §a3s §7and",
            "§7dealing damage.",
            "§8Mana Cost: §350",
            "§8Cooldown: §a15s"
        );
        SBItemFactory.setCustomModelData(stack, "SHADOW_FURY", 9);
        return stack;
    }

    public static ItemStack EMERALD_BLADE() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 130.0);

        ItemStack stack = SBItemFactory.create(Items.DIAMOND_SWORD, "Emerald Blade", Rarity.EPIC, stats);
        SBItemFactory.addLore(stack, 
            "§7A powerful blade made from pure",
            "§7Emeralds. This blade becomes",
            "§7stronger as you carry more",
            "§6coins §7in your purse."
        );
        SBItemFactory.setCustomModelData(stack, "EMERALD_BLADE", 10);
        return stack;
    }
}
