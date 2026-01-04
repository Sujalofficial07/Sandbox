package com.sujal.skyblocksandbox.item.definitions;

import com.sujal.skyblocksandbox.item.Rarity;
import com.sujal.skyblocksandbox.item.SBItemFactory;
import com.sujal.skyblocksandbox.stats.StatType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Formatting;

import java.util.EnumMap;
import java.util.Map;

public class SBItems_RangedArmor {

    // --- BOWS ---

    public static ItemStack TERMINATOR() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 310.0);
        stats.put(StatType.STRENGTH, 50.0);
        stats.put(StatType.CRIT_DAMAGE, 250.0);
        stats.put(StatType.ATTACK_SPEED, 40.0);

        ItemStack stack = SBItemFactory.create(Items.BOW, "Terminator", Rarity.LEGENDARY, stats);
        SBItems_Melee.addLore(stack,
            "§7Shoots §a3 §7arrows at once.",
            "§7Can damage endermen.",
            "§7Divides your §9Crit Chance §7by 4!",
            "",
            "§6Ability: Salvation §e§lRIGHT CLICK",
            "§7Can be cast after landing 3 hits.",
            "§7Shoot a beam, penetrating up to",
            "§e5 §7enemies.",
            "§8Cooldown: §a2s"
        );
        SBItems_Melee.setCustomModelData(stack, "TERMINATOR", 11);
        return stack;
    }

    public static ItemStack JUJU_SHORTBOW() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 310.0);
        stats.put(StatType.STRENGTH, 40.0);
        stats.put(StatType.CRIT_CHANCE, 10.0);
        stats.put(StatType.CRIT_DAMAGE, 110.0);

        ItemStack stack = SBItemFactory.create(Items.BOW, "Juju Shortbow", Rarity.EPIC, stats);
        SBItems_Melee.addLore(stack,
            "§7Shoots instantly!",
            "§7Can damage endermen.",
            "§8Requires §5Enderman Slayer III"
        );
        SBItems_Melee.setCustomModelData(stack, "JUJU_SHORTBOW", 12);
        return stack;
    }

    public static ItemStack MOSQUITO_BOW() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 251.0);
        stats.put(StatType.STRENGTH, 151.0);
        stats.put(StatType.CRIT_DAMAGE, 39.0);

        ItemStack stack = SBItemFactory.create(Items.BOW, "Mosquito Bow", Rarity.LEGENDARY, stats);
        SBItems_Melee.addLore(stack,
            "§6Ability: Nasty Bite §e§lSNEAK",
            "§7Fully charged shots cost §b11% §7of",
            "§7max mana and deal §c+19% §7damage.",
            "§7Heals for 2x the mana cost."
        );
        SBItems_Melee.setCustomModelData(stack, "MOSQUITO_BOW", 13);
        return stack;
    }

    // --- ARMOR ---

    public static ItemStack WARDEN_HELMET() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.HEALTH, 300.0);
        stats.put(StatType.DEFENSE, 100.0);

        // Uses PLAYER_HEAD typically, for now using Iron Helmet as placeholder or specific implementation needed
        ItemStack stack = SBItemFactory.create(Items.PLAYER_HEAD, "Warden Helmet", Rarity.LEGENDARY, stats);
        SBItems_Melee.addLore(stack,
            "§6Ability: Brute Force",
            "§7Halves your §f✦ Speed §7but grants",
            "§c+20% §7base weapon damage for",
            "§7every §a25 §f✦ Speed§7."
        );
        
        // Warden Helmet Texture (Skull)
        NbtCompound root = stack.getOrCreateNbt();
        NbtCompound skullOwner = new NbtCompound();
        skullOwner.putString("Name", "Warden Helmet");
        // UUID and Texture string would go here for real heads
        // root.put("SkullOwner", ...); 
        SBItems_Melee.setCustomModelData(stack, "WARDEN_HELMET", 14);
        return stack;
    }

    public static ItemStack SUPERIOR_DRAGON_HELMET() {
        return createSuperiorPiece(Items.DIAMOND_HELMET, "Helmet", 90, 130, 10, 2, 10, 25, 3);
    }
    
    public static ItemStack SUPERIOR_DRAGON_CHESTPLATE() {
        return createSuperiorPiece(Items.DIAMOND_CHESTPLATE, "Chestplate", 150, 190, 10, 2, 10, 25, 3);
    }

    public static ItemStack SUPERIOR_DRAGON_LEGGINGS() {
        return createSuperiorPiece(Items.DIAMOND_LEGGINGS, "Leggings", 130, 170, 10, 2, 10, 25, 3);
    }

    public static ItemStack SUPERIOR_DRAGON_BOOTS() {
        return createSuperiorPiece(Items.DIAMOND_BOOTS, "Boots", 80, 110, 10, 2, 10, 25, 3);
    }

    public static ItemStack NECRON_CHESTPLATE() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.HEALTH, 260.0);
        stats.put(StatType.DEFENSE, 140.0);
        stats.put(StatType.STRENGTH, 40.0);
        stats.put(StatType.CRIT_DAMAGE, 30.0);
        stats.put(StatType.MANA, 30.0); // Witherborn set base

        ItemStack stack = SBItemFactory.create(Items.LEATHER_CHESTPLATE, "Necron's Chestplate", Rarity.LEGENDARY, stats);
        // Set Color to Orange/Red (Necron Color)
        NbtCompound display = stack.getOrCreateSubNbt("display");
        display.putInt("color", 16733525); 

        SBItems_Melee.addLore(stack,
            "§6Full Set Bonus: Witherborn",
            "§7Spawns a wither minion every",
            "§e30s §7healing you and dealing",
            "§7damage to nearby enemies."
        );
        SBItems_Melee.setCustomModelData(stack, "NECRON_CHESTPLATE", 19);
        return stack;
    }

    // --- Helper for Superior Armor ---
    private static ItemStack createSuperiorPiece(net.minecraft.item.Item item, String name, double hp, double def, double str, double cc, double cd, double intel, double speed) {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.HEALTH, hp);
        stats.put(StatType.DEFENSE, def);
        stats.put(StatType.STRENGTH, str);
        stats.put(StatType.CRIT_CHANCE, cc);
        stats.put(StatType.CRIT_DAMAGE, cd);
        stats.put(StatType.MANA, intel);
        stats.put(StatType.SPEED, speed);

        ItemStack stack = SBItemFactory.create(item, "Superior Dragon " + name, Rarity.LEGENDARY, stats);
        SBItems_Melee.addLore(stack,
            "§6Full Set Bonus: Superior Blood",
            "§7Most of your stats are increased",
            "§7by §a5%§7 and §6Aspect of the",
            "§6Dragons §7ability deals §a50%",
            "§7more damage."
        );
        SBItems_Melee.setCustomModelData(stack, "SUPERIOR_" + name.toUpperCase(), 15);
        return stack;
    }
}
