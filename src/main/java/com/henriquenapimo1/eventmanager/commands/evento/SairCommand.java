package com.henriquenapimo1.eventmanager.commands.evento;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class SairCommand {

    public SairCommand(CmdContext ctx) {
        Evento e = Main.getMain().evento;

        if(e == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento.");
            return;
        }

        if(e.getPlayers().contains(ctx.getSender())) {
            ctx.reply("§7Saindo do evento...");
            e.removePlayer(ctx.getSender(),false);

        } else if(e.getSpectators().contains(ctx.getSender())) {
            ctx.reply("§7Saindo do evento...");
            e.removeSpectator(ctx.getSender());
        } else {
            ctx.reply("§7Você não está no evento.");
        }
    }
}
