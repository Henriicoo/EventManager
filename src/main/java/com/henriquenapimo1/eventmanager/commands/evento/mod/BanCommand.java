package com.henriquenapimo1.eventmanager.commands.evento.mod;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BanCommand {

    public BanCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("utils.args", CmdContext.CommandType.EVENTO,"/evento ban [nick]");
            return;
        }

        Player p = Bukkit.getPlayer(ctx.getArg(1));
        if(p == null) {
            ctx.reply("evento.ban.offline", CmdContext.CommandType.EVENTO);
            return;
        }

        if(evento.getPlayers().contains(p)) {
            ctx.reply("evento.ban.error.not-playing", CmdContext.CommandType.EVENTO,p.getName());
        }

        if(p.hasPermission("eventmanager.mod") || p.hasPermission("eventmanager.admin")) {
            ctx.reply("evento.ban.error.staff", CmdContext.CommandType.EVENTO);
            return;
        }

        evento.banPlayer(p);
    }
}
