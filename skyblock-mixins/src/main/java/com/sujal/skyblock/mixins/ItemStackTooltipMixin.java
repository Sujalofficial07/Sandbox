package com.sujal.skyblock.mixins;

import com.sujal.skyblock.core.api.ItemType;
import com.sujal.skyblock.core.api.Rarity;
import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.util.SBItemUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackTooltipMixin {

    @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
    private void modifyTooltip(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        ItemStack stack = (ItemStack) (Object) this;

        if (!SBItemUtils.isSkyblockItem(stack)) return;

        List<Text> tooltip = cir.getReturnValue();
        
        // 1. Keep Name, Clear Vanilla
        Text name = tooltip.get(0);
        tooltip.clear();
        tooltip.add(name);

        // 2. Add Stats (Hypixel Order: Dmg -> Str -> CC -> CD -> HP -> Def -> Intel)
        addStatLine(tooltip, stack, StatType.DAMAGE);
        addStatLine(tooltip, stack, StatType.STRENGTH);
        addStatLine(tooltip, stack, StatType.CRIT_CHANCE);
        addStatLine(tooltip, stack, StatType.CRIT_DAMAGE);
        addStatLine(tooltip, stack, StatType.HEALTH);
        addStatLine(tooltip, stack, StatType.DEFENSE);
        addStatLine(tooltip, stack, StatType.INTELLIGENCE);
        addStatLine(tooltip, stack, StatType.FEROCITY);
        addStatLine(tooltip, stack, StatType.SPEED);

        tooltip.add(Text.empty());

        // 3. Add Ability
        String abilityName = SBItemUtils.getAbilityName(stack);
        if (!abilityName.isEmpty()) {
            // Header: "Ability: Name  RIGHT CLICK"
            MutableText abilityHeader = Text.literal("Ability: " + abilityName).formatted(Formatting.GOLD);
            abilityHeader.append(Text.literal("  RIGHT CLICK").formatted(Formatting.YELLOW, Formatting.BOLD));
            tooltip.add(abilityHeader);

            // Description
            String desc = SBItemUtils.getAbilityDesc(stack);
            String[] lines = desc.split("\n");
            for (String line : lines) {
                tooltip.add(Text.literal(line).formatted(Formatting.GRAY));
            }
            
            // Mana Cost & Cooldown
            // In a real implementation, store cost in NBT. For now, hardcoded visual style.
            tooltip.add(Text.literal("Mana Cost: 50").formatted(Formatting.DARK_GRAY)); 
            tooltip.add(Text.literal("Cooldown: 10s").formatted(Formatting.DARK_GRAY));
            tooltip.add(Text.empty());
        }

        // 4. Footer (Rarity + Type)
        Rarity rarity = SBItemUtils.getRarity(stack);
        ItemType type = SBItemUtils.getType(stack);
        
        // "LEGENDARY SWORD" in bold rarity color
        MutableText footer = Text.literal(rarity.getName() + " " + type.getName())
                .formatted(rarity.getColor(), Formatting.BOLD);
                
        tooltip.add(footer);
    }

    private void addStatLine(List<Text> tooltip, ItemStack stack, StatType stat) {
        double value = SBItemUtils.getStat(stack, stat);
        if (value != 0) {
            // Format: "Damage: +260" in Red
            // Hypixel format is: Gray Name + ": " + Color + "+" + Value
            
            MutableText line = Text.literal(stat.getName() + ": ")
                    .formatted(Formatting.GRAY);
            
            String valStr = "+" + (int)value;
            // Add % for Crit Chance / Crit Damage
            if (stat == StatType.CRIT_CHANCE || stat == StatType.CRIT_DAMAGE) {
                valStr += "%";
            }

            line.append(Text.literal(valStr).formatted(stat.getColor()));
            tooltip.add(line);
        }
    }
}
