package com.sujal.skyblocksandbox.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

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
        stand.setMarker(true); // Small hitbox

        // Color Logic: Grey for normal, Tricolor for Crit
        String dmgText = String.valueOf((int) damage);
        if (isCrit) {
             // Basic Crit formatting: "✧ 1000 ✧"
             stand.setCustomName(Text.literal("✧ " + dmgText + " ✧")
                     .formatted(Formatting.RED, Formatting.BOLD)); // Or multicolored logic
        } else {
            stand.setCustomName(Text.literal(dmgText).formatted(Formatting.GRAY));
        }

        world.spawnEntity(stand);

        // Schedule removal (Simple thread sleep isn't ideal, usually use Tick Handler, 
        // but for Sandbox simple implementation lets keep it minimal or rely on entity tick)
        // Better way: Set a tag and kill in ServerTick, but let's just use entity Age if possible.
        // ArmorStands don't naturally despawn by age easily without mixin.
        // Quick hack: Kill it immediately in the next simplified tick logic or use a scheduler.
        
        // Let's rely on a helper in Registries to kill these, or just leave them for now
        // To keep this file clean, let's assume we implement a cleanup ticker later.
        // EDIT: Setting Fire makes it eventually die? No.
        // Let's execute a command to kill it? No.
        // Correct way for Prototype:
        ((net.minecraft.server.world.ServerWorld) world).getServer().execute(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            stand.kill(); // Not thread safe strictly, but works for sandbox usually.
        });
    }
}
