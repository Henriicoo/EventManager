package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import com.henriquenapimo1.eventmanager.utils.Utils;

public class SetPremioCommand {

    public SetPremioCommand(CmdContext ctx) {

        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("§cErro! Argumento requerido: §7/evento setpremio [prêmio]");
            return;
        }

        int i;
        try {
            i = Integer.parseInt(ctx.getArg(1));
        } catch (Exception e) {
            ctx.reply("§cErro! Você precisa colocar um valor válido como prêmio.");
            return;
        }

        if(i > Utils.getInt("max-premio")) {
            ctx.reply("§cErro! O prêmio excede o valor máximo ("+Utils.getInt("max-premio")+")");
            return;
        }

        evento.prize = i;
        ctx.reply("§aPrêmio configurado para R$" + i + " com sucesso!");
    }
}
