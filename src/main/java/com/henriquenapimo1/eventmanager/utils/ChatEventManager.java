package com.henriquenapimo1.eventmanager.utils;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.Bolao;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatEventManager {

    private static int bolaoTaskId = 0;

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
}
