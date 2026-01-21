package com.sujal.skyblock.core.api;

import net.minecraft.util.Formatting;

public enum Rarity {
    COMMON("COMMON", Formatting.WHITE),
    UNCOMMON("UNCOMMON", Formatting.GREEN),
    RARE("RARE", Formatting.BLUE),
    EPIC("EPIC", Formatting.DARK_PURPLE),
    LEGENDARY("LEGENDARY", Formatting.GOLD),
    MYTHIC("MYTHIC", Formatting.LIGHT_PURPLE),
    DIVINE("DIVINE", Formatting.AQUA),
    SPECIAL("SPECIAL", Formatting.RED),
    VERY_SPECIAL("VERY SPECIAL", Formatting.RED);

    private final String name;
    private final Formatting color;

    Rarity(String name, Formatting color) {
        this.name = name;
        this.color = color;
    }

    public String getName() { return name; }
    public Formatting getColor() { return color; }
    
    public static Rarity getNext(Rarity current) {
        int nextOrd = current.ordinal() + 1;
        if (nextOrd >= values().length) return current;
        return values()[nextOrd];
    }
}
