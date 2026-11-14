package com.hellgauntlet.boss.client;

import com.hellgauntlet.boss.ModEntities;
import com.hellgauntlet.boss.TheCorruptedMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import com.hellgauntlet.boss.ModItems;
import com.hellgauntlet.boss.ModParticles;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

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

    @SubscribeEvent
    public static void onClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(KnightArmorClientExtensions.INSTANCE,
                ModItems.STEEL_HELMET.get(), ModItems.STEEL_CHESTPLATE.get(), ModItems.STEEL_LEGGINGS.get(), ModItems.STEEL_BOOTS.get(),
                ModItems.CORRUPTED_HELMET.get(), ModItems.CORRUPTED_CHESTPLATE.get(), ModItems.CORRUPTED_LEGGINGS.get(), ModItems.CORRUPTED_BOOTS.get());
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        for (var skin : event.getSkins()) {
            Object renderer = event.getSkin(skin);
            if (renderer instanceof PlayerRenderer player) {
                player.addLayer(new CorruptedGlowLayer(player));
            }
        }
    }
}
