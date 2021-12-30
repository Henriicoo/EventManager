package com.henriquenapimo1.eventmanager.commands.chat.loteria;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.ChatEventManager;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class LoteriaCriarCommand {

    public LoteriaCriarCommand(CmdContext ctx) {
        if(Main.getMain().loteria != null) {
            ctx.reply("loteria.criar.error", CmdContext.CommandType.LOTERIA);
            return;
        }

        if(ctx.getArgs().length <= 2) {
            ctx.reply("args", CmdContext.CommandType.LOTERIA,"/loteria criar [prêmio] [valor/random]");
            return;
        }

        int premio;

        try {
            premio = Integer.parseInt(ctx.getArg(1));
        } catch (Exception e) {
            ctx.reply("not-number", CmdContext.CommandType.LOTERIA,"prêmio");
            return;
        }

        if(ctx.getArg(2).equalsIgnoreCase("random")) {
            ChatEventManager.iniciarLoteria(premio,0);
            ctx.reply("loteria.criar.success.random", CmdContext.CommandType.LOTERIA,String.valueOf(premio));
            return;
        }

        int num;

        try {
            num = Integer.parseInt(ctx.getArg(2));
        } catch (Exception e) {
            ctx.reply("not-number", CmdContext.CommandType.LOTERIA,"número premiado");
            return;
        }

        if(num > Utils.getInt("loteria-max-numero")) {
            ctx.reply("max-premio", CmdContext.CommandType.LOTERIA,String.valueOf(Utils.getInt("loteria-max-numero")));
            return;
        }

        ChatEventManager.iniciarLoteria(premio,num);
        ctx.reply("loteria.criar.success.numero", CmdContext.CommandType.LOTERIA,String.valueOf(premio),String.valueOf(num));
    }
}
