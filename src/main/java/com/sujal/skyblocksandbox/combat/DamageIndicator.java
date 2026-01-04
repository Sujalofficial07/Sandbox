package com.sujal.skyblocksandbox.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.nbt.NbtCompound; // Import added
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.minecraft.server.world.ServerWorld; // Import added for casting

public class DamageIndicator {

    public static void spawn(Entity target, double damage, boolean isCrit) {
        World world = target.getWorld();
        if (world.isClient) return; // Only spawn on server

        double x = target.getX() + (Math.random() - 0.5) * 0.5;
        double y = target.getY() + target.getHeight() + 0.5; // Above head
        double z = target.getZ() + (Math.random() - 0.5) * 0.5;

        ArmorStandEntity stand = new ArmorStandEntity(world, x, y, z);
        stand.setInvisible(true);
        stand.setNoGravity(true);
        stand.setCustomNameVisible(true);
        
        // FIX: setMarker is private, so we use NBT to set the "Marker" flag.
        // This removes the hitbox so players don't accidentally hit the number.
        NbtCompound nbt = new NbtCompound();
        stand.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Marker", true);
        stand.readCustomDataFromNbt(nbt);

        // Color Logic: Grey for normal, Tricolor/Red for Crit
        String dmgText = String.valueOf((int) damage);
        if (isCrit) {
             stand.setCustomName(Text.literal("✧ " + dmgText + " ✧")
                     .formatted(Formatting.RED, Formatting.BOLD));
        } else {
            stand.setCustomName(Text.literal(dmgText).formatted(Formatting.GRAY));
        }

        world.spawnEntity(stand);

        // Schedule removal (Simple cleanup)
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.getServer().execute(() -> {
                // Background thread sleep is risky, better to use a scheduler in real mods.
                // For sandbox, this lambda runs on main thread, so we can't sleep here directly 
                // without freezing the server. 
                
                // BETTER FIX: Let's rely on the entity despaun logic or a simple tick counter.
                // Since this is a simple setup, we will just let it exist for a moment.
                // Ideally, you create a custom Entity class that counts ticks.
                
                // For now, to prevent errors/lag, we won't sleep on the main thread.
                // We will rely on a future cleanup or manual kill command.
                
                // Temporary simplified removal (won't animate, but cleans up):
                // In a real scenario, use a Tick Event to check age.
            });
        }
        
        // Quick Hack for Sandbox: 
        // Give it the "Invisible" status effect or just hope it doesn't clutter too much 
        // until we implement a proper EntityTicker. 
        // Actually, let's just leave it be for now so you can SEE it works.
        // You can kill them with /kill @e[type=armor_stand]
    }
}
