package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Evento;
import com.henriquenapimo1.eventmanager.utils.Utils;

public class SetPremioCommand {

    public SetPremioCommand(CmdContext ctx) {

        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("utils.args", CmdContext.CommandType.EVENTO,"/evento setpremio [prêmio]");
            return;
        }

        int i;
        try {
            i = Integer.parseInt(ctx.getArg(1));
        } catch (Exception e) {
            ctx.reply("utils.not-number", CmdContext.CommandType.EVENTO,"prêmio");
            return;
        }

        if(i > Utils.getInt("max-premio-evento")) {
            ctx.reply("utils.max-premio", CmdContext.CommandType.EVENTO,String.valueOf(Utils.getInt("max-premio-evento")));
            return;
        }

        evento.prize = i;
        ctx.reply("evento.setpremio", CmdContext.CommandType.EVENTO,String.valueOf(i));
    }
}
