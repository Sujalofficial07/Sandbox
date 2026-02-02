package com.sujal.skyblock.skills;

import com.sujal.skyblock.skills.ui.SkillMenu;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockSkillsMod implements ModInitializer {
    public static final String MOD_ID = "skyblock-skills";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Skyblock Skills System Initializing...");
        
        // Command to open Skills Menu
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            dispatcher.register(CommandManager.literal("skills")
                .executes(ctx -> {
                    SkillMenu.openMainMenu(ctx.getSource().getPlayerOrThrow());
                    return 1;
                }));
        });
    }
}
