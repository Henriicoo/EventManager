package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Evento;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import org.bukkit.Bukkit;

public class TrancarCommand {

    public TrancarCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        if(evento.isLocked()) {
            ctx.reply("evento.trancar.success", CmdContext.CommandType.EVENTO,"destrancado");
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(
                    CustomMessages.getString("prefix.evento") + " " +
                            CustomMessages.getString("commands.evento.trancar.unlock")
            ));
        } else {
            ctx.reply("evento.trancar.success", CmdContext.CommandType.EVENTO,"trancado");
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(
                    CustomMessages.getString("prefix.evento") + " " +
                            CustomMessages.getString("commands.evento.trancar.lock")
            ));
        }

        evento.lockEvent(!evento.isLocked());
    }
}
