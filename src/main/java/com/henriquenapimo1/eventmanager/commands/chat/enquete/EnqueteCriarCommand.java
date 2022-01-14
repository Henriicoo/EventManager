package com.henriquenapimo1.eventmanager.commands.chat.enquete;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Enquete;

public class EnqueteCriarCommand {

    public EnqueteCriarCommand(CmdContext ctx) {
        if(Main.getMain().enquete != null) {
            ctx.reply("enquete.criar.error", CmdContext.CommandType.ENQUETE);
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("utils.args", CmdContext.CommandType.ENQUETE,"/enquete criar [título]");
            return;
        }

        Main.getMain().enquete = new Enquete(String.join(" ", ctx.getArgs())
                .replace("criar ",""));
        ctx.reply("enquete.criar.success", CmdContext.CommandType.ENQUETE);
    }
}
