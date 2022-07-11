package com.henriquenapimo1.eventmanager;

import com.henriquenapimo1.eventmanager.listeners.EventListener;
import com.henriquenapimo1.eventmanager.listeners.MenuListener;
import com.henriquenapimo1.eventmanager.utils.AutoUpdater;
import com.henriquenapimo1.eventmanager.utils.ChatEventManager;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.commands.CommandManager;
import com.henriquenapimo1.eventmanager.utils.objetos.events.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public Evento evento;
    public Quiz quiz;
    public Vouf vouf;
    public Bolao bolao;
    public Loteria loteria;
    public Enquete enquete;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - A dependência Vault não foi encontrada! Desabilitando o plugin", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getConfig().options().copyDefaults(true);
        CustomMessages.loadMessages();

        // Load commands
        new CommandManager(this);

        new AutoUpdater(this);

        getServer().getPluginManager().registerEvents(new EventListener(),this);
        getServer().getPluginManager().registerEvents(new MenuListener(),this);

        if(Utils.getBool("bolao-ativo"))
            ChatEventManager.startBolaoScheduler();

        if(Utils.getBool("loteria-ativo"))
            Bukkit.getScheduler().runTaskLater(this, ChatEventManager::startLoteriaScheduler,
                (Utils.getInt("bolao-intervalo")/2)*60*20L);

        log.info(String.format("[%s] Plugin iniciado com sucesso!",getDescription().getName()));
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
        enquete = null;

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
