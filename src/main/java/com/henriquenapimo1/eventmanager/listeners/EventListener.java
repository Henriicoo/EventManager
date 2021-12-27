package com.henriquenapimo1.eventmanager.listeners;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event) {
        Evento e = Main.getMain().getEvento();
        if(e != null && e.getPlayers().contains(event.getPlayer())) {
            e.removePlayer(event.getPlayer(),false);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        Evento e = Main.getMain().getEvento();
        if(e == null || !e.getFlags().getInvul()) return;

        if(!event.getEntityType().equals(EntityType.PLAYER)) return;

        Player p = (Player) event.getEntity();

        if(e.getPlayers().contains(p)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPvpEvent(EntityDamageByEntityEvent event) {
        Evento e = Main.getMain().getEvento();
        if(e == null || !e.getFlags().getPvp()) return;

        if(!event.getEntityType().equals(EntityType.PLAYER) || !event.getDamager().getType().equals(EntityType.PLAYER)) return;

        Player p = (Player) event.getEntity();

        if(e.getPlayers().contains(p)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Evento e = Main.getMain().getEvento();
        if(e == null) return;

        if(e.getPlayers().contains(event.getEntity())) {
            if(e.getFlags().getRespawn()) {
                e.addDeadPlayer(event.getEntity());
            } else {
                e.removePlayer(event.getEntity(),false);
            }
        }
    }
}
