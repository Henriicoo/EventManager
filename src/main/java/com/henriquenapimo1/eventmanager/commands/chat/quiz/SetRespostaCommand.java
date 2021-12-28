package com.henriquenapimo1.eventmanager.commands.chat.quiz;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Quiz;

public class SetRespostaCommand {

    public SetRespostaCommand(CmdContext ctx) {
        Quiz quiz = Main.getMain().quiz;

        if(quiz == null) {
            ctx.reply("§7Você precisa primeiro criar um quiz! /quiz criar [pergunta] [prêmio]");
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("§7Você precisa colocar uma resposta válida! §f/quiz setresposta [resposta]");
            return;
        }

        String resp = String.join(" ",ctx.getArgs())
                .replace("setresposta ","")
                .replaceAll(".$", "");

        ctx.reply("§aResposta configurada com sucesso! Iniciando o quiz...");
        quiz.start(resp);
    }
}
