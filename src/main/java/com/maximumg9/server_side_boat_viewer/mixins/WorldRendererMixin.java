package com.maximumg9.server_side_boat_viewer.mixins;

import com.maximumg9.server_side_boat_viewer.ServerSideBoatViewer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method="renderEntity",at=@At("HEAD"))
    public void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if(entity.getType() == EntityType.BOAT && ServerSideBoatViewer.renderingServerBoats) {
            IntegratedServer server = MinecraftClient.getInstance().getServer();
            if(server != null && MinecraftClient.getInstance().player != null) {
                ServerWorld world = Objects.requireNonNull(server.getPlayerManager().getPlayer(MinecraftClient.getInstance().player.getUuid())).getServerWorld();
                Entity ssentity = world.getEntity(entity.getUuid());
                if (ssentity != null) {
                    double d = ssentity.getX();
                    double e = ssentity.getY();
                    double f = ssentity.getZ();
                    float g = entity.getYaw();
                    ((WorldRenderer) ((Object) this)).entityRenderDispatcher.render(ssentity, d - cameraX, e - cameraY, f - cameraZ, g, tickDelta, matrices, vertexConsumers, ((WorldRenderer) ((Object) this)).entityRenderDispatcher.getLight(entity, tickDelta));

                }
            }
        }
    }
}
