package com.sujal.skyblockstats.stats;

import net.minecraft.nbt.NbtCompound;
import java.util.EnumMap;
import java.util.Map;

public class StatContainer {
    private final Map<SkyBlockStatType, SkyBlockStat> stats = new EnumMap<>(SkyBlockStatType.class);

    public StatContainer() {
        for (SkyBlockStatType type : SkyBlockStatType.values()) {
            stats.put(type, new SkyBlockStat(type));
        }
    }

    public SkyBlockStat getStat(SkyBlockStatType type) {
        return stats.get(type);
    }

    public double getStatValue(SkyBlockStatType type) {
        return stats.get(type).getValue();
    }

    public void writeToNbt(NbtCompound tag) {
        for (Map.Entry<SkyBlockStatType, SkyBlockStat> entry : stats.entrySet()) {
            NbtCompound statTag = new NbtCompound();
            entry.getValue().writeToNbt(statTag);
            tag.put(entry.getKey().name(), statTag);
        }
    }

    public void readFromNbt(NbtCompound tag) {
        for (SkyBlockStatType type : SkyBlockStatType.values()) {
            if (tag.contains(type.name())) {
                stats.get(type).readFromNbt(tag.getCompound(type.name()));
            }
        }
    }
}
