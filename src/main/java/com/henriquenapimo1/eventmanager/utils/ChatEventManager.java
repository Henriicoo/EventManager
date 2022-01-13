package com.henriquenapimo1.eventmanager.utils;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Bolao;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Loteria;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatEventManager {

    private static int bolaoTaskId = 0;
    private static int loteriaTaskId = 0;

    public static void startBolaoScheduler() {
        long intervalo = Utils.getInt("bolao-intervalo")*60*20L;
        bolaoTaskId = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), () -> {
            if(Main.getMain().bolao != null) {
                restartBolao(false);
                return;
            }
            if(Bukkit.getServer().getOnlinePlayers().size() < 3) {
                restartBolao(true);
                return;
            }
            //noinspection unchecked
            List<Integer> valores = (List<Integer>) Utils.getList("bolao-valores");
            Collections.shuffle(valores);

            Main.getMain().bolao = new Bolao(valores.get(0));
        },intervalo);
    }

    private static void restartBolao(boolean cancel) {
        if(cancel) {
            cancelBolao();
        } else {
            if(bolaoTaskId != 0)
                Bukkit.getScheduler().cancelTask(bolaoTaskId);
        }

        startBolaoScheduler();
    }

    public static void iniciarBolao(int valorInicial) {
        cancelBolao();
        Main.getMain().bolao = new Bolao(valorInicial);
    }

    public static void cancelBolao() {
        if(bolaoTaskId != 0)
            Bukkit.getScheduler().cancelTask(bolaoTaskId);

        Bolao b = Main.getMain().bolao;
        if(b == null)
            return;

        if(b.getApostadores().isEmpty()) {
            Main.getMain().bolao = null;
            return;
        }
        AtomicInteger i = new AtomicInteger();

        b.getApostadores().forEach(uuid -> {
            OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
            Main.getEconomy().depositPlayer(p, b.getValorInicial());

            if(p.isOnline() && p.getPlayer() != null) {
                p.getPlayer().sendMessage("§aSeu dinheiro do bolão foi devolvido!");
            }

            if(i.get()==b.getApostadores().size()) {
                Main.getMain().bolao = null;
                return;
            }
            i.getAndIncrement();
        });
    }

    public static void startLoteriaScheduler() {
        long intervalo = Utils.getInt("loteria-intervalo")*60*20L;
        loteriaTaskId = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), () -> {
            if(Main.getMain().loteria != null) {
                restartLoteria(false);
                return;
            }
            if(Bukkit.getServer().getOnlinePlayers().size() < 3) {
                restartLoteria(true);
                return;
            }
            //noinspection unchecked
            List<Integer> valores = (List<Integer>) Utils.getList("loteria-premios");
            Collections.shuffle(valores);

            Main.getMain().loteria = new Loteria(valores.get(0),0,true);
        },intervalo);
    }

    private static void restartLoteria(boolean cancel) {
        if(cancel) {
            cancelLoteria();
        } else {
            if(loteriaTaskId != 0)
                Bukkit.getScheduler().cancelTask(loteriaTaskId);
        }

        startLoteriaScheduler();
    }

    public static void iniciarLoteria(int premio, int valorInicial) {
        cancelLoteria();
        Main.getMain().loteria = new Loteria(premio,valorInicial,false);
    }

    public static void cancelLoteria() {
        if(loteriaTaskId != 0)
            Bukkit.getScheduler().cancelTask(loteriaTaskId);

        Loteria l = Main.getMain().loteria;

        if(l == null)
            return;

        Main.getMain().loteria = null;
    }
}
