package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class SetSpawnCommand {

    public SetSpawnCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        ctx.reply("§7Spawn do evento setado para a sua localização atual!");
        evento.setSpawn(ctx.getSender().getLocation());
    }
}
