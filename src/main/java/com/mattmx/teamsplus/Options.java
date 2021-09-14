package com.mattmx.teamsplus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class Options {
    private final transient static File OPTIONS_FILE = new File("TeamsPlus.cfg");
    public float TEAM_NAME_SIZE = 0.03f;
    public float TEAM_NAME_GAP = 0.8f;

    public static Options loadConfig() {
        Options optn = new Options();
        if (!OPTIONS_FILE.exists()) {
            optn.saveConfig();
        }
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String fileContent = "";
        try {
            fileContent = String.join("\n", Files.readAllLines(OPTIONS_FILE.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        optn = gson.fromJson(fileContent, Options.class);
        return optn;
    }

    public void saveConfig() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String serialized = gson.toJson(this);
        if (!OPTIONS_FILE.exists()) {
            try {
                OPTIONS_FILE.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter file = new FileWriter(OPTIONS_FILE.getName(), false);
            file.write(serialized);
            file.close();
            System.out.println("Written");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}