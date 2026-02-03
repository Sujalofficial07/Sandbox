package com.sujal.skyblock.mixin;

import com.sujal.skyblock.core.data.DataHolder;
import com.sujal.skyblock.core.data.PlayerProfileData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity implements DataHolder {
    
    private final PlayerProfileData skyBlockData = new PlayerProfileData();

    @Override
    public PlayerProfileData getSkyBlockData() {
        return this.skyBlockData;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void injectWrite(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound sbTag = new NbtCompound();
        skyBlockData.writeNbt(sbTag);
        nbt.put("SkyBlockCore", sbTag);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void injectRead(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("SkyBlockCore")) {
            skyBlockData.readNbt(nbt.getCompound("SkyBlockCore"));
        }
    }
}
