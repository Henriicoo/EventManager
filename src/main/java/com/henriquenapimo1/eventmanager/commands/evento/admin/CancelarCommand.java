package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;

public class CancelarCommand {

    public CancelarCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        evento.broadcast("§cO evento foi cancelado!");
        evento.finalizar();

        ctx.reply("§7Evento cancelado com sucesso!");
    }
}
