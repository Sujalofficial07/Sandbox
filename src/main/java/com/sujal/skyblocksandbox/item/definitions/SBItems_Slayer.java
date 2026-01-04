package com.sujal.skyblocksandbox.item.definitions;

import com.sujal.skyblocksandbox.item.Rarity;
import com.sujal.skyblocksandbox.item.SBItemFactory;
import com.sujal.skyblocksandbox.stats.StatType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import java.util.EnumMap;
import java.util.Map;

public class SBItems_Slayer {

    public static ItemStack OVERFLUX_POWER_ORB() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        ItemStack stack = SBItemFactory.create(Items.PLAYER_HEAD, "Overflux Power Orb", Rarity.EPIC, stats);
        SBItemFactory.addLore(stack,
            "§6Ability: Deploy §e§lRIGHT CLICK",
            "§7Place an orb for §a60s §7buffing",
            "§7up to §b4 §7players within §a18",
            "§7blocks.",
            "§8Costs 50% of Mana",
            "",
            "§7Buffs:",
            "§c+2.5% §7health regen/s",
            "§c+5 §aChange",
            "§4+5 §fStrength",
            "§b+100% §7mana regen"
        );
        SBItemFactory.setCustomModelData(stack, "OVERFLUX_POWER_ORB", 200);
        return stack;
    }

    public static ItemStack WAND_OF_ATONEMENT() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        ItemStack stack = SBItemFactory.create(Items.STICK, "Wand of Atonement", Rarity.EPIC, stats);
        SBItemFactory.addLore(stack,
            "§6Ability: Huge Heal §e§lRIGHT CLICK",
            "§7Heals §c170/s §7for §a7s§7.",
            "§7Wand heals don't stack.",
            "§8Mana Cost: §3240"
        );
        SBItemFactory.setCustomModelData(stack, "WAND_OF_ATONEMENT", 201);
        return stack;
    }
    
    public static ItemStack VOIDEDGE_KATANA() {
        Map<StatType, Double> stats = new EnumMap<>(StatType.class);
        stats.put(StatType.DAMAGE, 125.0);
        stats.put(StatType.STRENGTH, 60.0);
        
        ItemStack stack = SBItemFactory.create(Items.DIAMOND_SWORD, "Voidedge Katana", Rarity.RARE, stats);
        SBItemFactory.addLore(stack, 
            "§6Ability: Soulcry §e§lRIGHT CLICK",
            "§7Gain §c+200% §7damage against",
            "§7Endermen for §a4s§7.",
            "§8Mana Cost: §3200"
        );
        SBItemFactory.setCustomModelData(stack, "VOIDEDGE_KATANA", 202);
        return stack;
    }
}
