package com.henriquenapimo1.eventmanager.utils;

import com.henriquenapimo1.eventmanager.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CustomMessages {

    private static YamlConfiguration msg = null;

    public static void loadMessages() {
        File messageFile = new File(Main.getMain().getDataFolder(), "mensagens.yml");

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
}
