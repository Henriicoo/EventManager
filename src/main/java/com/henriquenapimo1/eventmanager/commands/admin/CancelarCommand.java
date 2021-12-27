package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CmdContext;
import com.henriquenapimo1.eventmanager.utils.Evento;

public class CancelarCommand {

    public CancelarCommand(CmdContext ctx) {
        Evento evento = Main.getMain().getEvento();

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        evento.broadcast("§cO evento foi cancelado!");
        evento.finalizar();

        ctx.reply("§7Evento cancelado com sucesso!");
    }
}
