package com.henriquenapimo1.eventmanager.commands.chat.quiz;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Quiz;

public class QuizRespostaCommand {

    public QuizRespostaCommand(CmdContext ctx) {

        Quiz q = Main.getMain().quiz;

        if(q == null) {
            ctx.reply("§7Não há nenhum quiz acontecendo no momento!");
            return;
        }

        if(!ctx.getSender().hasPermission("eventmanager.quiz.responder")) {
            ctx.reply("§cVocê não tem permissão para responder o quiz!");
            return;
        }

        if(ctx.getSender().hasPermission("eventmanager.staff") || ctx.getSender().hasPermission("eventmanager.mod") || ctx.getSender().hasPermission("eventmanager.admin")) {
            ctx.reply("§7Você é um staff, portanto, não pode responder o quiz!");
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("§7Você precisa colocar uma resposta válida! §f/quiz responder [resposta]");
            return;
        }

        String resp = String.join(" ",ctx.getArgs())
                .replace("resposta ","")
                .replaceAll(".$", "");

        if(resp.equalsIgnoreCase(q.getResposta())) {
            q.finalizar(ctx.getSender());
        } else {
            ctx.reply("§cResposta incorreta! Tente novamente");
        }
    }
}
