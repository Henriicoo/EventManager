package com.henriquenapimo1.eventmanager.commands.chat.vouf;

import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class VoufHelpCommand {

    public VoufHelpCommand(CmdContext ctx) {
        ComponentBuilder b = new ComponentBuilder(Utils.getPref(CmdContext.CommandType.VOUF) + " §6§lMenu de ajuda VouF");
        b.append("\n§a/vouf help §7- §eMostra o menu de ajuda;");
        b.append("\n§a/vouf resposta [true/false] §7- §eResponde um VouF como verdadeiro ou falso;");

        if(ctx.getSender().hasPermission("eventmanager.vouf.criar")) {
            b.append("\n§a§lComandos de Criação§7:");
            b.append("\n§a/vouf criar [pergunta] [prêmio] §7- §eCria um VouF com a pergunta e o prêmio inseridos;");
            b.append("\n§a/vouf finalizar [true/false] §7- §eFinaliza um VouF com a resposta inserida.");
        }

        ctx.getSender().spigot().sendMessage(b.create());
    }
}
