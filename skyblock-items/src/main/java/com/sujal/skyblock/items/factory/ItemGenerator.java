package com.sujal.skyblock.items.factory;

import com.sujal.skyblock.core.api.Rarity;
import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.util.SBItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Map;

public class ItemGenerator {

    public static ItemStack createItem(Item vanillaItem, String name, Rarity rarity, Map<StatType, Double> stats, String abilityName, String abilityDesc) {
        ItemStack stack = new ItemStack(vanillaItem);
        
        // 1. Set Display Name
        stack.setCustomName(Text.literal(name).formatted(rarity.getColor()));
        
        // 2. Set Stats in NBT
        stats.forEach((stat, val) -> SBItemUtils.setStat(stack, stat, val));
        SBItemUtils.setRarity(stack, rarity);
        SBItemUtils.setString(stack, "AbilityName", abilityName);
        SBItemUtils.setString(stack, "AbilityDesc", abilityDesc);

        // 3. Generate Lore (Visuals)
        updateLore(stack);

        return stack;
    }

    public static void updateLore(ItemStack stack) {
        NbtCompound display = stack.getOrCreateSubNbt("display");
        NbtList lore = new NbtList();
        
        // Stats Section
        lore.add(NbtString.of(""));
        for (StatType stat : StatType.values()) {
            double val = SBItemUtils.getStat(stack, stat);
            if (val != 0) {
                String line = "§7" + stat.getName() + ": §a+" + (int)val + stat.getIcon();
                lore.add(NbtString.of(line));
            }
        }
        
        // Ability Section
        String abName = SBItemUtils.getString(stack, "AbilityName");
        if (!abName.isEmpty()) {
            lore.add(NbtString.of(""));
            lore.add(NbtString.of("§6Ability: " + abName + " §e§lRIGHT CLICK"));
            lore.add(NbtString.of("§7" + SBItemUtils.getString(stack, "AbilityDesc")));
            lore.add(NbtString.of("§8Mana Cost: 50")); // Placeholder
            lore.add(NbtString.of("§8Cooldown: 10s")); // Placeholder
        }

        // Rarity Section
        Rarity rarity = SBItemUtils.getRarity(stack);
        lore.add(NbtString.of(""));
        lore.add(NbtString.of(rarity.getColor() + "§l" + rarity.getName() + " ITEM"));

        display.put("Lore", lore);
    }
}
