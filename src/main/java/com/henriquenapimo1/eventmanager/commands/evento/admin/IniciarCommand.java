package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;

public class IniciarCommand {

    public IniciarCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        evento.lockEvent(true);
        evento.teleportAll(evento.getSpawn());
        evento.getPlayers().forEach(p -> p.sendTitle("§6Evento Iniciado!","§eBom jogo",5,50,5));
        ctx.reply("§aEvento iniciado!");
    }
}
