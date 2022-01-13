package com.henriquenapimo1.eventmanager.commands.chat.quiz;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Quiz;
import com.henriquenapimo1.eventmanager.utils.Utils;

public class QuizCriarCommand {

    public QuizCriarCommand(CmdContext ctx) {
        if(ctx.getArgs().length < 3) {
            ctx.reply("utils.args", CmdContext.CommandType.QUIZ,"/quiz criar [pergunta] [prêmio]");
            return;
        }

        if(Main.getMain().quiz != null) {
            ctx.reply("quiz.criar.error", CmdContext.CommandType.QUIZ);
            return;
        }

        int i;

        try {
            i = Integer.parseInt(ctx.getArg(ctx.getArgs().length-1));
        } catch (Exception e) {
            ctx.reply("utils.not-number", CmdContext.CommandType.QUIZ,"prêmio");
            return;
        }

        if(i > Utils.getInt("max-premio-quiz")) {
            ctx.reply("utils.max-premio", CmdContext.CommandType.QUIZ,String.valueOf(Utils.getInt("max-premio-quiz")));
            return;
        }

        String pergunta = String.join(" ",ctx.getArgs())
                .replace(String.valueOf(i),"")
                .replace("criar ","");

        Main.getMain().quiz = new Quiz(pergunta,i);
        ctx.reply("quiz.criar.success", CmdContext.CommandType.QUIZ);
    }
}
