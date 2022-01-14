package com.henriquenapimo1.eventmanager.utils;

import com.henriquenapimo1.eventmanager.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class CustomMessages {

    private static YamlConfiguration msg;
    private static File messageFile;

    public static void loadMessages() {
        msg = null;

        messageFile = new File(Main.getMain().getDataFolder(), "mensagens.yml");

       if(!messageFile.exists()) {
           Main.getMain().saveResource("mensagens.yml",false);
       }

       msg = YamlConfiguration.loadConfiguration(messageFile);
    }

    public static String getString(String path) {
        //noinspection ConstantConditions
        return ChatColor.translateAlternateColorCodes('&',msg.getString(path));
    }

    public static String getString(String path, String... args) {
        path = CustomMessages.getString(path);

        if(args.length == 1) {
            path = path.replace("{0}",args[0]);
        } else if(args.length == 2) {
            path = path.replace("{0}",args[0]).replace("{1}",args[1]);
        }
        return path;
    }

    public static void messageUpdate() throws IOException {
        //noinspection ConstantConditions
        Reader targetReader = new InputStreamReader(Main.getMain().getResource("mensagens.yml"));

        YamlConfiguration c = YamlConfiguration.loadConfiguration(targetReader);
        targetReader.close();

        boolean change = false;
        for (String defaultKey : c.getKeys(true)) {
            if (!msg.contains(defaultKey)) {
                msg.set(defaultKey, c.get(defaultKey));
                msg.save(messageFile);
                change = true;
            }
        }

        if (change) msg.save(messageFile);
        loadMessages();
    }
}
