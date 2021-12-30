package com.henriquenapimo1.eventmanager.commands.chat.loteria;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Loteria;

public class LoteriaApostarCommand {

    public LoteriaApostarCommand(CmdContext ctx) {
        Loteria l = Main.getMain().loteria;

        if(l == null) {
            ctx.reply("loteria.no-loteria", CmdContext.CommandType.LOTERIA);
            return;
        }

        if(!ctx.getSender().hasPermission("eventmanager.loteria.apostar")) {
            ctx.reply("no-permission", CmdContext.CommandType.LOTERIA,"eventmanager.loteria.apostar");
            return;
        }

        if(ctx.getSender().hasPermission("eventmanager.staff") || ctx.getSender().hasPermission("eventmanager.mod") || ctx.getSender().hasPermission("eventmanager.admin")) {
            ctx.reply("loteria.apostar.staff", CmdContext.CommandType.LOTERIA);
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("args", CmdContext.CommandType.LOTERIA,"/loteria apostar [número]");
            return;
        }

        int i;

        try {
            i = Integer.parseInt(ctx.getArg(ctx.getArgs().length-1));
        } catch (Exception e) {
            ctx.reply("not-number", CmdContext.CommandType.LOTERIA,"aposta");
            return;
        }

        if(l.apostar(ctx.getSender(),i)) {
            if(l.getMaxApostas() != -1) {
                int a = l.getMaxApostas()-l.getApostas(ctx.getSender());
                if(a == 0) {
                    ctx.reply("loteria.apostar.error.no-chances", CmdContext.CommandType.LOTERIA);
                } else {
                    ctx.reply("loteria.apostar.error.has-hances", CmdContext.CommandType.LOTERIA,String.valueOf(a));
                }
            } else {
                ctx.reply("loteria.apostar.error.simple", CmdContext.CommandType.LOTERIA);
            }
        }
    }
}
