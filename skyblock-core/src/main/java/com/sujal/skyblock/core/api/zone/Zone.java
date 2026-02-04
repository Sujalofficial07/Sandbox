package com.sujal.skyblock.core.api.zone;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class Zone {
    private final String id;
    private final ZoneType type;
    private final String displayName;
    private final Box bounds;
    private final String worldKey;
    private final int requiredCombatLevel;
    private final int requiredMiningLevel;
    private final int requiredFarmingLevel;
    private final int requiredForagingLevel;
    private final boolean pvpEnabled;
    private final boolean buildingAllowed;
    
    private Zone(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.displayName = builder.displayName;
        this.bounds = builder.bounds;
        this.worldKey = builder.worldKey;
        this.requiredCombatLevel = builder.requiredCombatLevel;
        this.requiredMiningLevel = builder.requiredMiningLevel;
        this.requiredFarmingLevel = builder.requiredFarmingLevel;
        this.requiredForagingLevel = builder.requiredForagingLevel;
        this.pvpEnabled = builder.pvpEnabled;
        this.buildingAllowed = builder.buildingAllowed;
    }
    
    public String getId() {
        return id;
    }
    
    public ZoneType getType() {
        return type;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public Box getBounds() {
        return bounds;
    }
    
    public String getWorldKey() {
        return worldKey;
    }
    
    public boolean contains(BlockPos pos) {
        return bounds.contains(pos.getX(), pos.getY(), pos.getZ());
    }
    
    public int getRequiredCombatLevel() {
        return requiredCombatLevel;
    }
    
    public int getRequiredMiningLevel() {
        return requiredMiningLevel;
    }
    
    public int getRequiredFarmingLevel() {
        return requiredFarmingLevel;
    }
    
    public int getRequiredForagingLevel() {
        return requiredForagingLevel;
    }
    
    public boolean isPvpEnabled() {
        return pvpEnabled;
    }
    
    public boolean isBuildingAllowed() {
        return buildingAllowed;
    }
    
    public static Builder builder(String id) {
        return new Builder(id);
    }
    
    public static class Builder {
        private final String id;
        private ZoneType type = ZoneType.HUB;
        private String displayName;
        private Box bounds;
        private String worldKey = "minecraft:overworld";
        private int requiredCombatLevel = 0;
        private int requiredMiningLevel = 0;
        private int requiredFarmingLevel = 0;
        private int requiredForagingLevel = 0;
        private boolean pvpEnabled = false;
        private boolean buildingAllowed = false;
        
        private Builder(String id) {
            this.id = id;
            this.displayName = id;
        }
        
        public Builder type(ZoneType type) {
            this.type = type;
            return this;
        }
        
        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }
        
        public Builder bounds(Box bounds) {
            this.bounds = bounds;
            return this;
        }
        
        public Builder bounds(BlockPos min, BlockPos max) {
            this.bounds = new Box(min, max);
            return this;
        }
        
        public Builder worldKey(String worldKey) {
            this.worldKey = worldKey;
            return this;
        }
        
        public Builder requiredCombatLevel(int level) {
            this.requiredCombatLevel = level;
            return this;
        }
        
        public Builder requiredMiningLevel(int level) {
            this.requiredMiningLevel = level;
            return this;
        }
        
        public Builder requiredFarmingLevel(int level) {
            this.requiredFarmingLevel = level;
            return this;
        }
        
        public Builder requiredForagingLevel(int level) {
            this.requiredForagingLevel = level;
            return this;
        }
        
        public Builder pvpEnabled(boolean enabled) {
            this.pvpEnabled = enabled;
            return this;
        }
        
        public Builder buildingAllowed(boolean allowed) {
            this.buildingAllowed = allowed;
            return this;
        }
        
        public Zone build() {
            if (bounds == null) {
                throw new IllegalStateException("Zone bounds must be set");
            }
            return new Zone(this);
        }
    }
}
