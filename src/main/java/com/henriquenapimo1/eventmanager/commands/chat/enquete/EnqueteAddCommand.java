package com.henriquenapimo1.eventmanager.commands.chat.enquete;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Enquete;

public class EnqueteAddCommand {

    public EnqueteAddCommand(CmdContext ctx) {
        Enquete e = Main.getMain().enquete;

        if(e == null) {
            ctx.reply("enquete.no-enquete", CmdContext.CommandType.ENQUETE);
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("utils.args", CmdContext.CommandType.ENQUETE,"/enquete add [opção]");
            return;
        }

        if(e.addAlternativa(String.join(" ", ctx.getArgs()).replace("add ",""))) {
            ctx.reply("enquete.add.success", CmdContext.CommandType.ENQUETE);
        } else {
            ctx.reply("enquete.add.error", CmdContext.CommandType.ENQUETE);
        }
    }
}
