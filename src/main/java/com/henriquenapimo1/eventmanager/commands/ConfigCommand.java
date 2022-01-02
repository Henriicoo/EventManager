package com.henriquenapimo1.eventmanager.commands;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigCommand {

    private final CmdContext ctx;

    public ConfigCommand(CmdContext ctx) {
        this.ctx = ctx;
        if(ctx.getArgs().length < 2) {
            help();
            return;
        }

        switch (ctx.getArg(1)) {
            case "list": {
                list();
            } break;
            case "info": {
                info();
            } break;
            case "set": {
                set();
            } break;
            default: {
                help();
            } break;
        }
    }

    private void help() {
        ComponentBuilder b = new ComponentBuilder(Utils.getPref(CmdContext.CommandType.MAIN) + " §6Menu de Ajuda - Config\n");
        b.append("§a/eventmanager config help §7- §eMostra o menu de ajuda;\n");
        b.append("§a/eventmanager config list §7- §eMostra uma lista com todas as configurações;\n");
        b.append("§a/eventmanager config info [config] §7- §eVer informações sobre alguma configuração;\n");
        b.append("§a/eventmanager config set [convig] [valor] §7- §eAlterar o valor de uma configuração dentro do jogo;\n");

        ctx.getSender().spigot().sendMessage(b.create());
    }

    private void list() {
        Set<String> config = Utils.getConfiguration().keySet();

        List<String> configList = new ArrayList<>(config.size());
        configList.addAll(config);

        ComponentBuilder cb = new ComponentBuilder(Utils.getPref(CmdContext.CommandType.MAIN));
        cb.append(" §6§lLista de Configurações\n");

        for(int i=0; i<config.size(); i++) {
            Object c = Main.getMain().getConfig().get(configList.get(i));
            String color = "§7";

            if(c instanceof Boolean) {
                if((Boolean)c) {
                    color = "§a";
                } else {
                    color = "§c";
                }
            }

            cb.append(new ComponentBuilder(color+configList.get(i) + "§f, ")
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/eventmanager config info "+configList.get(i)))
                    .create());
        }
        cb.append("\n§fClique em uma configuração para ver mais informações sobre\n§f");
        ctx.getSender().spigot().sendMessage(cb.create());
    }

    private void info() {
        if(ctx.getArgs().length < 3) {
            ctx.reply("utils.args", CmdContext.CommandType.MAIN,"/eventmanager config info [configuração]");
            return;
        }

        String config = ctx.getArg(2);
        String desc = Utils.getConfiguration().get(config);
        if(desc == null) {
            ctx.replyText("§cConfiguração não encontrada! Use /eventmanager config list para ver uma lista com todas as configurações", CmdContext.CommandType.MAIN);
            return;
        }

        ctx.replyText(String.format(
                "§7§lConfiguração %s\n" +
                        "§f%s" +
                        "\n§7§lValor atual: %s",
                config,desc,Utils.getString(config)),
                CmdContext.CommandType.MAIN);
    }

    private void set() {
        if(ctx.getArgs().length < 4) {
            ctx.reply("utils.args", CmdContext.CommandType.MAIN,"/eventmanager config set [configuração] [valor]");
            return;
        }

        String configName = ctx.getArg(2);
        if(!Utils.getConfiguration().containsKey(configName)) {
            ctx.replyText("§cConfiguração não encontrada! Use /eventmanager config list para ver uma lista com todas as configurações", CmdContext.CommandType.MAIN);
            return;
        }

        String arg = ctx.getArg(3);
        FileConfiguration config = Main.getMain().getConfig();
        Object obj = config.get(configName);
        assert obj != null;

        String color = "§7";
        if(obj.toString().equalsIgnoreCase("true") || obj.toString().equalsIgnoreCase("false")) {
            if (arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("false")) {
                try {
                    boolean b = Boolean.parseBoolean(arg);

                    config.set(configName, b);
                    Main.getMain().saveConfig();

                    if (b) {
                        color = "§a";
                    } else {
                        color = "§c";
                    }
                    Main.getMain().reloadConfig();

                    ctx.replyText(String.format("§aConfiguração %s §aalterada para %s%s §acom sucesso!",
                            configName, color, arg), CmdContext.CommandType.MAIN);
                } catch (Exception e) {
                    ctx.replyText("§cValor inválido! Use true/false", CmdContext.CommandType.MAIN);
                }
            } else {
                ctx.replyText("§cValor inválido! Use true/false", CmdContext.CommandType.MAIN);
            }
            return;
        }
        if(obj instanceof Integer) {
            try {
                int i = Integer.parseInt(arg);
                config.set(configName,i);
                Main.getMain().saveConfig();

                Main.getMain().reloadConfig();

                ctx.replyText(String.format("§aConfiguração %s §aalterada para %s%s §acom sucesso!",
                        configName,color,arg), CmdContext.CommandType.MAIN);
            } catch (NumberFormatException e) {
                ctx.replyText("§cValor inválido! Forneça um número!", CmdContext.CommandType.MAIN);

            }
        } else {
            ctx.replyText("§7Você não pode alterar esse valor aqui!", CmdContext.CommandType.MAIN);
        }
    }
}
