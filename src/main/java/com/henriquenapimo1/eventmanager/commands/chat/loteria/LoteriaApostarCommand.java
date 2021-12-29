package com.henriquenapimo1.eventmanager.commands.chat.loteria;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Loteria;

public class LoteriaApostarCommand {

    public LoteriaApostarCommand(CmdContext ctx) {
        Loteria l = Main.getMain().loteria;

        if(l == null) {
            ctx.reply("§7Não há nenhuma loteria acontecendo no momento!");
            return;
        }

        if(!ctx.getSender().hasPermission("eventmanager.loteria.apostar")) {
            ctx.reply("§cVocê não tem permissão para apostar na Loteria!");
            return;
        }

        if(ctx.getSender().hasPermission("eventmanager.staff") || ctx.getSender().hasPermission("eventmanager.mod") || ctx.getSender().hasPermission("eventmanager.admin")) {
            ctx.reply("§7Você é um staff, portanto, não pode apostar na loteria!");
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("§cErro! Você precisa apostar em um número. /loteria apostar [número]");
            return;
        }

        int i;

        try {
            i = Integer.parseInt(ctx.getArg(ctx.getArgs().length-1));
        } catch (Exception e) {
            ctx.reply("§cErro! Você precisa apostar em um número. /loteria apostar [número]");
            return;
        }

        if(l.apostar(ctx.getSender(),i)) {
            if(l.getMaxApostas() != -1) {
                int a = l.getMaxApostas()-l.getApostas(ctx.getSender());
                if(a == 0) {
                    ctx.reply("§7Não foi dessa vez! Você não tem mais chances para apostar.");
                } else {
                    ctx.reply("§7Não foi dessa vez! Tente novamente. (Você ainda tem " + a + " chances)");
                }
            } else {
                ctx.reply("§7Não foi dessa vez! Tente novamente");
            }
        }
    }
}
