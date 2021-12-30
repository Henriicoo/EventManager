package com.henriquenapimo1.eventmanager.commands.chat.bolao;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.ChatEventManager;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class BolaoCriarCommand {

    public BolaoCriarCommand(CmdContext ctx) {

        if(Main.getMain().bolao != null) {
            ctx.reply("bolao.criar.error", CmdContext.CommandType.BOLAO);
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("args", CmdContext.CommandType.BOLAO,"/bolao criar [valorInicial]");
            return;
        }

        int i;

        try {
            i = Integer.parseInt(ctx.getArg(1));
        } catch (Exception e) {
            ctx.reply("not-number", CmdContext.CommandType.BOLAO,"valor inicial");
            return;
        }

        ChatEventManager.iniciarBolao(i);
        ctx.reply("bolao.criar.success", CmdContext.CommandType.BOLAO);
    }
}
