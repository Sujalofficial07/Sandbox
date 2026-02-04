package com.sujal.skyblock.core.internal.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProfileStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileStorage.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    private final File profilesDirectory;
    
    public ProfileStorage(File dataDirectory) {
        this.profilesDirectory = new File(dataDirectory, "profiles");
        if (!profilesDirectory.exists()) {
            profilesDirectory.mkdirs();
        }
    }
    
    public boolean loadProfile(ProfileImpl profile) {
        File profileFile = new File(profilesDirectory, profile.getUUID().toString() + ".json");
        
        if (!profileFile.exists()) {
            return true;
        }
        
        try (FileReader reader = new FileReader(profileFile)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            
            if (json.has("profileName")) {
                profile.setProfileName(json.get("profileName").getAsString());
            }
            if (json.has("coins")) {
                profile.setCoins(json.get("coins").getAsLong());
            }
            if (json.has("bankCoins")) {
                profile.setBankCoins(json.get("bankCoins").getAsLong());
            }
            if (json.has("skyBlockLevel")) {
                profile.setSkyBlockLevel(json.get("skyBlockLevel").getAsInt());
            }
            if (json.has("skyBlockXP")) {
                profile.setSkyBlockXP(json.get("skyBlockXP").getAsLong());
            }
            if (json.has("firstJoinTime")) {
                profile.setFirstJoinTime(json.get("firstJoinTime").getAsLong());
            }
            if (json.has("lastSeenTime")) {
                profile.setLastSeenTime(json.get("lastSeenTime").getAsLong());
            }
            if (json.has("moduleData")) {
                profile.getData().deserialize(json.getAsJsonObject("moduleData"));
            }
            
            profile.setFirstJoin(false);
            return false;
            
        } catch (IOException e) {
            LOGGER.error("Failed to load profile for UUID: {}", profile.getUUID(), e);
            return true;
        }
    }
    
    public void saveProfile(ProfileImpl profile) {
        File profileFile = new File(profilesDirectory, profile.getUUID().toString() + ".json");
        
        try (FileWriter writer = new FileWriter(profileFile)) {
            JsonObject json = new JsonObject();
            
            json.addProperty("uuid", profile.getUUID().toString());
            json.addProperty("profileName", profile.getProfileName());
            json.addProperty("coins", profile.getCoins());
            json.addProperty("bankCoins", profile.getBankCoins());
            json.addProperty("skyBlockLevel", profile.getSkyBlockLevel());
            json.addProperty("skyBlockXP", profile.getSkyBlockXP());
            json.addProperty("firstJoinTime", profile.getFirstJoinTime());
            json.addProperty("lastSeenTime", profile.getLastSeenTime());
            json.add("moduleData", profile.getData().serialize());
            
            GSON.toJson(json, writer);
            
        } catch (IOException e) {
            LOGGER.error("Failed to save profile for UUID: {}", profile.getUUID(), e);
        }
    }
}
