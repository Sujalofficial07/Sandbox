package com.sujal.skyblock.core.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CoreConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreConfig.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    private File dataDirectory;
    private boolean enableHUD;
    private boolean enableScoreboard;
    private boolean enableActionBar;
    private boolean overrideVanillaHealth;
    private boolean overrideVanillaDamage;
    private int manaRegenRate;
    
    public CoreConfig() {
        this.dataDirectory = new File("skyblock-data");
        this.enableHUD = true;
        this.enableScoreboard = true;
        this.enableActionBar = true;
        this.overrideVanillaHealth = true;
        this.overrideVanillaDamage = true;
        this.manaRegenRate = 20;
    }
    
    public static CoreConfig load() {
        File configFile = new File("config/skyblock-core.json");
        
        if (!configFile.exists()) {
            CoreConfig config = new CoreConfig();
            config.save(configFile);
            return config;
        }
        
        try (FileReader reader = new FileReader(configFile)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            CoreConfig config = new CoreConfig();
            
            if (json.has("dataDirectory")) {
                config.dataDirectory = new File(json.get("dataDirectory").getAsString());
            }
            if (json.has("enableHUD")) {
                config.enableHUD = json.get("enableHUD").getAsBoolean();
            }
            if (json.has("enableScoreboard")) {
                config.enableScoreboard = json.get("enableScoreboard").getAsBoolean();
            }
            if (json.has("enableActionBar")) {
                config.enableActionBar = json.get("enableActionBar").getAsBoolean();
            }
            if (json.has("overrideVanillaHealth")) {
                config.overrideVanillaHealth = json.get("overrideVanillaHealth").getAsBoolean();
            }
            if (json.has("overrideVanillaDamage")) {
                config.overrideVanillaDamage = json.get("overrideVanillaDamage").getAsBoolean();
            }
            if (json.has("manaRegenRate")) {
                config.manaRegenRate = json.get("manaRegenRate").getAsInt();
            }
            
            return config;
            
        } catch (IOException e) {
            LOGGER.error("Failed to load config, using defaults", e);
            return new CoreConfig();
        }
    }
    
    public void save(File file) {
        file.getParentFile().mkdirs();
        
        try (FileWriter writer = new FileWriter(file)) {
            JsonObject json = new JsonObject();
            json.addProperty("dataDirectory", dataDirectory.getPath());
            json.addProperty("enableHUD", enableHUD);
            json.addProperty("enableScoreboard", enableScoreboard);
            json.addProperty("enableActionBar", enableActionBar);
            json.addProperty("overrideVanillaHealth", overrideVanillaHealth);
            json.addProperty("overrideVanillaDamage", overrideVanillaDamage);
            json.addProperty("manaRegenRate", manaRegenRate);
            
            GSON.toJson(json, writer);
            
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }
    
    public File getDataDirectory() {
        return dataDirectory;
    }
    
    public boolean isEnableHUD() {
        return enableHUD;
    }
    
    public boolean isEnableScoreboard() {
        return enableScoreboard;
    }
    
    public boolean isEnableActionBar() {
        return enableActionBar;
    }
    
    public boolean isOverrideVanillaHealth() {
        return overrideVanillaHealth;
    }
    
    public boolean isOverrideVanillaDamage() {
        return overrideVanillaDamage;
    }
    
    public int getManaRegenRate() {
        return manaRegenRate;
    }
}
