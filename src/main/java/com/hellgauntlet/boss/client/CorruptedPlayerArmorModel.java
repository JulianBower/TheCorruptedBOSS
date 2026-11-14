package com.hellgauntlet.boss.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wearable armor in the silhouette of The Corrupted: T-visor knight helmet, layered pauldrons, plated arms and legs,
 * and a tattered cape on the chestplate. The geometry is the boss model baked to player proportions (ArmorMesh);
 * one instance exists per armor slot and draws only that slot's quads, following the vanilla humanoid part poses.
 */
public class CorruptedPlayerArmorModel extends HumanoidModel<LivingEntity> {
    private static final int STRIDE = 23;
    private final EquipmentSlot slot;
    /** Entity being rendered, set by the item extension right before rendering (drives the cape). */
    public LivingEntity entity;

    public CorruptedPlayerArmorModel(EquipmentSlot slot) {
        super(emptyRoot());
        this.slot = slot;
    }

    private static ModelPart emptyRoot() {
        Map<String, ModelPart> children = new HashMap<>();
        for (String name : List.of("head", "hat", "body", "right_arm", "left_arm", "right_leg", "left_leg")) {
            children.put(name, new ModelPart(List.of(), new HashMap<>()));
        }
        return new ModelPart(List.of(), children);
    }

    @Override
    public void renderToBuffer(PoseStack ps, VertexConsumer vc, int light, int overlay, int color) {
        switch (this.slot) {
            case HEAD -> {
                ps.pushPose();
                this.head.translateAndRotate(ps);
                box(ps, vc, -4.5F, -8.5F, -4.5F, 4.5F, 0.5F, 4.5F, ArmorMesh.UV_HEAD, light, overlay, color);
                draw(ArmorMesh.HEAD.D, ps, vc, light, overlay, color);
                ps.popPose();
            }
            case CHEST -> {
                ps.pushPose();
                this.body.translateAndRotate(ps);
                box(ps, vc, -4.5F, -0.5F, -2.5F, 4.5F, 7.0F, 2.5F, ArmorMesh.UV_BODY, light, overlay, color);
                draw(ArmorMesh.CHEST.D, ps, vc, light, overlay, color);
                cape(ps, vc, light, overlay, color);
                ps.popPose();
                sleeve(ps, this.rightArm, -3.6F, 1.6F, ArmorMesh.R_ARM.D, vc, light, overlay, color);
                sleeve(ps, this.leftArm, -1.6F, 3.6F, ArmorMesh.L_ARM.D, vc, light, overlay, color);
            }
            case LEGS -> {
                ps.pushPose();
                this.body.translateAndRotate(ps);
                box(ps, vc, -4.7F, 6.6F, -2.7F, 4.7F, 12.5F, 2.7F, ArmorMesh.UV_BODY, light, overlay, color);
                draw(ArmorMesh.BODY_LOW.D, ps, vc, light, overlay, color);
                ps.popPose();
                legPiece(ps, this.rightLeg, -0.5F, 7.0F, ArmorMesh.R_LEG_UP.D, vc, light, overlay, color);
                legPiece(ps, this.leftLeg, -0.5F, 7.0F, ArmorMesh.L_LEG_UP.D, vc, light, overlay, color);
            }
            case FEET -> {
                legPiece(ps, this.rightLeg, 7.0F, 12.7F, ArmorMesh.R_LEG_LOW.D, vc, light, overlay, color);
                legPiece(ps, this.leftLeg, 7.0F, 12.7F, ArmorMesh.L_LEG_LOW.D, vc, light, overlay, color);
            }
            default -> {
            }
        }
    }

    /** Arm armor: solid sleeve slightly larger than the vanilla arm, then the boss plates on top. */
    private static void sleeve(PoseStack ps, ModelPart arm, float x0, float x1, float[] quads, VertexConsumer vc, int light, int overlay, int color) {
        ps.pushPose();
        arm.translateAndRotate(ps);
        box(ps, vc, x0, -2.6F, -2.6F, x1, 10.6F, 2.6F, ArmorMesh.UV_ARM, light, overlay, color);
        draw(quads, ps, vc, light, overlay, color);
        ps.popPose();
    }

    /** Leg armor between y0 and y1 (part space): solid shell larger than the vanilla leg, then the boss plates. */
    private static void legPiece(PoseStack ps, ModelPart leg, float y0, float y1, float[] quads, VertexConsumer vc, int light, int overlay, int color) {
        ps.pushPose();
        leg.translateAndRotate(ps);
        box(ps, vc, -2.7F, y0, -2.7F, 2.7F, y1, 2.7F, ArmorMesh.UV_LEG, light, overlay, color);
        draw(quads, ps, vc, light, overlay, color);
        ps.popPose();
    }

    private static void part(PoseStack ps, ModelPart part, float[] quads, VertexConsumer vc, int light, int overlay, int color) {
        ps.pushPose();
        part.translateAndRotate(ps);
        draw(quads, ps, vc, light, overlay, color);
        ps.popPose();
    }

    /** Flowing cape hanging from the shoulders, swinging out when moving and sneaking. */
    private void cape(PoseStack ps, VertexConsumer vc, int light, int overlay, int color) {
        float angle = 0.1F;
        if (this.entity != null) {
            angle += Mth.clamp(this.entity.walkAnimation.speed(), 0.0F, 1.0F) * 0.5F
                    + Mth.sin(this.entity.tickCount * 0.1F) * 0.03F + (this.entity.isCrouching() ? 0.3F : 0.0F);
        }
        ps.pushPose();
        ps.translate(0.0F, 1.0F / 16.0F, 2.4F / 16.0F);
        ps.mulPose(com.mojang.math.Axis.XP.rotation(angle));
        float[] uv = ArmorMesh.CAPE_UV;
        // two layers (outside / inside) so it is visible from both sides
        box(ps, vc, -4.4F, 0.0F, 0.0F, 4.4F, 16.0F, 0.7F, uv, light, overlay, color);
        ps.popPose();
    }

    private static void box(PoseStack ps, VertexConsumer vc, float x0, float y0, float z0, float x1, float y1, float z1,
                            float[] uv, int light, int overlay, int color) {
        PoseStack.Pose pose = ps.last();
        float[][] faces = {
                {x0, y0, z0, x1, y0, z0, x1, y1, z0, x0, y1, z0, 0, 0, -1},
                {x1, y0, z1, x0, y0, z1, x0, y1, z1, x1, y1, z1, 0, 0, 1},
                {x0, y0, z1, x0, y0, z0, x0, y1, z0, x0, y1, z1, -1, 0, 0},
                {x1, y0, z0, x1, y0, z1, x1, y1, z1, x1, y1, z0, 1, 0, 0},
                {x0, y0, z1, x1, y0, z1, x1, y0, z0, x0, y0, z0, 0, -1, 0},
                {x0, y1, z0, x1, y1, z0, x1, y1, z1, x0, y1, z1, 0, 1, 0}};
        float[][] corner = {{uv[0], uv[1]}, {uv[2], uv[1]}, {uv[2], uv[3]}, {uv[0], uv[3]}};
        for (float[] f : faces) {
            for (int i = 0; i < 4; i++) {
                vc.addVertex(pose, f[i * 3] / 16.0F, f[i * 3 + 1] / 16.0F, f[i * 3 + 2] / 16.0F)
                        .setColor(color).setUv(corner[i][0], corner[i][1]).setOverlay(overlay).setLight(light)
                        .setNormal(pose, f[12], f[13], f[14]);
            }
        }
    }

    private static void draw(float[] q, PoseStack ps, VertexConsumer vc, int light, int overlay, int color) {
        PoseStack.Pose pose = ps.last();
        for (int i = 0; i < q.length; i += STRIDE) {
            for (int v = 0; v < 4; v++) {
                int o = i + v * 5;
                vc.addVertex(pose, q[o] / 16.0F, q[o + 1] / 16.0F, q[o + 2] / 16.0F)
                        .setColor(color).setUv(q[o + 3], q[o + 4]).setOverlay(overlay).setLight(light)
                        .setNormal(pose, q[i + 20], q[i + 21], q[i + 22]);
            }
        }
    }
}
