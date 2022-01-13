package com.henriquenapimo1.eventmanager.commands.evento;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class SairCommand {

    public SairCommand(CmdContext ctx) {
        Evento e = Main.getMain().evento;

        if(e == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        if(e.getPlayers().contains(ctx.getSender())) {
            ctx.reply("evento.sair.success", CmdContext.CommandType.EVENTO);
            e.removePlayer(ctx.getSender(),false);

        } else if(e.getSpectators().contains(ctx.getSender())) {
            ctx.reply("evento.sair.success", CmdContext.CommandType.EVENTO);
            e.removeSpectator(ctx.getSender());
        } else {
            ctx.reply("evento.sair.error", CmdContext.CommandType.EVENTO);
        }
    }
}
