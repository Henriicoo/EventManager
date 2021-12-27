package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.CmdContext;

public class EffectClearCommand {

    public EffectClearCommand(CmdContext ctx) {
        Evento evento = Main.getMain().getEvento();

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        ctx.reply("§7Limpando todos os efeitos de poção dos players!");
        evento.effectClear();
    }
}
