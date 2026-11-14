package com.hellgauntlet.boss.client;

import com.hellgauntlet.boss.ModEntities;
import com.hellgauntlet.boss.TheCorruptedMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import com.hellgauntlet.boss.ModParticles;

@EventBusSubscriber(modid = TheCorruptedMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetup {
    private ClientSetup() {}

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.THE_CORRUPTED.get(), TheCorruptedRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.CORRUPTION_BOLT.get(), CorruptionBoltParticle.Provider::new);
    }
}
