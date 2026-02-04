package com.sujal.skyblock.core.internal.events;

import com.sujal.skyblock.core.api.events.SkyBlockEvent;
import com.sujal.skyblock.core.api.events.SkyBlockEventAPI;
import com.sujal.skyblock.core.api.events.SkyBlockTickEvent;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EventBus implements SkyBlockEventAPI.SkyBlockEventProvider {
    private final Map<Class<?>, List<Consumer<?>>> listeners;
    private long tickCounter = 0;
    
    public EventBus() {
        this.listeners = new ConcurrentHashMap<>();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends SkyBlockEvent> void registerListener(Class<T> eventClass, Consumer<T> listener) {
        listeners.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(listener);
    }
    
    @Override
    public <T extends SkyBlockEvent> void unregisterListener(Class<T> eventClass, Consumer<T> listener) {
        List<Consumer<?>> eventListeners = listeners.get(eventClass);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends SkyBlockEvent> void fireEvent(T event) {
        List<Consumer<?>> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            for (Consumer<?> listener : new ArrayList<>(eventListeners)) {
                ((Consumer<T>) listener).accept(event);
                if (event.isCancelled()) {
                    break;
                }
            }
        }
    }
    
    @Override
    public void fireServerTick(MinecraftServer server) {
        tickCounter++;
        fireEvent(new SkyBlockTickEvent(server, tickCounter));
    }
}
