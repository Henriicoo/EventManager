package com.henriquenapimo1.eventmanager.utils.objetos;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.ChatEventManager;
import com.henriquenapimo1.eventmanager.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Bolao {

    private final int valorInicial;
    private int valorAcumulado = 0;
    private final HashSet<Player> apostadores = new HashSet<>();

    private int taskId;
    private int anuncios;

    public Bolao(int valorInicial) {
        this.valorInicial = valorInicial;
        Main.getMain().bolao = this;
        anunciar();
    }

    @SuppressWarnings("unused")
    public void apostar(Player p) {
        if(getApostadores().contains(p)) {
            p.sendMessage("§7Você já apostou nesse bolão! Aguarde o resultado.");
            return;
        }
        if(Main.getEconomy().getBalance(p) < valorInicial) {
            p.sendMessage("§cVocê não tem dinheiro suficiente para apostar no bolão!");
            return;
        }

        Main.getEconomy().withdrawPlayer(p,valorInicial);
        getApostadores().add(p);
        valorAcumulado = valorAcumulado+valorInicial;

        p.sendMessage("§7Você apostou no bolão! Aguarde o resultado. Boa sorte!");
    }

    private void anunciar() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), () -> {
            if(anuncios == Utils.getInt("bolao-anuncios")) {
                finalizar();
            } else {
                anuncios = anuncios++;
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
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("§8[§6Bolão§8] §7Bolão cancelado! Não houve apostadores nesse bolão."));
            return;
        }
        Player ganhador;

        if(getApostadores().size() == 1) {
         ganhador = (Player) getApostadores().toArray()[0];
        } else {
            ganhador = (Player) apostadores.toArray()
                    [(int) Math.floor(Math.random()*(apostadores.size())+0)];
        }

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("§8[§6§lBolão§8] §7O(a) ganhador(a) do bolão foi: §f" + ganhador.getName() + "§7! Ele(a) ganhou a quantia total de §6R$" + valorAcumulado + "§7!"));
        getApostadores().stream().filter(a -> a != ganhador).forEach(a -> a.sendMessage("§7Você perdeu o bolão e perdeu R$"+valorInicial));

        ganhador.sendMessage("§aVocê ganhou o bolão e ganhou um total de R$"+valorAcumulado);
        Main.getEconomy().depositPlayer(ganhador,valorAcumulado);

        for (int i=0;i<5;i++) {
            Utils.spawnFirework(ganhador);
        }

        Main.getMain().bolao = null;
        if(Utils.getBool("bolao-ativo"))
            ChatEventManager.startBolaoScheduler();
    }

    public HashSet<Player> getApostadores() {
        return apostadores;
    }
}
