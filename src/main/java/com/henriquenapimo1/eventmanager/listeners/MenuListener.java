package com.henriquenapimo1.eventmanager.listeners;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.Flags;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.gui.InventoryGUIs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        switch (event.getView().getTitle()) {
            case "§0Flags de Evento": flagInvClick(event); break;
            case "§0Lista de Jogadores": playerInvClick(event); break;
        }
    }

    private void flagInvClick(InventoryClickEvent event) {
        if (Main.getMain().evento == null) return;

        Flags fl = Main.getMain().evento.getFlags();
        event.setCancelled(true);

        switch (event.getRawSlot()) {
            case 19: {
                fl.changeGamemode();
            }
            break;
            case 21: {
                fl.setPvp(!fl.getPvp());
            }
            break;
            case 22: {
                fl.setFly(!fl.getFly());
            }
            break;
            case 24: {
                fl.setInvul(!fl.getInvul());
            }
            break;
            case 25: {
                fl.setRespawn(!fl.getRespawn());
            }
            break;
            default: {
                return;
            }
        }

        Player p = (Player) event.getWhoClicked();

        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 100, 1);
        p.openInventory(InventoryGUIs.getFlagsGUI());
    }

    private void playerInvClick(InventoryClickEvent event) {
        if (Main.getMain().evento == null) return;
        event.setCancelled(true);

        switch (event.getRawSlot()) {
            case 45:
            case 53: {
                String n = event.getCurrentItem().getItemMeta().getDisplayName();
                if(n.startsWith("§7Página")) {
                    InventoryGUIs.getPlayersInventory(Integer.parseInt(n.replace("§7Página ","")));
                    return;
                }
            } break;
        }

        Player p = Bukkit.getPlayer(
                ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())
        );

        if(p == null) {
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().sendMessage(Utils.getPref() + " §7Não foi possível te teletransportar à esse jogador.");
            return;
        }

        if(event.getClick().isLeftClick()) {
            event.getWhoClicked().teleport(p);
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().sendMessage(Utils.getPref()+" §7Teletransportado à " + p.getName() + " com sucesso.");
        } else if(event.getClick().isRightClick()) {
            Main.getMain().evento.banPlayer(p);
            event.getWhoClicked().closeInventory();
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onItemDrop(PlayerDropItemEvent event) {
        assert event.getItemDrop().getItemStack().getItemMeta() != null;

        switch (event.getPlayer().getOpenInventory().getTitle()) {
            case "§0Flags de Evento":
            case "§0Lista de Jogadores": {
                event.setCancelled(true);
                return;
            }
        }

        switch (event.getItemDrop().getItemStack().getItemMeta().getDisplayName()) {
            case "§a§lMenu de Jogadores":
            case "§e§lMenu de Flags":
            case "§c§lSair do Evento": {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemClickEvent(PlayerInteractEvent event) {
        if(Arrays.asList(Action.RIGHT_CLICK_AIR,Action.RIGHT_CLICK_BLOCK).contains(event.getAction())) {

            if(event.getItem() == null) return;
            assert event.getItem().getItemMeta() != null;

            Evento evento = Main.getMain().evento;
            if(evento == null) return;

            switch (event.getItem().getItemMeta().getDisplayName()) {
                case "§a§lMenu de Jogadores": {
                    event.getPlayer().openInventory(InventoryGUIs.getPlayersInventory(1));
                    event.setCancelled(true);
                } break;
                case "§e§lMenu de Flags": {
                    event.getPlayer().openInventory(InventoryGUIs.getFlagsGUI());
                    event.setCancelled(true);
                } break;
                case "§c§lSair do Evento": {
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
                    event.setCancelled(true);
                } break;
            }
        }
    }

}
