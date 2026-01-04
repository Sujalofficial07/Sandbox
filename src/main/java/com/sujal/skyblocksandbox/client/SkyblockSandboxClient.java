package com.sujal.skyblocksandbox.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

@Environment(EnvType.CLIENT)
public class SkyblockSandboxClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register HUD
        HudRenderCallback.EVENT.register(new SkyblockHud());
    }
}
