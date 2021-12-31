package com.henriquenapimo1.eventmanager.commands.chat.vouf;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Vouf;

public class VoufCriarCommand {

    public VoufCriarCommand(CmdContext ctx) {
        if(ctx.getArgs().length < 3) {
            ctx.reply("utils.args", CmdContext.CommandType.VOUF,"/vouf criar [pergunta] [prêmio]");
            return;
        }

        if(Main.getMain().vouf != null) {
            ctx.reply("vouf.criar.error", CmdContext.CommandType.VOUF);
            return;
        }

        int i;

        try {
            i = Integer.parseInt(ctx.getArg(ctx.getArgs().length-1));
        } catch (Exception e) {
            ctx.reply("utils.not-number", CmdContext.CommandType.VOUF,"prêmio");
            return;
        }

        if(i > Utils.getInt("max-premio-vouf")) {
            ctx.reply("utils.max-premio", CmdContext.CommandType.VOUF,String.valueOf(Utils.getInt("max-premio-vouf")));
            return;
        }

        String pergunta = String.join(" ",ctx.getArgs())
                .replace(String.valueOf(i),"")
                .replace("criar ","")
                .replaceAll(".$", "");

        Main.getMain().vouf = new Vouf(pergunta,i);
        ctx.reply("vouf.criar.success", CmdContext.CommandType.VOUF);
    }
}
