package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Evento;
import com.henriquenapimo1.eventmanager.utils.gui.InventoryGUIs;
import org.bukkit.Sound;

public class FlagCommand {

    public FlagCommand(CmdContext ctx) {

        Evento e = Main.getMain().evento;

        if(e == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        ctx.getSender().openInventory(InventoryGUIs.getFlagsGUI());
        ctx.getSender().playSound(ctx.getSender().getLocation(), Sound.ENTITY_ITEM_PICKUP,100,1);
    }
}
