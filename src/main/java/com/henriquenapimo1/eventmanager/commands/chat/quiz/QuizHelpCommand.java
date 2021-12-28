package com.henriquenapimo1.eventmanager.commands.chat.quiz;

import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class QuizHelpCommand {

    public QuizHelpCommand(CmdContext ctx) {
        ComponentBuilder b = new ComponentBuilder(Utils.getPref() + " §6§lMenu de ajuda Quiz");
        b.append("\n§a/quiz help §7- §eMostra o menu de ajuda;");
        b.append("\n§a/quiz resposta [resposta] §7- §eResponde um quiz com a resposta inserida;");

        if(ctx.getSender().hasPermission("eventmanager.quiz.criar")) {
            b.append("\n§a§lComandos de Criação§7:");
            b.append("\n§a/quiz criar [pergunta] [prêmio] §7- §eCria um Quiz com a pergunta e o prêmio inseridos;");
            b.append("\n§a/vouf setresposta [resposta] §7- §eSeta a resposta do quiz para a resposta inserida.");
        }

        ctx.getSender().spigot().sendMessage(b.create());
    }
}
