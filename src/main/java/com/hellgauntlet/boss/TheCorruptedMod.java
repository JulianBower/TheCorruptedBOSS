package com.hellgauntlet.boss;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

@Mod(TheCorruptedMod.MODID)
public class TheCorruptedMod {
    public static final String MODID = "hellgauntletboss";

    public TheCorruptedMod(IEventBus modBus) {
        ModEffects.EFFECTS.register(modBus);
        ModEntities.ENTITY_TYPES.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModCreativeTabs.TABS.register(modBus);
        ModParticles.PARTICLES.register(modBus);
        ModArmorMaterials.ARMOR_MATERIALS.register(modBus);

        modBus.addListener(TheCorruptedMod::onAttributes);

        NeoForge.EVENT_BUS.addListener(CorruptedBlasts::onLevelTick);
        NeoForge.EVENT_BUS.addListener((ServerStoppedEvent event) -> CorruptedBlasts.clear());
    }

    private static void onAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.THE_CORRUPTED.get(), TheCorruptedBossEntity.createAttributes().build());
    }
}
