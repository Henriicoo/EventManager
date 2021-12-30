package com.henriquenapimo1.eventmanager.commands.evento.mod;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UnbanCommand {

    public UnbanCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("args", CmdContext.CommandType.EVENTO,"/evento unban [nick]");
            return;
        }

        Player p = Bukkit.getPlayer(ctx.getArg(1));
        if(p == null) {
            ctx.reply("evento.ban.offline", CmdContext.CommandType.EVENTO);
            return;
        }

        evento.unbanPlayer(p);
    }
}
