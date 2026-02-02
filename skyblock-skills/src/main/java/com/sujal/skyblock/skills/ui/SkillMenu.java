package com.sujal.skyblock.skills.ui;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import com.sujal.skyblock.skills.api.SkillType;
import com.sujal.skyblock.skills.data.SkillExpTable;
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

import java.text.DecimalFormat;

public class SkillMenu {
    
    private static final DecimalFormat COMMA_FORMAT = new DecimalFormat("#,###");

    // --- MAIN SKILL MENU ---
    public static void openMainMenu(ServerPlayerEntity player) {
        SimpleInventory inv = new SimpleInventory(54);
        SkyblockProfile profile = getProfile(player);

        // Fill Background
        fillGlass(inv);

        // Row 2 (Slots 10-16) - Primary Skills
        setSkillIcon(inv, 10, SkillType.FARMING, profile);
        setSkillIcon(inv, 11, SkillType.MINING, profile);
        setSkillIcon(inv, 12, SkillType.COMBAT, profile);
        setSkillIcon(inv, 13, SkillType.FORAGING, profile);
        setSkillIcon(inv, 14, SkillType.FISHING, profile);
        setSkillIcon(inv, 15, SkillType.ENCHANTING, profile);
        setSkillIcon(inv, 16, SkillType.ALCHEMY, profile);

        // Row 3 (Slots 19-25) - Secondary Skills
        setSkillIcon(inv, 19, SkillType.TAMING, profile);
        setSkillIcon(inv, 20, SkillType.DUNGEONEERING, profile);
        setSkillIcon(inv, 21, SkillType.CARPENTRY, profile);
        setSkillIcon(inv, 22, SkillType.RUNECRAFTING, profile);
        setSkillIcon(inv, 23, SkillType.SOCIAL, profile);
        
        // Close Button
        ItemStack close = new ItemStack(Items.BARRIER);
        close.setCustomName(Text.literal("§cClose"));
        inv.setStack(49, close);

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInv, p) -> GenericContainerScreenHandler.createGeneric9x6(syncId, playerInv, inv),
            Text.literal("Your Skills")
        ));
    }

    // --- SPECIFIC SKILL DETAIL MENU ---
    public static void openSkillDetail(ServerPlayerEntity player, SkillType skill) {
        SimpleInventory inv = new SimpleInventory(54);
        SkyblockProfile profile = getProfile(player);
        
        double currentXp = profile.getSkillXp(skill.getName());
        int currentLevel = SkillExpTable.getLevel(currentXp);

        fillGlass(inv);

        // The "Path" Logic from the video
        // Slots: 18 -> 19 -> 20 -> 21 -> 22 -> 23 -> 24 -> 25 -> 26
        // Then loop back or simplified layout for now
        
        // We will show levels 1 to 25 mapping to slots
        // This is a simplified visual representation of the path
        int[] slots = {19, 20, 21, 22, 23, 24, 25}; 
        
        for (int i = 0; i < slots.length; i++) {
            int levelToShow = i + 1; // Level 1, 2, 3...
            boolean isUnlocked = currentLevel >= levelToShow;
            
            ItemStack item;
            String name;
            Formatting color;

            if (isUnlocked) {
                item = new ItemStack(Items.LIME_STAINED_GLASS_PANE);
                name = "§a" + skill.getName() + " Level " + toRoman(levelToShow);
                color = Formatting.GREEN;
            } else if (levelToShow == currentLevel + 1) {
                item = new ItemStack(Items.YELLOW_STAINED_GLASS_PANE);
                name = "§e" + skill.getName() + " Level " + toRoman(levelToShow);
                color = Formatting.YELLOW;
            } else {
                item = new ItemStack(Items.RED_STAINED_GLASS_PANE);
                name = "§c" + skill.getName() + " Level " + toRoman(levelToShow);
                color = Formatting.RED;
            }
            
            // Special Icons for Milestones (like video 5, 10, etc)
            if (levelToShow % 5 == 0) {
                 item = new ItemStack(isUnlocked ? Items.EMERALD_BLOCK : Items.REDSTONE_BLOCK);
            }

            item.setCustomName(Text.literal(name));
            setLore(item,
                "§7Rewards:",
                "§a" + skill.getPerkName() + " " + toRoman(levelToShow),
                " §8Grants Stat Bonuses",
                "",
                color + (isUnlocked ? "UNLOCKED" : "LOCKED")
            );
            
            inv.setStack(slots[i], item);
        }
        
        // Back Button
        ItemStack back = new ItemStack(Items.ARROW);
        back.setCustomName(Text.literal("§aGo Back"));
        setLore(back, "§7To Skills Menu");
        inv.setStack(48, back);

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInv, p) -> GenericContainerScreenHandler.createGeneric9x6(syncId, playerInv, inv),
            Text.literal(skill.getName() + " Skill")
        ));
    }

    private static void setSkillIcon(SimpleInventory inv, int slot, SkillType skill, SkyblockProfile profile) {
        double xp = profile.getSkillXp(skill.getName());
        int level = SkillExpTable.getLevel(xp);
        double progress = SkillExpTable.getProgress(xp, level);
        
        ItemStack stack = new ItemStack(skill.getIcon());
        stack.setCustomName(Text.literal("§a" + skill.getName() + " " + toRoman(level)));
        
        setLore(stack,
            "§7" + getSkillDescription(skill),
            "",
            "§7Progress to Level " + (level + 1) + ": §e" + String.format("%.1f", progress) + "%",
            "§7------------------ §e" + COMMA_FORMAT.format((int)xp) + " XP",
            "",
            "§eClick to view!"
        );
        
        inv.setStack(slot, stack);
    }
    
    private static String getSkillDescription(SkillType skill) {
        switch(skill) {
            case FARMING: return "Harvest crops and shear sheep";
            case MINING: return "Spelunk islands for ores";
            case COMBAT: return "Slay mobs and bosses";
            case FORAGING: return "Cut trees and forage plants";
            // Add others...
            default: return "Level up this skill!";
        }
    }

    private static void fillGlass(SimpleInventory inv) {
        ItemStack glass = new ItemStack(Items.GRAY_STAINED_GLASS_PANE);
        glass.setCustomName(Text.empty());
        for(int i=0; i<inv.size(); i++) {
            if(inv.getStack(i).isEmpty()) inv.setStack(i, glass);
        }
    }

    private static void setLore(ItemStack stack, String... lines) {
        NbtCompound display = stack.getOrCreateSubNbt("display");
        NbtList lore = new NbtList();
        for(String line : lines) lore.add(NbtString.of(line));
        display.put("Lore", lore);
    }
    
    private static String toRoman(int n) {
        return switch (n) {
            case 1 -> "I"; case 2 -> "II"; case 3 -> "III"; case 4 -> "IV"; case 5 -> "V";
            case 10 -> "X"; case 15 -> "XV"; case 20 -> "XX"; case 25 -> "XXV";
            default -> String.valueOf(n);
        };
    }

    private static SkyblockProfile getProfile(ServerPlayerEntity player) {
        return ProfileManager.getServerInstance((ServerWorld)player.getWorld()).getProfile(player);
    }
              }
