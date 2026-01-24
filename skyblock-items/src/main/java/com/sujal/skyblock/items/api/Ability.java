package com.sujal.skyblock.items.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public abstract class Ability {
    private final String name;
    private final String description;
    private final int manaCost;
    private final int cooldownSeconds;

    public Ability(String name, String description, int manaCost, int cooldownSeconds) {
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.cooldownSeconds = cooldownSeconds;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getManaCost() { return manaCost; }
    public int getCooldown() { return cooldownSeconds; }

    // This method will be called when right-click happens
    public abstract void onActivate(PlayerEntity player, World world);
}
