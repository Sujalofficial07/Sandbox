package com.sujal.skyblockstats.stats;

import net.minecraft.nbt.NbtCompound;
import java.util.UUID;

public class SkyBlockProfile {
    private final UUID profileId;
    private final StatContainer statContainer;

    public SkyBlockProfile() {
        this.profileId = UUID.randomUUID();
        this.statContainer = new StatContainer();
    }

    public StatContainer getStatContainer() {
        return statContainer;
    }

    public void writeToNbt(NbtCompound tag) {
        tag.putUuid("ProfileId", profileId);
        NbtCompound statsTag = new NbtCompound();
        statContainer.writeToNbt(statsTag);
        tag.put("Stats", statsTag);
    }

    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("Stats")) {
            statContainer.readFromNbt(tag.getCompound("Stats"));
        }
    }
}
