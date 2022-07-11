package com.henriquenapimo1.eventmanager.utils.commands;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Enquete;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Evento;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabComplete {

    public static List<String> complete(CommandSender sender, Command command, String[] args) {
        // Comando de Evento
        if(command.getName().equals("evento")) {
            Evento evento = Main.getMain().evento;
            if(args.length <= 1) {
                List<String> tab = new ArrayList<>(Arrays.asList("entrar", "sair", "help"));

                if (sender.hasPermission("eventmanager.mod"))
                    tab.addAll(Arrays.asList("anunciar", "ban", "bc", "unban"));

                if (sender.hasPermission("eventmanager.admin"))
                    tab.addAll(Arrays.asList("cancelar", "criar", "darefeito", "daritem", "effectclear", "finalizar",
                            "flags", "gamemode","iniciar", "itemclear", "setpremio", "setspawn", "tphere", "trancar"));

                return tab;
            }
            if(args.length <= 2) {
                switch (args[0]) {
                    case "ban": {
                        List<String> st = new ArrayList<>();
                        evento.getPlayers().forEach(p -> st.add(p.getName()));
                        return st;
                    }
                    case "unban": {
                        List<String> st = new ArrayList<>();
                        //noinspection ConstantConditions
                        evento.getBannedPlayers().forEach(p -> st.add(Bukkit.getPlayer(p).getName()));
                        return st;
                    }
                    case "gamemode": {
                        return Arrays.asList("survival","creative","adventure","spectator");
                    }
                }
            }
            if(args[1].equalsIgnoreCase("finalizar")) {
                List<String> st = new ArrayList<>();
                evento.getPlayers().forEach(p -> st.add(p.getName()));
                return st;
            }
        }

        // Comando de Quiz
        if(command.getName().equalsIgnoreCase("quiz")) {
            if(args.length <= 1) {
                List<String> tab = new ArrayList<>(Arrays.asList("help", "resposta"));

                if (sender.hasPermission("eventmanager.quiz.criar"))
                    tab.addAll(Arrays.asList("criar", "setresposta","finalizar"));

                return tab;
            }
        }

        // Comando de VouF
        if(command.getName().equalsIgnoreCase("vouf")) {
            if(args.length <= 1) {
                List<String> tab = new ArrayList<>(Arrays.asList("help", "resposta"));

                if (sender.hasPermission("eventmanager.vouf.criar"))
                    tab.addAll(Arrays.asList("criar", "finalizar"));

                return tab;
            }
        }

        // Comando Eventmanager
        if(command.getName().equalsIgnoreCase("eventmanager")) {
            if(args.length <= 1) {
                List<String> tab = new ArrayList<>(Collections.singletonList("info"));

                if (sender.hasPermission("eventmanager.staff"))
                    tab.addAll(Arrays.asList("help","reload","config"));

                return tab;
            }
            if(args.length <= 2 && sender.hasPermission("eventmanager.admin")) {
                switch (args[0]) {
                    case "reload": {
                        return Collections.singletonList("confirm");
                    }
                    case "help": {
                        return Arrays.asList("evento","quiz","vouf","perms");
                    }
                    case "config": {
                        return Arrays.asList("help","set","info","list");
                    }
                }
            }
            if(args.length <= 3 && sender.hasPermission("eventmanager.admin")) {
                switch (args[1]) {
                    case "set":
                    case "info": {
                        return new ArrayList<>(Utils.getConfiguration().keySet());
                    }
                }
            }
        }

        // Comando Bolão
        if(command.getName().equalsIgnoreCase("bolao")) {
            if(args.length <= 1) {
                List<String> tab = new ArrayList<>(Arrays.asList("help", "apostar"));

                if (sender.hasPermission("eventmanager.bolao.criar"))
                    tab.addAll(Arrays.asList("criar","finalizar"));

                return tab;
            }
        }

        // Comando Loteria
        if(command.getName().equalsIgnoreCase("loteria")) {
            if(args.length <= 1) {
                List<String> tab = new ArrayList<>(Arrays.asList("help", "apostar"));

                if (sender.hasPermission("eventmanager.loteria.criar"))
                    tab.addAll(Arrays.asList("criar","finalizar"));

                return tab;
            }
            if(args.length == 3 && args[0].equalsIgnoreCase("criar")) {
                try {
                    //noinspection unused
                    int i = Integer.parseInt(args[2]);
                    return Collections.emptyList();
                } catch (Exception e) {
                    //
                }
                return Collections.singletonList("random");
            }
        }

        // Comando Enquete
        if(command.getName().equalsIgnoreCase("enquete")) {
            Enquete enquete = Main.getMain().enquete;
            if(args.length <= 1) {
                List<String> tab = new ArrayList<>(Arrays.asList("help", "votar"));

                if (sender.hasPermission("eventmanager.loteria.criar"))
                    tab.addAll(Arrays.asList("criar","add","finalizar","iniciar"));

                return tab;
            }

            if(args.length == 2 && args[0].equalsIgnoreCase("finalizar")) {
                return Collections.singletonList("-s");
            }
            if(args.length == 2 && args[0].equalsIgnoreCase("votar") && enquete != null) {
                return enquete.getAlternativas();
            }
        }

        return Collections.emptyList();
    }
}
