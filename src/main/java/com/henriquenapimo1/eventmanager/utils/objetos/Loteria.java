package com.henriquenapimo1.eventmanager.utils.objetos;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.ChatEventManager;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.Utils;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Loteria {

    private final int numeroFinal;
    private final int premio;
    private final HashMap<UUID,Integer> apostas = new HashMap<>();
    private final boolean auto;

    private int taskId;

    public Loteria(int premio, int numFinal, boolean auto) {
        this.premio = premio;
        this.auto = auto;

        if(numFinal == 0) {
            this.numeroFinal = (int) Math.floor(Math.random() * (getMaxNumero()));
        } else {
            this.numeroFinal = numFinal;
        }

        Main.getMain().loteria = this;

        anunciar();
    }

    public int getPremio() {
        return premio;
    }

    public int getMaxNumero() {
        return Utils.getInt("loteria-max-numero");
    }

    public int getMaxApostas() {
        return Utils.getInt("loteria-max-apostas");
    }

    public boolean apostar(Player p, int aposta) {
        int max = getMaxApostas();
        if(max != -1 && apostas.containsKey(p.getUniqueId())) {
            int i = apostas.get(p.getUniqueId());

            if(max == i) {
                p.sendMessage(Utils.getPref(CmdContext.CommandType.LOTERIA) + " " +
                        CustomMessages.getString("commands.loteria.apostar.error.no-chances",String.valueOf(max)));
                return false;
            }

            apostas.replace(p.getUniqueId(),i+1);

            if(aposta == numeroFinal) {
                finalizar(p);
                return false;
            }

            return true;
        }

        apostas.put(p.getUniqueId(),1);

        if(aposta == numeroFinal) {
            finalizar(p);
            return false;
        }

        return true;
    }

    public int getApostas(Player p) {
        return apostas.get(p.getUniqueId());
    }

    private int anuncios = 0;

    public int getAnuncios() {
        this.anuncios = anuncios+1;
        return anuncios-1;
    }

    private void anunciar() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), () -> {
            if(auto && getAnuncios() >= Utils.getInt("loteria-anuncios")) {
                finalizar(null);
            } else {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if(p.hasPermission("eventmanager.admin")) {
                        ComponentBuilder b = Utils.getAnuncio(this);
                        b.getComponent(0).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§7Número escolhido: " + numeroFinal)));
                        p.spigot().sendMessage(b.create());
                        return;
                    }

                    p.spigot().sendMessage(Utils.getAnuncio(this).create());
                });
            }},
                0,Utils.getInt("loteria-anunciar")*20L);
    }

    public void finalizar(Player ganhador) {
        Bukkit.getScheduler().cancelTask(taskId);

        if(ganhador == null) {
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Utils.getPref(CmdContext.CommandType.LOTERIA) + " " +
                    CustomMessages.getString("events.loteria.cancel",String.valueOf(numeroFinal))));
            return;
        }

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Utils.getPref(CmdContext.CommandType.LOTERIA) + " " +
                CustomMessages.getString("events.loteria.ganhador",ganhador.getName(),String.valueOf(numeroFinal))));

        ganhador.sendMessage(CustomMessages.getString("commands.loteria.apostar.success", String.valueOf(premio)));

        Main.getEconomy().depositPlayer(ganhador,premio);
        Utils.spawnFirework(ganhador,5);

        Main.getMain().loteria = null;

        if(Utils.getBool("loteria-ativo"))
            ChatEventManager.startLoteriaScheduler();
    }
}
