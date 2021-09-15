package com.mattmx.teamsplus;

import com.mattmx.teamsplus.modmenu.ConfigFile;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    public static final String MOD_ID = "teams-plus";
    public static ConfigFile OPTIONS;
    @Override
    public void onInitialize() {
        AutoConfig.register(ConfigFile.class, GsonConfigSerializer::new);
        OPTIONS = AutoConfig.getConfigHolder(ConfigFile.class).getConfig();
        RegisteredPlayer.getAll();
        RegisteredPlayer.beginRepeat();
    }
}
