package com.henriquenapimo1.eventmanager.commands.chat.vouf;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Vouf;

public class VoufRespostaCommand {

    public VoufRespostaCommand(CmdContext ctx) {
        Vouf v = Main.getMain().vouf;

        if(v == null) {
            ctx.reply("vouf.no-vouf", CmdContext.CommandType.VOUF);
            return;
        }

        if(!ctx.getSender().hasPermission("eventmanager.vouf.responder")) {
            ctx.reply("utils.no-permission", CmdContext.CommandType.VOUF,"eventmanager.vouf.responder");
            return;
        }

        if(ctx.getSender().hasPermission("eventmanager.staff") || ctx.getSender().hasPermission("eventmanager.mod") || ctx.getSender().hasPermission("eventmanager.admin")) {
            ctx.reply("vouf.resposta.staff", CmdContext.CommandType.VOUF);
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("utils.args", CmdContext.CommandType.VOUF,"/vouf resposta [true/false]");
            return;
        }

        switch (ctx.getArg(1).toLowerCase()) {
            case "true": v.addPlayer(ctx.getSender(),true);break;
            case "false": v.addPlayer(ctx.getSender(),false);break;
            default: {
                ctx.reply("utils.args", CmdContext.CommandType.VOUF,"/vouf resposta [true/false]");
            } break;
        }
    }
}
