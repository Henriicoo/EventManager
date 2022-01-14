package com.henriquenapimo1.eventmanager.commands.chat.enquete;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Enquete;

public class EnqueteIniciarCommand {

    public EnqueteIniciarCommand(CmdContext ctx) {
        Enquete e = Main.getMain().enquete;

        if (e == null) {
            ctx.reply("enquete.no-enquete", CmdContext.CommandType.ENQUETE);
            return;
        }

        if (e.hasStarted()) {
            ctx.reply("enquete.iniciar.error", CmdContext.CommandType.ENQUETE);
            return;
        }

        if (e.iniciar()) {
            ctx.reply("enquete.iniciar.success", CmdContext.CommandType.ENQUETE);
        } else {
            ctx.reply("enquete.iniciar.no-alt", CmdContext.CommandType.ENQUETE);
        }
    }
}
