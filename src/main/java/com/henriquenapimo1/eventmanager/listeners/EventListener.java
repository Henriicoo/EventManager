package com.henriquenapimo1.eventmanager.listeners;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event) {
        Evento e = Main.getMain().evento;
        if(e != null && e.getPlayers().contains(event.getPlayer())) {
            e.removePlayer(event.getPlayer(),false);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(event.getPlayer().hasPermission("eventmanager.staff")) return;

        // se tem evento
        Evento e = Main.getMain().evento;
        if(e != null && event.getPlayer().hasPermission("eventmanager.entrar")) {
            event.getPlayer().spigot().sendMessage(Utils.getAnuncio(e).create());
        }
        // se tem quiz
        Quiz q = Main.getMain().quiz;
        if(q != null && event.getPlayer().hasPermission("eventmanager.quiz.responder")) {
            event.getPlayer().spigot().sendMessage(Utils.getAnuncio(q).create());
        }
        // se tem vouf
        Vouf v = Main.getMain().vouf;
        if(v != null && event.getPlayer().hasPermission("eventmanager.vouf.responder")) {
            event.getPlayer().spigot().sendMessage(Utils.getAnuncio(v).create());
        }
        //se tem bolão
        Bolao b = Main.getMain().bolao;
        if(b != null && event.getPlayer().hasPermission("eventmanager.bolao.apostar")) {
            event.getPlayer().spigot().sendMessage(Utils.getAnuncio(b).create());
        }
        // se tem loteria
        Loteria l = Main.getMain().loteria;
        if(l != null && event.getPlayer().hasPermission("eventmanager.loteria.apostar")) {
            event.getPlayer().spigot().sendMessage(Utils.getAnuncio(l).create());
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        Evento e = Main.getMain().evento;
        if(e == null || !e.getFlags().getInvul()) return;

        if(!event.getEntityType().equals(EntityType.PLAYER)) return;

        Player p = (Player) event.getEntity();

        if(e.getPlayers().contains(p)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPvpEvent(EntityDamageByEntityEvent event) {
        Evento e = Main.getMain().evento;
        if(e == null || !e.getFlags().getPvp()) return;

        if(!event.getEntity().getType().equals(EntityType.PLAYER) || !event.getDamager().getType().equals(EntityType.PLAYER)) return;

        if(e.getPlayers().contains((Player) event.getEntity())
                && e.getPlayers().contains((Player) event.getDamager())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Evento e = Main.getMain().evento;
        if(e == null) return;

        if(e.getPlayers().contains(event.getEntity())) {
            event.getDrops().clear();
            if(e.getFlags().getRespawn()) {
                Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> e.addDeadPlayer(event.getEntity()),20);
            } else {
                event.getEntity().sendMessage("§7Você morreu, por isso, foi removido do evento!");
                e.removePlayer(event.getEntity(),false);
            }
        }
    }
}
