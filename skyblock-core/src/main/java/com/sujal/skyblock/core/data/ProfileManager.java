package com.sujal.skyblock.core.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement; // <--- ADDED THIS IMPORT
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager extends PersistentState {
    private static final String DATA_NAME = "skyblock_profiles";
    private final Map<UUID, SkyblockProfile> profiles = new HashMap<>();

    // Required for reading from NBT
    public static ProfileManager fromNbt(NbtCompound tag) {
        ProfileManager manager = new ProfileManager();
        // NbtElement is now recognized
        NbtList list = tag.getList("Profiles", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < list.size(); i++) {
            SkyblockProfile profile = SkyblockProfile.fromNbt(list.getCompound(i));
            manager.profiles.put(profile.getUuid(), profile);
        }
        return manager;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        NbtList list = new NbtList();
        for (SkyblockProfile profile : profiles.values()) {
            list.add(profile.writeNbt(new NbtCompound()));
        }
        tag.put("Profiles", list);
        return tag;
    }

    public static ProfileManager getServerInstance(ServerWorld world) {
        return world.getServer().getOverworld().getPersistentStateManager()
                .getOrCreate(ProfileManager::fromNbt, ProfileManager::new, DATA_NAME);
    }

    public SkyblockProfile getProfile(PlayerEntity player) {
        return profiles.computeIfAbsent(player.getUuid(), uuid -> {
            SkyblockProfile newProfile = new SkyblockProfile(uuid);
            this.markDirty();
            return newProfile;
        });
    }
}
