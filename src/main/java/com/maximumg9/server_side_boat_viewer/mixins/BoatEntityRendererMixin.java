package com.maximumg9.server_side_boat_viewer.mixins;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BoatEntityRenderer.class)
public abstract class BoatEntityRendererMixin {
    @Shadow public abstract Identifier getTexture(BoatEntity boatEntity);

    @Inject(method="render(Lnet/minecraft/entity/vehicle/BoatEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
        at= @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/model/CompositeEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void render(BoatEntity boatEntity,
                        float f,
                        float g,
                        MatrixStack matrixStack,
                        VertexConsumerProvider vertexConsumerProvider,
                        int i,
                        CallbackInfo ci,
                        float h,
                        float j,
                        float k,
                        Pair<Identifier, CompositeEntityModel<BoatEntity>> pair,
                        Identifier identifier,
                        CompositeEntityModel<BoatEntity> compositeEntityModel,
                        VertexConsumer vertexConsumer) {

        compositeEntityModel.render(matrixStack,vertexConsumer,i, OverlayTexture.DEFAULT_UV,boatEntity.getServer() == null ? 1.0F : 0.5F,boatEntity.getServer() == null ? 1.0F : 0.5F,boatEntity.getServer() == null ? 1.0F : 0.5F,1.0F);
    }
    @Redirect(method="render(Lnet/minecraft/entity/vehicle/BoatEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
    at=@At(value="INVOKE",target= "Lnet/minecraft/client/render/entity/model/CompositeEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    private void modifyAlpha(CompositeEntityModel<BoatEntity> instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
    } // Basically undo the call
}
