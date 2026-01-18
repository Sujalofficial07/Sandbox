package com.sujal.skyblockstats;

import com.sujal.skyblockstats.registry.ComponentRegistry;
import com.sujal.skyblockstats.registry.DamageRegistry;
import com.sujal.skyblockstats.registry.StatRegistry;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockStatsMod implements ModInitializer {
	public static final String MOD_ID = "skyblockstats";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing SkyBlock Stats Engine...");
		
		StatRegistry.init();
		ComponentRegistry.init();
		DamageRegistry.init();
		
		LOGGER.info("SkyBlock Stats Engine loaded successfully!");
	}
}
