package com.henriquenapimo1.eventmanager.utils.objetos;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.ChatEventManager;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class Bolao {

    private final int valorInicial;
    private int valorAcumulado = 0;
    private final HashSet<UUID> apostadores = new HashSet<>();

    private int taskId;
    private int anuncios;

    public Bolao(int valorInicial) {
        this.valorInicial = valorInicial;
        Main.getMain().bolao = this;

        anunciar();
    }

    public void apostar(Player p) {
        if(getApostadores().contains(p.getUniqueId())) {
            p.sendMessage(Utils.getPref(CmdContext.CommandType.BOLAO) + " " +
                    CustomMessages.getString("commands.bolao.apostar.error.apostou"));
            return;
        }
        if(Main.getEconomy().getBalance(p) < valorInicial) {
            p.sendMessage(Utils.getPref(CmdContext.CommandType.BOLAO) + " " +
                    CustomMessages.getString("commands.bolao.apostar.error.no-money"));
            return;
        }

        Main.getEconomy().withdrawPlayer(p,valorInicial);
        getApostadores().add(p.getUniqueId());
        valorAcumulado = valorAcumulado+valorInicial;

        p.sendMessage(Utils.getPref(CmdContext.CommandType.BOLAO) + " " +
                CustomMessages.getString("commands.bolao.apostar.success"));
    }

    public int getAnuncios() {
        this.anuncios = anuncios+1;
        return anuncios-1;
    }

    private void anunciar() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), () -> {
            if(getAnuncios() >= Utils.getInt("bolao-anuncios")) {
                finalizar();
            } else {
                Bukkit.getOnlinePlayers().forEach(p -> p.spigot().sendMessage(Utils.getAnuncio(this).create()));
            }
                },0, Utils.getInt("bolao-intervalo")*20L);
    }

    public int getValorAcumulado() {
        return valorAcumulado;
    }

    public int getValorInicial() {
        return valorInicial;
    }

    public void finalizar() {
        Bukkit.getScheduler().cancelTask(taskId);

        if(getApostadores().isEmpty()) {
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Utils.getPref(CmdContext.CommandType.BOLAO) + " " +
                    CustomMessages.getString("events.bolao.cancel")
            ));
            Main.getMain().bolao = null;
            return;
        }
        OfflinePlayer ganhador;

        if(getApostadores().size() == 1) {
         ganhador = Bukkit.getOfflinePlayer(((UUID) getApostadores().toArray()[0]));
        } else {
            ganhador = (Player) apostadores.toArray()
                    [(int) Math.floor(Math.random()*(apostadores.size())+0)];
        }

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Utils.getPref(CmdContext.CommandType.BOLAO) + " " +
                CustomMessages.getString("events.bolao.ganhador",ganhador.getName(),String.valueOf(valorAcumulado))));

        getApostadores().stream().filter(uuid -> uuid != ganhador.getUniqueId()).forEach(a -> {
            Player p = Bukkit.getPlayer(a);
            if(p != null) {
                p.sendMessage(CustomMessages.getString("events.bolao.lost",String.valueOf(valorInicial)));
            }});

        if(ganhador.isOnline() && ganhador.getPlayer() != null) {
            ganhador.getPlayer().sendMessage(CustomMessages.getString("events.bolao.win",String.valueOf(valorAcumulado)));
            Utils.spawnFirework(ganhador.getPlayer(),5);
        }

        Main.getEconomy().depositPlayer(ganhador,valorAcumulado);

        Main.getMain().bolao = null;
        if(Utils.getBool("bolao-ativo"))
            ChatEventManager.startBolaoScheduler();
    }

    public HashSet<UUID> getApostadores() {
        return apostadores;
    }
}
