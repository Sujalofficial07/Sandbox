package com.sujal.skyblock.items.weapons;

import com.sujal.skyblock.core.api.ItemType;
import com.sujal.skyblock.core.api.Rarity;
import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.items.api.Ability;
import com.sujal.skyblock.items.api.SBItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class Hyperion extends SBItem {

    @Override
    public String getId() { return "HYPERION"; }

    @Override
    public String getDisplayName() { return "Hyperion"; }

    @Override
    public Item getMaterial() { return Items.IRON_SWORD; }

    @Override
    public Rarity getRarity() { return Rarity.LEGENDARY; }

    @Override
    public ItemType getType() { return ItemType.SWORD; }

    @Override
    public Map<StatType, Double> getStats() {
        Map<StatType, Double> stats = new HashMap<>();
        stats.put(StatType.STRENGTH, 150.0); // Used as Damage
        stats.put(StatType.STRENGTH, 30.0);  // Actual Strength bonus
        stats.put(StatType.CRIT_DAMAGE, 50.0);
        stats.put(StatType.INTELLIGENCE, 400.0);
        return stats;
    }

    @Override
    public Ability getAbility() {
        return new Ability("Wither Impact", "Teleport 10 blocks ahead and deal massive damage.", 250, 0) {
            @Override
            public void onActivate(PlayerEntity player, World world) {
                // Teleport Logic
                Vec3d look = player.getRotationVector();
                double range = 10.0;
                Vec3d dest = player.getPos().add(look.x * range, look.y * range, look.z * range);
                
                player.teleport(dest.x, dest.y, dest.z);
                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                
                // Damage Logic (Area of Effect) would go here
                // List<Entity> targets = world.getEntities...
            }
        };
    }
}
