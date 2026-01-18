package com.sujal.skyblockstats.registry;

import com.sujal.skyblockstats.components.PlayerSkyBlockComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public class ComponentRegistry implements EntityComponentInitializer {

    // Component Key definition
    public static final ComponentKey<PlayerSkyBlockComponent> SKYBLOCK_STATS = 
        ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("skyblockstats", "stats"), PlayerSkyBlockComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        // Attach component to Players
        registry.registerForPlayers(SKYBLOCK_STATS, PlayerSkyBlockComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
    
    public static void init() {
        // Static init if needed
    }
}
