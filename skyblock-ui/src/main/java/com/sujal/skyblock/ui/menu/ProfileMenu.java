package com.sujal.skyblock.ui.menu;

import com.sujal.skyblock.core.api.StatType;
import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.stats.engine.StatCalculator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ProfileMenu {

    public static void open(ServerPlayerEntity player) {
        SimpleInventory inv = new SimpleInventory(54);
        
        ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) player.getWorld());
        SkyblockProfile profile = pm.getProfile(player);

        // Slot 13: SkyBlock Profile (Head)
        inv.setStack(13, createProfileHead(player, profile));

        // Slot 49: Close
        ItemStack close = new ItemStack(Items.BARRIER);
        close.setCustomName(Text.literal("§cClose"));
        inv.setStack(49, close);

        // Fill background glass
        ItemStack glass = new ItemStack(Items.BLACK_STAINED_GLASS_PANE);
        glass.setCustomName(Text.empty());
        for(int i=0; i<54; i++) {
            if(inv.getStack(i).isEmpty()) inv.setStack(i, glass);
        }

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInv, p) -> GenericContainerScreenHandler.createGeneric9x6(syncId, playerInv, inv),
            Text.literal("SkyBlock Menu")
        ));
    }

    private static ItemStack createProfileHead(PlayerEntity player, SkyblockProfile profile) {
        ItemStack head = new ItemStack(Items.PLAYER_HEAD);
        NbtCompound tag = head.getOrCreateNbt();
        tag.putString("SkullOwner", player.getName().getString());
        
        head.setCustomName(Text.literal("§aYour SkyBlock Profile"));
        
        NbtList lore = new NbtList();
        lore.add(NbtString.of("§7View your equipment, stats,"));
        lore.add(NbtString.of("§7and collections!"));
        lore.add(NbtString.of(""));
        
        // Add Stats dynamically
        addStatLore(lore, "Health", StatType.HEALTH, player, profile, Formatting.RED);
        addStatLore(lore, "Defense", StatType.DEFENSE, player, profile, Formatting.GREEN);
        addStatLore(lore, "Strength", StatType.STRENGTH, player, profile, Formatting.RED);
        addStatLore(lore, "Speed", StatType.SPEED, player, profile, Formatting.WHITE);
        addStatLore(lore, "Crit Chance", StatType.CRIT_CHANCE, player, profile, Formatting.BLUE);
        addStatLore(lore, "Crit Damage", StatType.CRIT_DAMAGE, player, profile, Formatting.BLUE);
        addStatLore(lore, "Intelligence", StatType.INTELLIGENCE, player, profile, Formatting.AQUA);

        lore.add(NbtString.of(""));
        lore.add(NbtString.of("§eClick to view details!"));
        
        tag.getCompound("display").put("Lore", lore);
        return head;
    }

    private static void addStatLore(NbtList lore, String name, StatType type, PlayerEntity player, SkyblockProfile profile, Formatting color) {
        double val = StatCalculator.getFinalStat(player, profile, type);
        lore.add(NbtString.of("§7" + name + ": " + color + (int)val));
    }
}
