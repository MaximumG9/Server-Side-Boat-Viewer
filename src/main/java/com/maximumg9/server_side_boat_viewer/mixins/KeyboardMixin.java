package com.maximumg9.server_side_boat_viewer.mixins;

import com.maximumg9.server_side_boat_viewer.ServerSideBoatViewer;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method="processF3",at=@At(value="INVOKE",target="Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;)V"))
    private void addValueChoice(int key, CallbackInfoReturnable<Boolean> cir) {
        ((Keyboard) ((Object) this)).client.inGameHud.getChatHud().addMessage(Text.of("F3 + Y = Turn on rendering of server versions of boats (singleplayer only)"));
    }

    @Inject(method="processF3",at=@At(value="TAIL"))
    private void addKeybind(int key, CallbackInfoReturnable<Boolean> cir) {
        if(key == 89) {
            ServerSideBoatViewer.renderingServerBoats = !ServerSideBoatViewer.renderingServerBoats;
        }
    }
}
