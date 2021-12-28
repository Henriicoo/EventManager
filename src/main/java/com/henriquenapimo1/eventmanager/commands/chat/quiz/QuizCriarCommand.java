package com.henriquenapimo1.eventmanager.commands.chat.quiz;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Quiz;
import com.henriquenapimo1.eventmanager.utils.Utils;

public class QuizCriarCommand {

    public QuizCriarCommand(CmdContext ctx) {
        if(ctx.getArgs().length < 3) {
            ctx.reply("§cErro! Argumentos requeridos: §7/quiz criar [pergunta] [prêmio]");
            return;
        }

        if(Main.getMain().quiz != null) {
            ctx.reply("§cErro! Você não pode criar um quiz enquanto já tem um acontecendo.");
            return;
        }

        int i;

        try {
            i = Integer.parseInt(ctx.getArg(ctx.getArgs().length-1));
        } catch (Exception e) {
            ctx.reply("§cErro! Você precisa colocar um valor como prêmio.");
            return;
        }

        if(i > Utils.getInt("max-premio-quiz")) {
            ctx.reply("§cErro! O prêmio excede o valor máximo ("+Utils.getInt("max-premio-quiz")+")");
            return;
        }

        String pergunta = String.join(" ",ctx.getArgs())
                .replace(String.valueOf(i),"")
                .replace("criar ","")
                .replaceAll(".$", "");

        Main.getMain().quiz = new Quiz(pergunta,i);
        ctx.reply("§aQuiz criado com sucesso! Agora use /quiz setresposta [resposta] para setar a resposta do quiz e iniciá-lo!");
    }
}
