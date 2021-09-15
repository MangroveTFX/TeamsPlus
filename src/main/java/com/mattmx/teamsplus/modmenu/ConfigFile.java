package com.mattmx.teamsplus.modmenu;

import com.mattmx.teamsplus.Main;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.SerializedName;

@Config(name = Main.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/netherite_block.png")
public class ConfigFile implements ConfigData {
    @ConfigEntry.Category("Test")
    @ConfigEntry.Gui.TransitiveObject
    public MainCategory settings;

    public ConfigFile() {
        this.settings = new MainCategory();
    }

    public static class MainCategory {
        @Comment("Size of the team name size")
        public float TEAM_NAME_SIZE = 0.03f;

        @Comment("Gap between player's head and their team tag")
        public float TEAM_GAP_SIZE = 0.8f;

        @Comment("Reload playerdata automatically every 5 mins")
        public boolean RELOAD_AUTOMATICALLY = false;
    }
}

