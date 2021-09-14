/*

Method of teams working:
- Send each player online to the server
- Response will be formatted like so:
{
    player: "MattMX",
    teamname: "inferno",
    teamtitle: "&cInferno"
}
- Data will be serialized into a new Team registered to the client

 */
package com.mattmx.teamsplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Matrix4f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static net.minecraft.client.gui.DrawableHelper.drawCenteredText;
import static net.minecraft.client.gui.DrawableHelper.drawTextWithShadow;

public class RegisteredPlayer {
    public static ArrayList<RegisteredPlayer> PLAYERS = new ArrayList<>();

    public String USERNAME;
    public String TEAMNAME;

    public RegisteredPlayer(String name, String teamname) {
        this.USERNAME = name;
        this.TEAMNAME = teamname;
    }

    public static void beginRepeat() {
        final int[] timer = {1};
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (timer[0] % 6000 == 0) {
                getAll();
                timer[0] = 1;
            }
            timer[0]++;
        });
    }

    public static String str_URL = "https://pastebin.com/raw/rX8eXncq";

    public static String getPlayer(String name) {
        for (RegisteredPlayer player : PLAYERS) {
            if (player.USERNAME.equalsIgnoreCase(name)) {
                return player.TEAMNAME;
            }
        }
        return null;
    }

    public static void getAll() {
        PLAYERS.clear();
        URL url = null;
        try {
            url = new URL(str_URL);
        } catch (MalformedURLException e) {
            System.out.println("ERROR: COULD NOT GET URL " + str_URL + " THIS MAY BE AN ERROR WITH PASTEBIN.");
            e.printStackTrace();
        }
        if (url == null) {
            System.out.println("ERROR: COULD NOT GET URL " + str_URL + " THIS MAY BE AN ERROR WITH PASTEBIN.");
            return;
        }
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = in.readLine();
            while (line != null) {
                if (!(line.startsWith("#") || line.isEmpty())) {
                    String[] args = line.split(": ");
                    PLAYERS.add(new RegisteredPlayer(args[0], args[1]));
                }
                line = in.readLine();
            }
        } catch (IOException e) {
            System.out.println("ERROR: COULD NOT GET URL " + str_URL + " THIS MAY BE AN ERROR WITH PASTEBIN.");
            e.printStackTrace();
        }
        System.out.println("Successfully synced player-data with " + PLAYERS.size() + " entries total.");
        if (MinecraftClient.getInstance().world != null) {
            if (PLAYERS.size() == 0) {
                MinecraftClient.getInstance().player.sendMessage(new LiteralText("Unable to parse data from " + str_URL).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, str_URL)).withColor(Formatting.RED)), false);
                return;
            }
            MinecraftClient.getInstance().player.sendMessage(Utils.chat(
                    "&cSuccessfully synced player-data with &6" + PLAYERS.size() + " &centires total."), true);
        }
    }
}
