package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.CmdContext;

public class ItemClearCommand {

    public ItemClearCommand(CmdContext ctx) {
        Evento evento = Main.getMain().getEvento();

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        ctx.reply("§7Limpando todos os itens dos players!");
        evento.itemClear();
    }
}
