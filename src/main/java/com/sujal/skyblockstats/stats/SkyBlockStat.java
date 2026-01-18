package com.sujal.skyblockstats.stats;

import net.minecraft.nbt.NbtCompound;

public class SkyBlockStat {
    private final SkyBlockStatType type;
    private double baseValue;
    private double bonusValue;

    public SkyBlockStat(SkyBlockStatType type) {
        this.type = type;
        this.baseValue = type.getBaseDefault();
        this.bonusValue = 0.0;
    }

    public double getValue() {
        return baseValue + bonusValue;
    }

    public void setBaseValue(double val) {
        this.baseValue = val;
    }

    public void addBonus(double amount) {
        this.bonusValue += amount;
    }
    
    public void removeBonus(double amount) {
        this.bonusValue -= amount;
    }

    public void resetBonus() {
        this.bonusValue = 0.0;
    }

    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("base", baseValue);
        tag.putDouble("bonus", bonusValue);
    }

    public void readFromNbt(NbtCompound tag) {
        this.baseValue = tag.getDouble("base");
        this.bonusValue = tag.getDouble("bonus");
    }

    public SkyBlockStatType getType() {
        return type;
    }
}
