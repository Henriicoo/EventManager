package com.henriquenapimo1.eventmanager.commands.chat.vouf;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Vouf;

public class VoufFinalizarCommand {

    public VoufFinalizarCommand(CmdContext ctx) {
        Vouf v = Main.getMain().vouf;

        if(v == null) {
            ctx.reply("vouf.no-vouf", CmdContext.CommandType.VOUF);
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("args", CmdContext.CommandType.VOUF,"/vouf finalizar [true/false]");
            return;
        }

        switch (ctx.getArg(1).toLowerCase()) {
            case "true": v.finalizar(true); break;
            case "false": v.finalizar(false); break;
            default: {
                ctx.reply("args", CmdContext.CommandType.VOUF,"/vouf finalizar [true/false]");
            } break;
        }
    }
}
