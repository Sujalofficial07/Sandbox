package com.sujal.skyblockstats.registry;

import com.sujal.skyblockstats.components.PlayerSkyBlockComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistrationInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistrationContext;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public class ComponentRegistry implements EntityComponentInitializer, ComponentRegistrationInitializer {

    // Component Key Definition
    public static final ComponentKey<PlayerSkyBlockComponent> SKYBLOCK_STATS = 
        ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("skyblockstats", "stats"), PlayerSkyBlockComponent.class);

    // 1. Register the Component itself (Required for CCA 5+)
    @Override
    public void registerComponents(ComponentRegistrationContext context) {
        // Explicitly registering is strictly required in newer CCA versions if getOrCreate is called statically
        // But simply having this method and the entrypoint allows CCA to discover the static Key.
    }

    // 2. Attach the Component to Players
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(SKYBLOCK_STATS, PlayerSkyBlockComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
    
    public static void init() {
        // Static init to ensure class loading
    }
}
