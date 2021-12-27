package com.henriquenapimo1.eventmanager.listeners;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBarrierClick(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            ItemStack i = event.getPlayer().getInventory().getItemInMainHand();
            if(i.getType().equals(Material.BARRIER) && i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals("§cSair")) {

                Evento evento = Main.getMain().getEvento();
                if(evento != null) {

                    if(evento.getPlayers().contains(event.getPlayer())) {
                        evento.removePlayer(event.getPlayer(),false);
                        event.getPlayer().sendMessage("§7Você saiu do evento.");
                        event.getPlayer().getInventory().remove(i);
                    }
                    if(evento.getSpectators().contains(event.getPlayer())) {
                        evento.removeSpectator(event.getPlayer());
                        event.getPlayer().sendMessage("§7Você saiu do evento.");
                        event.getPlayer().getInventory().remove(i);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBarrierDrop(PlayerDropItemEvent event) {
        ItemStack i = event.getItemDrop().getItemStack();
        if(i.getType().equals(Material.BARRIER) && i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals("§cSair")) {

            Evento evento = Main.getMain().getEvento();
            if (evento != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Evento e = Main.getMain().getEvento();
        if(e != null && e.getPlayers().contains(event.getPlayer())) {
            e.removePlayer(event.getPlayer(),false);
        }
    }
}
