package com.henriquenapimo1.eventmanager.listeners;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.Flags;
import com.henriquenapimo1.eventmanager.utils.gui.InventoryGUIs;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class MenuListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryItemClickEvent(InventoryClickEvent event) {
        if(!event.getView().getTitle().equals("§8Flags de Evento"))
            return;

        if(Main.getMain().getEvento() == null) return;

        Flags fl = Main.getMain().getEvento().getFlags();

        switch (event.getRawSlot()) {
            case 19: {
                fl.changeGamemode();
            } break;
            case 21: {
                fl.setPvp(!fl.getPvp());
            } break;
            case 22: {
                fl.setFly(!fl.getFly());
            } break;
            case 24: {
                fl.setInvul(!fl.getInvul());
            } break;
            case 25: {
                fl.setRespawn(!fl.getRespawn());
            } break;
            default: {
                event.setCancelled(true);
                return;
            }
        }
        event.setCancelled(true);
        Player p = (Player) event.getWhoClicked();

        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT,100,1);
        p.openInventory(InventoryGUIs.getFlagsGUI());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onItemDrop(PlayerDropItemEvent event) {
        assert event.getItemDrop().getItemStack().getItemMeta() != null;

        switch (event.getItemDrop().getItemStack().getItemMeta().getDisplayName()) {
            case "§7 ":
            case "§cSair":
            case "§6Gamemode":
            case "§6PvP":
            case "§6Fly":
            case "§6Invulnerabilidade":
            case "§6Renascimento":
            case "§aAlterar Gamemode":
            case "§aAtivo":
            case "§cDesativado": {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemClickEvent(PlayerInteractEvent event) {
        if(Arrays.asList(Action.RIGHT_CLICK_AIR,Action.RIGHT_CLICK_BLOCK).contains(event.getAction())) {

            if(event.getItem() == null) return;
            assert event.getItem().getItemMeta() != null;

            Evento evento = Main.getMain().getEvento();
            if(evento == null) return;

            switch (event.getItem().getItemMeta().getDisplayName()) {
                case "§7Flags": {
                    event.getPlayer().openInventory(InventoryGUIs.getFlagsGUI());
                } break;

                case "§cSair": {
                    if(evento.getPlayers().contains(event.getPlayer())) {
                        evento.removePlayer(event.getPlayer(),false);
                        event.getPlayer().sendMessage("§7Você saiu do evento.");
                        event.getPlayer().getInventory().remove(event.getItem());
                    }
                    if(evento.getSpectators().contains(event.getPlayer())) {
                        evento.removeSpectator(event.getPlayer());
                        event.getPlayer().sendMessage("§7Você saiu do evento.");
                        event.getPlayer().getInventory().remove(event.getItem());
                    }
                } break;

            }
        }
    }

}
