package com.henriquenapimo1.eventmanager.commands.chat.enquete;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Enquete;

public class EnqueteVotarCommand {

    public EnqueteVotarCommand(CmdContext ctx) {
        Enquete e = Main.getMain().enquete;

        if(e == null || !e.hasStarted()) {
            ctx.reply("enquete.no-enquete", CmdContext.CommandType.ENQUETE);
            return;
        }

        if(!ctx.getSender().hasPermission("eventmanager.enquete.votar")) {
            ctx.reply("utils.no-permission", CmdContext.CommandType.ENQUETE,"eventmanager.enquete.votar");
            return;
        }

        if(ctx.getArgs().length != 2 || ctx.getArg(1).toCharArray().length > 1) {
            ctx.reply("utils.args", CmdContext.CommandType.ENQUETE,"/enquete votar [a/b/c/d]");
            return;
        }

        char c = ctx.getArg(1).charAt(0);

        if(e.getAlternativas().contains(String.valueOf(c))) {
            if(e.vote(ctx.getSender(), String.valueOf(c))) {
                ctx.reply("enquete.votar.success", CmdContext.CommandType.ENQUETE);
            } else {
                ctx.reply("enquete.votar.error", CmdContext.CommandType.ENQUETE);
            }
        } else {
            ctx.reply("utils.args", CmdContext.CommandType.ENQUETE,"/enquete votar [a/b/c/d]");
        }
    }
}
