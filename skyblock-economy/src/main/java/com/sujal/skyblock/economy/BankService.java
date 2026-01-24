package com.sujal.skyblock.economy;

import com.sujal.skyblock.core.data.ProfileManager;
import com.sujal.skyblock.core.data.SkyblockProfile;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class BankService {

    public static boolean deposit(ServerPlayerEntity player, double amount) {
        ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) player.getWorld());
        SkyblockProfile profile = pm.getProfile(player);

        if (profile.getCoins() >= amount) {
            profile.addCoins(-amount); // Remove from Purse
            profile.addToBank(amount); // Add to Bank
            pm.markDirty();
            
            player.sendMessage(Text.of("§aDeposited §6" + (long)amount + " coins§a!"), true);
            return true;
        }
        player.sendMessage(Text.of("§cYou don't have enough coins!"), false);
        return false;
    }

    public static boolean withdraw(ServerPlayerEntity player, double amount) {
        ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) player.getWorld());
        SkyblockProfile profile = pm.getProfile(player);

        if (profile.getBankBalance() >= amount) {
            profile.addToBank(-amount); // Remove from Bank
            profile.addCoins(amount);   // Add to Purse
            pm.markDirty();
            
            player.sendMessage(Text.of("§aWithdrew §6" + (long)amount + " coins§a!"), true);
            return true;
        }
        player.sendMessage(Text.of("§cYou don't have enough coins in the bank!"), false);
        return false;
    }
    
    // Interest System (1% every 31 hours)
    public static void checkInterest(ServerPlayerEntity player) {
        ProfileManager pm = ProfileManager.getServerInstance((ServerWorld) player.getWorld());
        SkyblockProfile profile = pm.getProfile(player);
        
        // Use logic here to check System.currentTimeMillis() vs profile.getLastInterestTime()
        // If diff > 31 hours -> add 1% -> update timestamp
    }
}
