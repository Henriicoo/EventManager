package com.henriquenapimo1.eventmanager.utils.gui;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Itens {

    public static ItemStack getItem(Material material, String name, String... lore) {
        ItemStack i = new ItemStack(material);
        ItemMeta m = i.getItemMeta();

        assert m != null;

        m.setDisplayName(name);

        if(lore != null)
        m.setLore(Arrays.asList(lore));

        i.setItemMeta(m);

        return i;
    }

    public static ItemStack getBoolItem(boolean atual) {
        Material mt;
        String nm;
        String lr;

        if(atual) {
            mt = Material.GREEN_DYE;
            nm = "§aAtivo";
            lr = "§7Clique para desativar";
        } else {
          mt = Material.RED_DYE;
          nm = "§cDesativado";
          lr = "§7Clique para ativar";
        }

        ItemStack i = new ItemStack(mt);
        ItemMeta m = i.getItemMeta();

        assert m != null;

        m.setDisplayName(nm);
        m.setLore(Collections.singletonList(lr));

        i.setItemMeta(m);
        return i;
    }

    public static ItemStack getGamemode() {
        Evento e = Main.getMain().getEvento();

        Material mt;
        List<String> lr = new ArrayList<>(Arrays.asList("§8Opções:","   §7Survival", "   §bCreative", "   §3Adventure", "   §9Spectator","§8Clique para alterar"));

        switch (e.getFlags().getGamemode()) {
            case SURVIVAL: {
                mt = Material.LIGHT_GRAY_DYE;
                lr.set(1,"§7→ Survival");
            } break;
            case CREATIVE: {
                mt = Material.LIGHT_BLUE_DYE;
                lr.set(2,"§b→ Creative");
            } break;
            case ADVENTURE: {
                mt = Material.CYAN_DYE;
                lr.set(3,"§3→ Adventure");
            } break;
            case SPECTATOR: {
                mt = Material.BLUE_DYE;
                lr.set(4,"§9→ Spectator");
            } break;
            default:
                throw new IllegalStateException("Unexpected value: " + e.getFlags().getGamemode());
        }

        ItemStack i = new ItemStack(mt);
        ItemMeta m = i.getItemMeta();

        assert m != null;

        m.setDisplayName("§aAlterar Gamemode");
        m.setLore(lr);

        i.setItemMeta(m);
        return i;
    }
}
