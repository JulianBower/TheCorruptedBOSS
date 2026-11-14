package com.hellgauntlet.boss;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TheCorruptedMod.MODID);

    public static final DeferredItem<DeferredSpawnEggItem> THE_CORRUPTED_SPAWN_EGG =
            ITEMS.register("the_corrupted_spawn_egg",
                    () -> new DeferredSpawnEggItem(ModEntities.THE_CORRUPTED, 0x1A0505, 0xD91E18, new Item.Properties()));

    private ModItems() {}
}
