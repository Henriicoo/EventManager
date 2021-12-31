package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import org.bukkit.GameMode;

public class GamemodeCommand {

    public GamemodeCommand(CmdContext ctx) {
        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("utils.args", CmdContext.CommandType.EVENTO,"/evento gamemode [gamemode]");
            return;
        }

        GameMode gm;

        switch (ctx.getArg(1).toLowerCase()) {
            case "survival":
            case "s":
            case "0": {
                gm = GameMode.SURVIVAL; break;
            }
            case "creative":
            case "criativo":
            case "c":
            case "1": {
                gm = GameMode.CREATIVE; break;
            }
            case "adventure":
            case "aventura":
            case "a":
            case "2": {
                gm = GameMode.ADVENTURE; break;
            }
            case "spectator":
            case "espectador":
            case "e":
            case "3": {
                gm = GameMode.SPECTATOR; break;
            }
            default: {
                ctx.reply("evento.gamemode.error", CmdContext.CommandType.EVENTO);
                return;
            }
        }

        evento.getFlags().setGamemode(gm);
        ctx.reply("evento.gamemode.success", CmdContext.CommandType.EVENTO);
    }
}
