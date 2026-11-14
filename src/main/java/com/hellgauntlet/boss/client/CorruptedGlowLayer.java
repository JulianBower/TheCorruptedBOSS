package com.hellgauntlet.boss.client;

import com.hellgauntlet.boss.KnightArmorItem;
import com.hellgauntlet.boss.TheCorruptedMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

/** Emissive pass (full bright) for worn Corrupted Knight pieces: glowing veins, T-visor and cape tips. */
public class CorruptedGlowLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation GLOW =
            ResourceLocation.fromNamespaceAndPath(TheCorruptedMod.MODID, "textures/entity/the_corrupted_glow.png");
    private static final EquipmentSlot[] SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public CorruptedGlowLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void render(PoseStack poseStack, MultiBufferSource buffer, int light, AbstractClientPlayer player,
                       float limbSwing, float limbSwingAmount, float partialTick, float age, float netHeadYaw, float headPitch) {
        if (player.isInvisible()) {
            return;
        }
        for (EquipmentSlot slot : SLOTS) {
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.getItem() instanceof KnightArmorItem armor && armor.isCorrupted() && armor.getEquipmentSlot() == slot) {
                CorruptedPlayerArmorModel model = KnightArmorClientExtensions.model(slot);
                model.entity = player;
                ((HumanoidModel) this.getParentModel()).copyPropertiesTo(model);
                model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentEmissive(GLOW)),
                        LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, -1);
            }
        }
    }
}
