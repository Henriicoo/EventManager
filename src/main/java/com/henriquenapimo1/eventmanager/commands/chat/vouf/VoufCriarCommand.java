package com.henriquenapimo1.eventmanager.commands.chat.vouf;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Vouf;

public class VoufCriarCommand {

    public VoufCriarCommand(CmdContext ctx) {
        if(ctx.getArgs().length < 3) {
            ctx.reply("§cErro! Argumentos requeridos: §7/vouf criar [pergunta] [prêmio]");
            return;
        }

        if(Main.getMain().vouf != null) {
            ctx.reply("§cErro! Você não pode criar um VouF enquanto já tem um acontecendo.");
            return;
        }

        int i;

        try {
            i = Integer.parseInt(ctx.getArg(ctx.getArgs().length-1));
        } catch (Exception e) {
            ctx.reply("§cErro! Você precisa colocar um valor como prêmio.");
            return;
        }

        if(i > Utils.getInt("max-premio-vouf")) {
            ctx.reply("§cErro! O prêmio excede o valor máximo ("+Utils.getInt("max-premio-vouf")+")");
            return;
        }

        String pergunta = String.join(" ",ctx.getArgs())
                .replace(String.valueOf(i),"")
                .replace("criar ","")
                .replaceAll(".$", "");

        Main.getMain().vouf = new Vouf(pergunta,i);
        ctx.reply("§aVouf criado com sucesso! Para finalizar, use §7/vouf finalizar [true/false]");
    }
}
