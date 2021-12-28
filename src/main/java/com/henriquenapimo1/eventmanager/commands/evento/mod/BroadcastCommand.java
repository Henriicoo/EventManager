package com.henriquenapimo1.eventmanager.commands.evento.mod;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class BroadcastCommand {

    public BroadcastCommand(CmdContext ctx) {

        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("§cUso: /evento bc [mensagem]");
        }

        String msg = String.join(" ", ctx.getArgs());
        msg = msg.replace("bc","");

        evento.broadcast(msg);
    }
}
