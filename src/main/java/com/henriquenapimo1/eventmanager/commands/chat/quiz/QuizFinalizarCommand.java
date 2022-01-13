package com.henriquenapimo1.eventmanager.commands.chat.quiz;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Quiz;

public class QuizFinalizarCommand {

    public QuizFinalizarCommand(CmdContext ctx) {
        Quiz q = Main.getMain().quiz;

        if(q == null) {
            ctx.reply("quiz.no-quiz", CmdContext.CommandType.QUIZ);
            return;
        }

        q.finalizar(null);
        ctx.reply("quiz.cancel", CmdContext.CommandType.QUIZ);
    }
}
