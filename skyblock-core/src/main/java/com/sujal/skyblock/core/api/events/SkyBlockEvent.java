package com.sujal.skyblock.core.api.events;

public abstract class SkyBlockEvent {
    private boolean cancelled = false;
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
