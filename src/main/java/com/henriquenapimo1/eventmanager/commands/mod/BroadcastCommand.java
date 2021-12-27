package com.henriquenapimo1.eventmanager.commands.mod;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.CmdContext;

public class BroadcastCommand {

    public BroadcastCommand(CmdContext ctx) {

        Evento evento = Main.getMain().getEvento();

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
