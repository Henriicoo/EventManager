package com.henriquenapimo1.eventmanager.commands.chat.vouf;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Vouf;

public class VoufRespostaCommand {

    public VoufRespostaCommand(CmdContext ctx) {
        Vouf v = Main.getMain().vouf;

        if(v == null) {
            ctx.reply("§7Não há nenhum VouF acontecendo no momento!");
            return;
        }

        if(!ctx.getSender().hasPermission("eventmanager.vouf.responder")) {
            ctx.reply("§cVocê não tem permissão para participar do VouF!");
            return;
        }

        if(ctx.getSender().hasPermission("eventmanager.staff") || ctx.getSender().hasPermission("eventmanager.mod") || ctx.getSender().hasPermission("eventmanager.admin")) {
            ctx.reply("§7Você é um staff, portanto, não pode responder o VouF!");
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("§cErro! Use /vouf resposta [true/false]");
            return;
        }

        switch (ctx.getArg(1).toLowerCase()) {
            case "true": v.addPlayer(ctx.getSender(),true);break;
            case "false": v.addPlayer(ctx.getSender(),false);break;
            default: {
                ctx.reply("§cErro! Use /vouf resposta [true/false]");
            } break;
        }
    }
}
