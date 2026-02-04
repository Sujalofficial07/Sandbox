package com.sujal.skyblock.core.mixin;

import com.sujal.skyblock.core.api.util.NBTUtils;
import com.sujal.skyblock.core.api.util.TextUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    
    @Shadow
    public abstract NbtCompound getOrCreateNbt();
    
    @Inject(method = "getTooltip", at = @At("RETURN"))
    private void onGetTooltip(TooltipType context, CallbackInfoReturnable<List<Text>> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        NbtCompound nbt = stack.getOrCreateNbt();
        
        if (!NBTUtils.hasSkyBlockTag(nbt)) {
            return;
        }
        
        List<Text> tooltip = cir.getReturnValue();
        
        List<String> lore = NBTUtils.getLore(nbt);
        if (!lore.isEmpty()) {
            tooltip.add(Text.empty());
            for (String line : lore) {
                tooltip.add(TextUtils.parseColorCodes(line));
            }
        }
        
        String rarity = NBTUtils.getRarity(nbt);
        if (!rarity.isEmpty() && !rarity.equals("COMMON")) {
            tooltip.add(Text.empty());
            tooltip.add(TextUtils.rarityColor(rarity, rarity));
        }
    }
}
