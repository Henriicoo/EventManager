package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.CmdContext;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DarItemCommand {

    public DarItemCommand(CmdContext ctx) {
        Evento evento = Main.getMain().getEvento();

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        ItemStack item = ctx.getSender().getInventory().getItemInMainHand();
        if(item.getType().equals(Material.AIR)) {
            ctx.reply("§7Não há nenhum item selecionado na sua mão principal!");
        } else {
            String aviso = "";

            if(item.getType().equals(Material.POTION)) {
                aviso = "\n§eDica: §7aplique um efeito de poção em todos usando §f/evento darefeito§7 com a poção na mão!";
            }

            evento.darItem(item);
            ctx.reply("§7Item dado com sucesso!" + aviso);
        }
    }
}
