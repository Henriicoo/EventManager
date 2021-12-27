package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CmdContext;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.gui.InventoryGUIs;
import org.bukkit.Sound;

public class FlagCommand {

    public FlagCommand(CmdContext ctx) {

        Evento e = Main.getMain().getEvento();

        if(e == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        ctx.getSender().openInventory(InventoryGUIs.getFlagsGUI());
        ctx.getSender().playSound(ctx.getSender().getLocation(), Sound.ENTITY_ITEM_PICKUP,100,1);
    }
}
