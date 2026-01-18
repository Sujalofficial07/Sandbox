package com.sujal.skyblockstats.components;

import com.sujal.skyblockstats.stats.SkyBlockProfile;
import com.sujal.skyblockstats.stats.StatContainer;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerSkyBlockComponent implements Component {
    private final PlayerEntity player;
    private final SkyBlockProfile profile;

    public PlayerSkyBlockComponent(PlayerEntity player) {
        this.player = player;
        this.profile = new SkyBlockProfile();
    }

    public SkyBlockProfile getProfile() {
        return profile;
    }
    
    public StatContainer getStats() {
        return profile.getStatContainer();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        profile.readFromNbt(tag);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        profile.writeToNbt(tag);
    }
}
