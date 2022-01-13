package com.henriquenapimo1.eventmanager.commands.chat.loteria;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Loteria;

public class LoteriaFinalizarCommand {

    public LoteriaFinalizarCommand(CmdContext ctx) {
        Loteria l = Main.getMain().loteria;

        if(l == null) {
            ctx.reply("loteria.no-loteria", CmdContext.CommandType.LOTERIA);
            return;
        }

        l.finalizar(null);
        ctx.reply("loteria.cancel", CmdContext.CommandType.LOTERIA);
    }
}
