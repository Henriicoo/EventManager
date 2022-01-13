package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DarItemCommand {

    public DarItemCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        ItemStack item = ctx.getSender().getInventory().getItemInMainHand();
        if(item.getType().equals(Material.AIR)) {
            ctx.reply("evento.dar.no-item", CmdContext.CommandType.EVENTO);
        } else {
            evento.darItem(item);
            ctx.reply("evento.dar.success", CmdContext.CommandType.EVENTO,"item");
        }
    }
}
