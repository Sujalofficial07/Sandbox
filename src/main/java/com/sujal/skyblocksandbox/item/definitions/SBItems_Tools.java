package com.sujal.skyblocksandbox.item.definitions;

import com.sujal.skyblocksandbox.item.Rarity;
import com.sujal.skyblocksandbox.item.SBItemFactory;
import com.sujal.skyblocksandbox.stats.StatType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import java.util.EnumMap;
import java.util.Map;

public class SBItems_Tools {

    public static ItemStack DIVANS_DRILL() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 100.0);
        // Mining Stats usually use custom lore/logic, but we simulate them here
        // In a real mod, add MINING_SPEED and MINING_FORTUNE to StatType
        
        ItemStack stack = SBItemFactory.create(Items.PRISMARINE_SHARD, "Divan's Drill", Rarity.MYTHIC, stats);
        SBItemFactory.addLore(stack,
            "§7Breaking Power 10",
            "",
            "§7Mining Speed: §a+1,800",
            "§7Mining Fortune: §a+120",
            "",
            "§6Ability: Alloy",
            "§7Grants §a+50 §6Mining Fortune §7and",
            "§a+100 §6Mining Speed §7to all players",
            "§7within §a64 §7blocks.",
            "",
            "§8This item can be reforged!",
            "§8Fuel: §20/100k"
        );
        SBItemFactory.setCustomModelData(stack, "DIVANS_DRILL", 20);
        return stack;
    }

    public static ItemStack TITANIUM_PICKAXE_X555() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 60.0);

        ItemStack stack = SBItemFactory.create(Items.DIAMOND_PICKAXE, "Titanium Pickaxe x555", Rarity.EPIC, stats);
        SBItemFactory.addLore(stack,
            "§7Breaking Power 9",
            "",
            "§7Mining Speed: §a+1,200",
            "§7Mining Fortune: §a+50",
            "",
            "§7Compactness:",
            "§7Grants §a+15% §7chance to drop",
            "§7extra titanium."
        );
        SBItemFactory.setCustomModelData(stack, "TITANIUM_PICKAXE_X555", 21);
        return stack;
    }

    public static ItemStack STONK() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        
        // Gold Pickaxe is vanilla fastest
        ItemStack stack = SBItemFactory.create(Items.GOLDEN_PICKAXE, "Stonk", Rarity.EPIC, stats);
        // Force Efficiency 6 Enchantment glow/effect
        stack.addEnchantment(net.minecraft.enchantment.Enchantments.EFFICIENCY, 6);
        
        SBItemFactory.addLore(stack,
            "§7Breaking Power 1",
            "",
            "§7Mining Speed: §a+370",
            "§7Drops cobblestone when mining stone.",
            "",
            "§7Included:",
            "§9Efficiency VI",
            "§9Telekinesis I"
        );
        SBItemFactory.setCustomModelData(stack, "STONK", 22);
        return stack;
    }

    public static ItemStack TREECAPITATOR() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 50.0);

        ItemStack stack = SBItemFactory.create(Items.GOLDEN_AXE, "Treecapitator", Rarity.EPIC, stats);
        SBItemFactory.addLore(stack,
            "§6Ability: Capitate",
            "§7Breaks all logs in a single",
            "§7tree!",
            "§8Cooldown: §a2s"
        );
        SBItemFactory.setCustomModelData(stack, "TREECAPITATOR", 23);
        return stack;
    }

    public static ItemStack GRAPPLING_HOOK() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        
        ItemStack stack = SBItemFactory.create(Items.FISHING_ROD, "Grappling Hook", Rarity.UNCOMMON, stats);
        SBItemFactory.addLore(stack,
            "§7Travel around in style using",
            "§7this Grappling Hook.",
            "§8Cooldown: §a2s"
        );
        SBItemFactory.setCustomModelData(stack, "GRAPPLING_HOOK", 24);
        return stack;
    }
    
    public static ItemStack ASPECT_OF_THE_VOID() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 120.0);
        stats.put(StatType.STRENGTH, 120.0);

        ItemStack stack = SBItemFactory.create(Items.DIAMOND_SHOVEL, "Aspect of the Void", Rarity.EPIC, stats);
        SBItemFactory.addLore(stack,
            "§6Ability: Instant Transmission §e§lRIGHT CLICK",
            "§7Teleport §a8 blocks §7ahead of",
            "§7you and gain §a+60 §f✦ Speed",
            "§7for §a3 seconds§7.",
            "§8Mana Cost: §345"
        );
        SBItemFactory.setCustomModelData(stack, "ASPECT_OF_THE_VOID", 25);
        return stack;
    }
}
