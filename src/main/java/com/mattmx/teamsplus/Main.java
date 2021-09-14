package com.mattmx.teamsplus;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    public static Options OPTIONS = new Options();
    public static boolean ENABLED = true;
    @Override
    public void onInitialize() {
        OPTIONS.saveConfig();
        OPTIONS = Options.loadConfig();
        RegisteredPlayer.getAll();
        RegisteredPlayer.beginRepeat();
    }
}
