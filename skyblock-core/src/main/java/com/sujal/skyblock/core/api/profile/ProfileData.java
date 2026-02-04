package com.sujal.skyblock.core.api.profile;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProfileData {
    private final Map<String, JsonObject> moduleData;
    
    public ProfileData() {
        this.moduleData = new HashMap<>();
    }
    
    public void setModuleData(String moduleId, JsonObject data) {
        moduleData.put(moduleId, data);
    }
    
    public Optional<JsonObject> getModuleData(String moduleId) {
        return Optional.ofNullable(moduleData.get(moduleId));
    }
    
    public JsonObject getOrCreateModuleData(String moduleId) {
        return moduleData.computeIfAbsent(moduleId, k -> new JsonObject());
    }
    
    public boolean hasModuleData(String moduleId) {
        return moduleData.containsKey(moduleId);
    }
    
    public void removeModuleData(String moduleId) {
        moduleData.remove(moduleId);
    }
    
    public Map<String, JsonObject> getAllModuleData() {
        return new HashMap<>(moduleData);
    }
    
    public void clearAllModuleData() {
        moduleData.clear();
    }
    
    public JsonObject serialize() {
        JsonObject root = new JsonObject();
        for (Map.Entry<String, JsonObject> entry : moduleData.entrySet()) {
            root.add(entry.getKey(), entry.getValue());
        }
        return root;
    }
    
    public void deserialize(JsonObject root) {
        moduleData.clear();
        for (String key : root.keySet()) {
            if (root.get(key).isJsonObject()) {
                moduleData.put(key, root.getAsJsonObject(key));
            }
        }
    }
}
