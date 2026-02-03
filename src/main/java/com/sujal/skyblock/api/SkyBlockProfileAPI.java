package com.sujal.skyblock.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

/**
 * Core API for Player Profile management.
 */
public interface SkyBlockProfileAPI {
    boolean unlockLocation(PlayerEntity player, Identifier locationId);
    boolean hasUnlocked(PlayerEntity player, Identifier locationId);
    void setCoinDisplay(PlayerEntity player, double amount); // Visual only
    Identifier getPrivateIslandDimension(PlayerEntity player);
}
