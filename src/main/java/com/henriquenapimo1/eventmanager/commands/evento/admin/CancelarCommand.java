package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Evento;

public class CancelarCommand {

    public CancelarCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        evento.broadcast(CustomMessages.getString("commands.evento.cancelar.broadcast"));
        evento.finalizar();

        ctx.reply("evento.cancelar.success", CmdContext.CommandType.EVENTO);
    }
}
