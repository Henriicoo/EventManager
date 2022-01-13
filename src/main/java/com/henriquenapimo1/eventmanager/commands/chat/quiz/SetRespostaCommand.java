package com.henriquenapimo1.eventmanager.commands.chat.quiz;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Quiz;

public class SetRespostaCommand {

    public SetRespostaCommand(CmdContext ctx) {
        Quiz quiz = Main.getMain().quiz;

        if(quiz == null) {
            ctx.reply("quiz.setresposta.error", CmdContext.CommandType.QUIZ);
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("utils.args", CmdContext.CommandType.QUIZ,"/quiz setresposta [resposta]");
            return;
        }

        String resp = String.join(" ",ctx.getArgs())
                .replace("setresposta ","");

        ctx.reply("quiz.setresposta.success", CmdContext.CommandType.QUIZ);
        quiz.start(resp);
    }
}
