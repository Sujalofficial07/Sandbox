package com.sujal.skyblock.core.api.events;

import net.minecraft.server.MinecraftServer;

import java.util.function.Consumer;

public class SkyBlockEventAPI {
    private static SkyBlockEventProvider provider;
    
    public static void register(SkyBlockEventProvider provider) {
        SkyBlockEventAPI.provider = provider;
    }
    
    public static <T extends SkyBlockEvent> void registerListener(Class<T> eventClass, Consumer<T> listener) {
        if (provider != null) {
            provider.registerListener(eventClass, listener);
        }
    }
    
    public static <T extends SkyBlockEvent> void unregisterListener(Class<T> eventClass, Consumer<T> listener) {
        if (provider != null) {
            provider.unregisterListener(eventClass, listener);
        }
    }
    
    public static <T extends SkyBlockEvent> void fireEvent(T event) {
        if (provider != null) {
            provider.fireEvent(event);
        }
    }
    
    public interface SkyBlockEventProvider {
        <T extends SkyBlockEvent> void registerListener(Class<T> eventClass, Consumer<T> listener);
        <T extends SkyBlockEvent> void unregisterListener(Class<T> eventClass, Consumer<T> listener);
        <T extends SkyBlockEvent> void fireEvent(T event);
        void fireServerTick(MinecraftServer server);
    }
}
