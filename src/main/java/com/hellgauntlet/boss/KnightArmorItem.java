package com.hellgauntlet.boss;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

/** One piece of the Steel Knight (tier 1) or Corrupted Knight (tier 2) armor. */
public class KnightArmorItem extends ArmorItem {
    private final boolean corrupted;

    public KnightArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties, boolean corrupted) {
        super(material, type, properties);
        this.corrupted = corrupted;
    }

    public boolean isCorrupted() {
        return this.corrupted;
    }
}
