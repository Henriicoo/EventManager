package com.henriquenapimo1.eventmanager.commands.chat.quiz;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Quiz;

public class QuizRespostaCommand {

    public QuizRespostaCommand(CmdContext ctx) {

        Quiz q = Main.getMain().quiz;

        if(q == null) {
            ctx.reply("quiz.no-quiz", CmdContext.CommandType.QUIZ);
            return;
        }

        if(!ctx.getSender().hasPermission("eventmanager.quiz.responder")) {
            ctx.reply("no-permission", CmdContext.CommandType.QUIZ,"eventmanager.quiz.responder");
            return;
        }

        if(ctx.getSender().hasPermission("eventmanager.staff") || ctx.getSender().hasPermission("eventmanager.mod") || ctx.getSender().hasPermission("eventmanager.admin")) {
            ctx.reply("quiz.resposta.staff", CmdContext.CommandType.QUIZ);
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("args", CmdContext.CommandType.QUIZ,"/quiz responder [resposta]");
            return;
        }

        String resp = String.join(" ",ctx.getArgs())
                .replace("resposta ","")
                .replaceAll(".$", "");

        if(resp.equalsIgnoreCase(q.getResposta())) {
            q.finalizar(ctx.getSender());
        } else {
            ctx.reply("quiz.resposta.incorreta", CmdContext.CommandType.QUIZ);
        }
    }
}
