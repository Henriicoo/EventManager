package com.henriquenapimo1.eventmanager;

import com.henriquenapimo1.eventmanager.listeners.CommandListener;
import com.henriquenapimo1.eventmanager.listeners.EventListener;
import com.henriquenapimo1.eventmanager.utils.Evento;
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

    private Evento evento;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - A dependência Vault não foi encontrada! Desabilitando o plugin", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        PluginCommand cmd = getCommand("evento");
        assert cmd != null;

        cmd.setExecutor(new CommandListener());
        cmd.setTabCompleter(this);

        getServer().getPluginManager().registerEvents(new EventListener(),this);

        saveDefaultConfig();

        log.info(String.format("[%s] Plugin iniciado com sucesso!",getDescription().getName()));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(command.getName().equals("evento") && args.length <= 1) {
            List<String> tab = new ArrayList<>(Arrays.asList("entrar", "sair","help"));

            if(sender.hasPermission("eventmanager.mod"))
                tab.addAll(Arrays.asList("anunciar","ban","bc","unban"));

            if(sender.hasPermission("eventmanager.admin"))
                tab.addAll(Arrays.asList("cancelar","criar","darefeito","daritem","effectclear","finalizar",
                        "gamemode","itemclear","reload","setpremio","setspawn", "tphere","trancar"));

            return tab;
        }

        if(args.length == 1) {
            switch (args[0]) {
                case "ban": {
                    List<String> st = new ArrayList<>();
                    evento.getPlayers().forEach(p -> st.add(p.getName()));
                    return st;
                }
                case "unban": {
                    List<String> st = new ArrayList<>();
                    evento.getBannedPlayers().forEach(p -> st.add(Bukkit.getPlayer(p).getName()));
                    return st;
                }
                case "gamemode": {
                    return Arrays.asList("survival","creative","adventure","spectator");
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void onDisable() {
        if(getEvento() != null) {
            getEvento().finalizar();
        }

        log.info(String.format("[%s] Desabilitando o plugin.",getDescription().getName()));
    }

    public void setEvento(Evento e) {
        evento = e;
    }

    public void removeEvento() {
        evento = null;
    }

    public Evento getEvento() {
        return evento;
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
