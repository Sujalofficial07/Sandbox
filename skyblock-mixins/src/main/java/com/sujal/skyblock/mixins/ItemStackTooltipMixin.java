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

        // Only modify if it's a SkyBlock item
        if (!SBItemUtils.isSkyblockItem(stack)) return;

        List<Text> tooltip = cir.getReturnValue();
        
        // 1. Keep the Name (First line), Remove everything else (Vanilla attributes)
        Text name = tooltip.get(0);
        tooltip.clear();
        tooltip.add(name);

        // 2. Add Stats (Hypixel Style)
        addStatLine(tooltip, stack, StatType.STRENGTH, Formatting.RED);
        addStatLine(tooltip, stack, StatType.DEFENSE, Formatting.GREEN);
        addStatLine(tooltip, stack, StatType.HEALTH, Formatting.RED);
        addStatLine(tooltip, stack, StatType.INTELLIGENCE, Formatting.AQUA);
        addStatLine(tooltip, stack, StatType.CRIT_CHANCE, Formatting.BLUE);
        addStatLine(tooltip, stack, StatType.CRIT_DAMAGE, Formatting.BLUE);
        addStatLine(tooltip, stack, StatType.SPEED, Formatting.WHITE);
        addStatLine(tooltip, stack, StatType.MINING_FORTUNE, Formatting.GOLD);
        addStatLine(tooltip, stack, StatType.FARMING_FORTUNE, Formatting.GOLD);

        tooltip.add(Text.empty()); // Spacer

        // 3. Add Ability
        String abilityName = SBItemUtils.getAbilityName(stack);
        if (!abilityName.isEmpty()) {
            tooltip.add(Text.literal("Ability: " + abilityName).formatted(Formatting.GOLD, Formatting.BOLD));
            String desc = SBItemUtils.getAbilityDesc(stack);
            
            // Text Wrapping logic (Basic)
            String[] lines = desc.split("\n");
            for (String line : lines) {
                tooltip.add(Text.literal(line).formatted(Formatting.GRAY));
            }
            
            tooltip.add(Text.literal("Mana Cost: 50").formatted(Formatting.DARK_GRAY)); // Placeholder logic
            tooltip.add(Text.literal("Cooldown: 10s").formatted(Formatting.DARK_GRAY)); // Placeholder logic
            tooltip.add(Text.empty());
        }

        // 4. Add Rarity and Type Tag (e.g., "LEGENDARY SWORD")
        Rarity rarity = SBItemUtils.getRarity(stack);
        ItemType type = SBItemUtils.getType(stack);
        
        MutableText footer = Text.literal(rarity.name() + " " + type.getName())
                .formatted(rarity.getColor(), Formatting.BOLD);
                
        tooltip.add(footer);
    }

    private void addStatLine(List<Text> tooltip, ItemStack stack, StatType stat, Formatting color) {
        double value = SBItemUtils.getStat(stack, stat);
        if (value != 0) {
            String valStr = (value > 0 ? "+" : "") + (int)value;
            MutableText line = Text.literal(stat.getName() + ": " + valStr)
                    .formatted(Formatting.GRAY);
            // Append icon if you want, handled by texture pack usually, but we can append text
            // line.append(Text.literal(" " + stat.getIcon()).formatted(color));
            tooltip.add(line);
        }
    }
}
