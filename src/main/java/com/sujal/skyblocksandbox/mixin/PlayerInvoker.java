package com.sujal.skyblocksandbox.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerEntity.class)
public interface PlayerInvoker {
    // This makes the protected method accessible
    @Invoker("closeHandledScreen")
    void invokeCloseHandledScreen();
}
