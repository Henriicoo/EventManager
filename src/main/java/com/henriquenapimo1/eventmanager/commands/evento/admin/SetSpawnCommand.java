package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class SetSpawnCommand {

    public SetSpawnCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        ctx.reply("evento.setspawn", CmdContext.CommandType.EVENTO);
        evento.setSpawn(ctx.getSender().getLocation());
    }
}
