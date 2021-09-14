package com.mattmx.teamsplus.mixins;

import com.mattmx.teamsplus.Main;
import com.mattmx.teamsplus.RegisteredPlayer;
import com.mattmx.teamsplus.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class PlayerRenderMixin<T extends Entity> {
    protected final EntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"))
    protected void renderLabelIfPresent(T entity, Text display, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        double d = this.dispatcher.getSquaredDistanceToCamera(entity);
        if (!(entity instanceof PlayerEntity)) return;
        String teamTag = RegisteredPlayer.getPlayer(entity.getName().getString());
        if (teamTag == null) return;
        display = Utils.chat(teamTag);
        if (!(d > 4096.0D)) {
            boolean bl = !entity.isSneaky();
            float f = entity.getHeight() + Main.OPTIONS.TEAM_NAME_GAP;
            int i = 0;
            matrices.push();
            matrices.translate(0.0D, (double)f, 0.0D);
            matrices.multiply(this.dispatcher.getRotation());
            matrices.scale(-Main.OPTIONS.TEAM_NAME_SIZE, -Main.OPTIONS.TEAM_NAME_SIZE, -Main.OPTIONS.TEAM_NAME_SIZE);
            Matrix4f matrix4f = matrices.peek().getModel();
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            float h = (float)(-textRenderer.getWidth(display) / 2);

            textRenderer.draw(display, h, (float)i, 553648127, false, matrix4f, vertexConsumers, bl, 0, light);
            if (bl) {
                textRenderer.draw(display, h, (float)i, -1, false, matrix4f, vertexConsumers, true, 0, light);
            }

            matrices.pop();
        }
    }
}
