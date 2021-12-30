package com.henriquenapimo1.eventmanager.commands.chat.bolao;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class BolaoFinalizarCommand {

    public BolaoFinalizarCommand(CmdContext ctx) {
        if(Main.getMain().bolao == null) {
            ctx.reply("bolao.no-bolao", CmdContext.CommandType.BOLAO);
            return;
        }

        Main.getMain().bolao.finalizar();
        ctx.reply("bolao.finalizar", CmdContext.CommandType.BOLAO);
    }
}
