package com.henriquenapimo1.eventmanager.commands.evento.mod;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class BroadcastCommand {

    public BroadcastCommand(CmdContext ctx) {

        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("utils.args", CmdContext.CommandType.EVENTO,"/evento bc [mensagem]");
        }

        String msg = String.join(" ", ctx.getArgs());
        msg = msg.replace("bc","");

        evento.broadcast(msg);
    }
}
