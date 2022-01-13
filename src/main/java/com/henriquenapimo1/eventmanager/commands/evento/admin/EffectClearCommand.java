package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class EffectClearCommand {

    public EffectClearCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        ctx.reply("evento.clear", CmdContext.CommandType.EVENTO,"efeitos de poção");
        evento.effectClear();
    }
}
