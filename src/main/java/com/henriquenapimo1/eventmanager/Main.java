package com.henriquenapimo1.eventmanager;

import com.henriquenapimo1.eventmanager.listeners.CommandListener;
import com.henriquenapimo1.eventmanager.listeners.EventListener;
import com.henriquenapimo1.eventmanager.listeners.MenuListener;
import com.henriquenapimo1.eventmanager.utils.ChatEventManager;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public Evento evento;
    public Quiz quiz;
    public Vouf vouf;
    public Bolao bolao;
    public Loteria loteria;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - A dependência Vault não foi encontrada! Desabilitando o plugin", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();
        CustomMessages.loadMessages();

        List<String> cmds = Arrays.asList("eventmanager","evento","quiz","vouf","bolao","loteria");

        cmds.forEach(c -> {
            PluginCommand cmd = getCommand(c);
            assert cmd != null;

            cmd.setExecutor(new CommandListener());
            cmd.setTabCompleter(this);
        });

        getServer().getPluginManager().registerEvents(new EventListener(),this);
        getServer().getPluginManager().registerEvents(new MenuListener(),this);

        if(Utils.getBool("bolao-ativo"))
            ChatEventManager.startBolaoScheduler();
        if(Utils.getBool("loteria-ativo"))
            ChatEventManager.startLoteriaScheduler();

        log.info(String.format("[%s] Plugin iniciado com sucesso!",getDescription().getName()));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Comando de Evento
        if(command.getName().equals("evento")) {
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

        return Collections.emptyList();
    }

    @Override
    public void onDisable() {
        if(evento != null) {
            evento.finalizar();
        }

        ChatEventManager.cancelBolao();

        quiz = null;
        vouf = null;
        bolao = null;
        loteria = null;

        log.info(String.format("[%s] Desabilitando o plugin.",getDescription().getName()));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Main getMain() {
        return (Main) Bukkit.getPluginManager().getPlugin("EventManager");
    }
}
