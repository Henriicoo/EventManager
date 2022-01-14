package com.henriquenapimo1.eventmanager.commands.chat.enquete;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Enquete;

public class EnqueteFinalizarCommand {

    public EnqueteFinalizarCommand(CmdContext ctx) {
        Enquete e = Main.getMain().enquete;

        if(e == null) {
            ctx.reply("enquete.no-enquete", CmdContext.CommandType.ENQUETE);
            return;
        }

        if(!e.hasStarted()) {
            ctx.reply("enquete.cancel", CmdContext.CommandType.ENQUETE);
            Main.getMain().enquete = null;
            return;
        }

        if(ctx.getArgs().length >= 2) {
            if(ctx.getArg(1).equalsIgnoreCase("-s")) {
                ctx.reply("enquete.finalizar", CmdContext.CommandType.ENQUETE);
                e.finalizar(true);
            } else {
                ctx.reply("utils.args", CmdContext.CommandType.ENQUETE,"/enquete finalizar <-s>");
            }
            return;
        }

        e.finalizar(false);
    }
}
