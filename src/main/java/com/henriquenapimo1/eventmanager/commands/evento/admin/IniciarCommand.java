package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;

public class IniciarCommand {

    public IniciarCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        evento.lockEvent(true);
        evento.teleportAll(evento.getSpawn());
        evento.getPlayers().forEach(p -> p.sendTitle(
                CustomMessages.getString("commands.evento.iniciar.title"),
                CustomMessages.getString("commands.evento.iniciar.subtitle"),5,50,5));

        evento.broadcastStaff(
                CustomMessages.getString("commands.evento.iniciar.success"));
    }
}
