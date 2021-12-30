package com.henriquenapimo1.eventmanager.utils.objetos;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class Vouf {

    private final String pergunta;
    private final int premio;

    private final HashSet<UUID> truePlayers = new HashSet<>();
    private final HashSet<UUID> falsePlayers = new HashSet<>();

    private int taskId;

    public Vouf(String pergunta, int premio) {
        this.pergunta = pergunta;
        this.premio = premio;

        anunciar();
    }

    public String getPergunta() {
        return pergunta;
    }

    public int getPremio() {
        return premio;
    }

    public void addPlayer(Player p, boolean bool) {
        if(truePlayers.contains(p.getUniqueId()) || falsePlayers.contains(p.getUniqueId())) {
            p.sendMessage(Utils.getPref(CmdContext.CommandType.VOUF) + " " +
                    CustomMessages.getString("commands.vouf.resposta.error"));
            return;
        }

        if(bool) {
            truePlayers.add(p.getUniqueId());
            p.sendMessage(CustomMessages.getString("commands.vouf.resposta.success",
                    CustomMessages.getString("events.vouf.true")));
        } else {
            falsePlayers.add(p.getUniqueId());
            p.sendMessage(CustomMessages.getString("commands.vouf.resposta.success",
                    CustomMessages.getString("events.vouf.false")));
        }
    }

    public void anunciar() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), () ->
                        Bukkit.getOnlinePlayers().forEach(p -> p.spigot().sendMessage(Utils.getAnuncio(this).create())),
                0,Utils.getInt("anunciar-vouf")*20L);
    }

    public void finalizar(boolean resposta) {
        Bukkit.getScheduler().cancelTask(taskId);

        if (resposta) {
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Utils.getPref(CmdContext.CommandType.VOUF) + " " +
                    CustomMessages.getString("events.vouf.ganhador",
                            CustomMessages.getString("events.vouf.true"))));
            truePlayers.forEach(uuid -> {
                Player p = Bukkit.getPlayer(uuid);
                if(p != null) {
                    p.sendMessage(
                            CustomMessages.getString("events.vouf.win",String.valueOf(premio))
                    );
                    Main.getEconomy().depositPlayer(p,premio);
                }});
        } else {
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Utils.getPref(CmdContext.CommandType.VOUF) + " " +
                    CustomMessages.getString("events.vouf.ganhador",
                            CustomMessages.getString("events.vouf.false"))));
            falsePlayers.forEach(uuid -> {
                Player p = Bukkit.getPlayer(uuid);
                if(p != null) {
                    p.sendMessage(
                            CustomMessages.getString("events.vouf.win",String.valueOf(premio))
                    );
                    Main.getEconomy().depositPlayer(p, premio);
                }});
        }

        Main.getMain().vouf = null;
    }
}
