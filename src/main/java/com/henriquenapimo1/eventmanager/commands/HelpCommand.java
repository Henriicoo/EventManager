package com.henriquenapimo1.eventmanager.commands;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.commands.chat.bolao.BolaoHelpCommand;
import com.henriquenapimo1.eventmanager.commands.chat.loteria.LoteriaHelpCommand;
import com.henriquenapimo1.eventmanager.commands.chat.quiz.QuizHelpCommand;
import com.henriquenapimo1.eventmanager.commands.chat.vouf.VoufHelpCommand;
import com.henriquenapimo1.eventmanager.commands.evento.EventoHelpCommand;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelpCommand {

    public HelpCommand(CmdContext ctx) {
        if(ctx.getArgs().length <= 1) {
            ComponentBuilder b = new ComponentBuilder(Utils.getPref(CmdContext.CommandType.MAIN) + " §6Menu de Ajuda\n");
            b.append("§a/eventmanager help §7- §eMostra o menu de ajuda;\n");
            b.append("§a/eventmanager info §7- §eMostra a versão do plugin;\n");
            b.append("§a/eventmanager config help §7- §eComando para alterar ou ver informações sobre alguma configuração;\n");
            b.append("§a/eventmanager reload §7- §ePara recarregar o plugin e suas configurações;\n");

            b.append("§7▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n");
            b.append("§7Clique em um nome para ver o menu de ajuda\n");

            b.append(new ComponentBuilder(Utils.getPref(CmdContext.CommandType.EVENTO) + " ")
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Clique para ver ajuda sobre os Eventos")))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/eventmanager help evento"))
                    .create());
            b.append(new ComponentBuilder(Utils.getPref(CmdContext.CommandType.QUIZ) + " ")
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Clique para ver ajuda sobre os Quizzes")))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/eventmanager help quiz"))
                    .create());
            b.append(new ComponentBuilder(Utils.getPref(CmdContext.CommandType.VOUF) + " ")
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Clique para ver ajuda sobre os VouFs")))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/eventmanager help vouf"))
                    .create());
            b.append(new ComponentBuilder(Utils.getPref(CmdContext.CommandType.BOLAO) + " ")
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Clique para ver ajuda sobre os Bolões")))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/eventmanager help bolao"))
                    .create());
            b.append(new ComponentBuilder(Utils.getPref(CmdContext.CommandType.LOTERIA) + " ")
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Clique para ver ajuda sobre a Loteria")))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/eventmanager help loteria"))
                    .create());
            b.append(new ComponentBuilder("§8[§e§lPerms§8]")
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Clique para ver ajuda sobre as Permissões")))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/eventmanager help perms"))
                    .create());

            ctx.getSender().spigot().sendMessage(b.create());
            return;
        }

        List<String> args = new ArrayList<>(Arrays.asList(ctx.getArgs()));
        args.remove(ctx.getArg(1));

        CmdContext ctx2 = new CmdContext(ctx.getSender(),ctx.getCommand(), args.toArray(new String[0]));

        switch (ctx.getArg(1)) {
            case "evento": new EventoHelpCommand(ctx2); break;
            case "quiz": new QuizHelpCommand(ctx2); break;
            case "vouf": new VoufHelpCommand(ctx2); break;
            case "bolao": new BolaoHelpCommand(ctx2); break;
            case "loteria": new LoteriaHelpCommand(ctx2); break;
            case "perms": {
                if(ctx.getSender().hasPermission("eventmanager.staff")) {
                    ctx.reply("utils.no-permission", CmdContext.CommandType.MAIN,"eventmanager.staff");
                    return;
                }
                ComponentBuilder cb = new ComponentBuilder(Utils.getPref(CmdContext.CommandType.MAIN) + " §6§lLista de permissões");

                Main.getMain().getDescription().getPermissions().forEach(p ->
                        cb.append(String.format("§8- §7%s §f| §f%s;\n",p.getName(),p.getDescription())));

                cb.append("§7Para ter mais informações, ");
                cb.append(new ComponentBuilder("§a[Clique aqui]")
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§7Clique para abrir a wiki do plugin")))
                        .event(new ClickEvent(ClickEvent.Action.OPEN_URL,
                                "https://github.com/HenriqueNapimo1/EventManager/wiki/Lista-de-Comandos-e-Permissões"))
                        .create());

                ctx.getSender().spigot().sendMessage(cb.create());
            } break;
            default: ctx.replyText("§7Argumento inválido!", CmdContext.CommandType.MAIN); break;
        }
    }
}
