package com.sujal.skyblock.core.mixin;

import com.sujal.skyblock.core.SkyBlockCore;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    
    @Inject(method = "tick", at = @At("TAIL"))
    private void onServerTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        MinecraftServer server = (MinecraftServer) (Object) this;
        SkyBlockCore core = SkyBlockCore.getInstance();
        
        if (core != null && core.getEventBus() != null) {
            core.getEventBus().fireServerTick(server);
        }
    }
}
