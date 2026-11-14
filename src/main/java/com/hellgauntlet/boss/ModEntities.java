package com.hellgauntlet.boss;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, TheCorruptedMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<TheCorruptedBossEntity>> THE_CORRUPTED =
            ENTITY_TYPES.register("the_corrupted", () -> EntityType.Builder
                    .of(TheCorruptedBossEntity::new, MobCategory.MONSTER)
                    .sized(1.1F, 3.4F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .build("the_corrupted"));

    private ModEntities() {}
}
