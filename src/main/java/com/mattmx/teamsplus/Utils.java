package com.mattmx.teamsplus;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;

public class Utils {
    public static Text chat(String str){
        return Text.of(str.replace("&","§"));
    }
    public static void playsound(SoundEvent sound, float pitch, float vol){
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(sound,pitch,vol));
    }
    public static String formatFromHTML(String str) {
        return str.replace("&#39;", "'")
                .replace("&amp;", "&")
                .replace("</td>", "");
    }
}