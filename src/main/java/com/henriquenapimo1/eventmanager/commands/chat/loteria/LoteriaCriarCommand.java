package com.henriquenapimo1.eventmanager.commands.chat.loteria;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.ChatEventManager;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class LoteriaCriarCommand {

    public LoteriaCriarCommand(CmdContext ctx) {
        if(Main.getMain().loteria != null) {
            ctx.reply("§7Você não pode criar uma loteria enquanto há outra acontecendo!");
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("§7Erro! Uso: /loteria criar [prêmio] [valor/random]");
            return;
        }

        if(ctx.getArgs().length == 2) {
            ctx.reply("§7Erro! Se deseja criar uma loteria com um número aleatório, use /loteria criar [prêmio] random");
            return;
        }

        int premio;

        try {
            premio = Integer.parseInt(ctx.getArg(1));
        } catch (Exception e) {
            ctx.reply("§cErro! Você precisa colocar um número válido como prêmio.");
            return;
        }

        if(ctx.getArg(2).equalsIgnoreCase("random")) {
            ChatEventManager.iniciarLoteria(premio,0);
            ctx.reply("§aVocê iniciou uma loteria com o prêmio em R$"+premio+" e com um número aleatório!");
            return;
        }

        int num;

        try {
            num = Integer.parseInt(ctx.getArg(2));
        } catch (Exception e) {
            ctx.reply("§cErro! Você precisa colocar um número válido ou criar com um número aleatório usando /loteria criar [prêmio] random");
            return;
        }

        ChatEventManager.iniciarLoteria(premio,num);
        ctx.reply("§aVocê iniciou uma loteria com o prêmio em R$"+premio+" e com o número premiado "+num+"!");
    }
}
