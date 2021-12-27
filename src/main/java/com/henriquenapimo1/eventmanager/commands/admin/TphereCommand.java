package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.CmdContext;

public class TphereCommand {

    public TphereCommand(CmdContext ctx) {
        Evento evento = Main.getMain().getEvento();

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        evento.teleportAll(ctx.getSender().getLocation());
        ctx.reply("§aTodos os players foram teleportados para a sua localização atual!");
    }
}
