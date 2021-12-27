package com.henriquenapimo1.eventmanager.utils.gui;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.Flags;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class InventoryGUIs {

    public static Inventory getFlagsGUI() {
        Evento e = Main.getMain().getEvento();

        Flags f = e.getFlags();

        Inventory i = Bukkit.createInventory(null,9*4,"§8Flags de Evento");

        for (int a = 0; a < i.getSize(); a++) {
            i.setItem(a,Itens.getItem(Material.GRAY_STAINED_GLASS_PANE,"§7 "));
        }

        i.setItem(10, Itens.getItem(Material.DIAMOND_BLOCK,"§6Gamemode","§7Clique para alterar o gamemode"));
        i.setItem(12, Itens.getItem(Material.IRON_SWORD,"§6PvP","§7Clique para alterar o modo PvP"));
        i.setItem(13,Itens.getItem(Material.ELYTRA,"§6Fly","§7Clique para alterar o fly"));
        i.setItem(15,Itens.getItem(Material.GOLDEN_APPLE,"§6Invulnerabilidade","§7Clique para alterar a invulnerabilidade"));
        i.setItem(16,Itens.getItem(Material.TOTEM_OF_UNDYING,"§6Renascimento","§7Clique para alterar o renascimento"));

        i.setItem(19,Itens.getGamemode());
        i.setItem(21, Itens.getBoolItem(f.getPvp()));
        i.setItem(22,Itens.getBoolItem(f.getFly()));
        i.setItem(24,Itens.getBoolItem(f.getInvul()));
        i.setItem(25,Itens.getBoolItem(f.getRespawn()));
        return i;
    }
}
