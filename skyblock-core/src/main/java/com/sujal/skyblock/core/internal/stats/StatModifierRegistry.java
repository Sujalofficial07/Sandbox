package com.sujal.skyblock.core.internal.stats;

import com.sujal.skyblock.core.api.stats.StatModifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StatModifierRegistry {
    private final Map<String, StatModifier> globalModifiers;
    
    public StatModifierRegistry() {
        this.globalModifiers = new HashMap<>();
    }
    
    public void registerGlobalModifier(StatModifier modifier) {
        globalModifiers.put(modifier.getId(), modifier);
    }
    
    public void unregisterGlobalModifier(String id) {
        globalModifiers.remove(id);
    }
    
    public Optional<StatModifier> getGlobalModifier(String id) {
        return Optional.ofNullable(globalModifiers.get(id));
    }
    
    public Map<String, StatModifier> getAllGlobalModifiers() {
        return new HashMap<>(globalModifiers);
    }
}
