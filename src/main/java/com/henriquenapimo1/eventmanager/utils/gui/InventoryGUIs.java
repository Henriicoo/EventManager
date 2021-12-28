package com.henriquenapimo1.eventmanager.utils.gui;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.Flags;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InventoryGUIs {

    public static Inventory getFlagsGUI() {
        Evento e = Main.getMain().evento;

        Flags f = e.getFlags();

        Inventory i = Bukkit.createInventory(null,9*4,"§0Flags de Evento");

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

    public static void setStaffHotbar(Player p) {
        p.getInventory().setItem(0,Itens.getItem(Material.COMPASS,"§a§lMenu de Jogadores","§7Clique direito para abrir o menu de players"));
        p.getInventory().setItem(4,Itens.getItem(Material.COMPARATOR,"§e§lMenu de Flags","§7Clique direito para abrir o menu de flags"));
        p.getInventory().setItem(8,Itens.getItem(Material.BARRIER,"§c§lSair do Evento","§7Clique direito para sair do evento"));
    }

    public static Inventory getPlayersInventory(int pag) {
        Evento e = Main.getMain().evento;
        pag = pag-1;

        double paginas = 0;
        double s = e.getPlayers().size();
        int slots;

        if(s <= 9) {
            slots = 9;
        } else if(s <= 9*2) {
            slots = 9*2;
        } else if(s <= 9*3) {
            slots = 9*3;
        } else if(s <= 9*4) {
            slots = 9*4;
        } else if(s <= 9*5) {
            slots = 9*5;
        } else {
            slots = 9*6;
            paginas = s/9*5;
        }

        Inventory inv = Bukkit.createInventory(null,slots,"§0Lista de Jogadores");

        if(paginas == 0) {
            e.getPlayers().forEach(p -> {
                ItemStack i = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta m = (SkullMeta) i.getItemMeta();

                assert m != null;

                m.setDisplayName(p.getDisplayName());
                m.setLore(Arrays.asList("§7Vida: §f" + p.getHealthScale()+"%",
                        "§7","§7Clique esquerdo para teletransportar","§7Clique direito para §cbanir§7!"));

                i.setItemMeta(m);
                inv.addItem(i);
            });
            return inv;
        } else {
            e.getPlayers().stream().skip((9*5)*pag).limit(9*5).collect(Collectors.toList()).forEach(p -> {
                ItemStack i = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta m = (SkullMeta) i.getItemMeta();

                assert m != null;

                m.setDisplayName(p.getDisplayName());
                m.setLore(Arrays.asList("§7Vida: §f" + p.getHealthScale()+"%",
                        "§7","§7Clique esquerdo para teletransportar","§7Clique direito para §cbanir§7!"));

                i.setItemMeta(m);
                inv.addItem(i);
            });
            if(pag != 0)
                inv.setItem(45,Itens.getItem(Material.ARROW,"§7Página "+Integer.parseInt(String.valueOf(pag))));

            inv.setItem(53,Itens.getItem(Material.ARROW,"§7Página "+Integer.parseInt(String.valueOf(pag+2))));
        }
        return inv;
    }

    public static void clearHotbar(Player p) {
        p.getInventory().setItem(0,new ItemStack(Material.AIR));
        p.getInventory().setItem(4,new ItemStack(Material.AIR));
        p.getInventory().setItem(8,new ItemStack(Material.AIR));
    }
}
