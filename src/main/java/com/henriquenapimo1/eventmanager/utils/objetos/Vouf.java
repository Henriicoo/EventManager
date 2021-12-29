package com.henriquenapimo1.eventmanager.utils.objetos;

import com.henriquenapimo1.eventmanager.Main;
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
            p.sendMessage("§7Você já respondeu esse VouF e não pode mais mudar!");
            return;
        }

        if(bool) {
            truePlayers.add(p.getUniqueId());
            p.sendMessage("§7Você marcou esse VouF como "+Utils.getString("vouf-true")+"§7! Agora, espere o resultado sair.");
        } else {
            falsePlayers.add(p.getUniqueId());
            p.sendMessage("§7Você marcou esse VouF como "+Utils.getString("vouf-false")+"§7! Agora, espere o resultado sair.");
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
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("§8[§e§lVouF§8] §7A resposta correta era "+Utils.getString("vouf-true")+"§7!"));
            truePlayers.forEach(uuid -> {
                Player p = Bukkit.getPlayer(uuid);
                if(p != null) {
                    p.sendMessage("§aVocê acertou o VouF e recebeu R$"+premio);
                    Main.getEconomy().depositPlayer(p,premio);
                }});
        } else {
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("§8[§e§lVouF§8] §7A resposta correta era "+Utils.getString("vouf-false")+"§7!"));
            falsePlayers.forEach(uuid -> {
                Player p = Bukkit.getPlayer(uuid);
                if(p != null) {
                    p.sendMessage("§aVocê acertou o VouF e recebeu R$" + premio);
                    Main.getEconomy().depositPlayer(p, premio);
                }});
        }

        Main.getMain().vouf = null;
    }
}
