package com.henriquenapimo1.eventmanager.commands.chat.bolao;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.Bolao;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class ApostarCommand {

    public ApostarCommand(CmdContext ctx) {
        Bolao b = Main.getMain().bolao;

        if(b == null) {
            ctx.reply("§7Não há nenhum bolão acontecendo no momento!");
            return;
        }

        if(!ctx.getSender().hasPermission("eventmanager.bolao.apostar")) {
            ctx.reply("§cVocê não tem permissão para apostar no bolão!");
            return;
        }

        if(!Utils.getBool("staff-apostar")) {
            if(ctx.getSender().hasPermission("eventmanager.staff") || ctx.getSender().hasPermission("eventmanager.mod") || ctx.getSender().hasPermission("eventmanager.admin")) {
                ctx.reply("§7Você é um staff, portanto, não pode apostar no bolão!");
                return;
            }
        }

        b.apostar(ctx.getSender());
    }
}
