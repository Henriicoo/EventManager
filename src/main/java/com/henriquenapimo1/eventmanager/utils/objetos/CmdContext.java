package com.henriquenapimo1.eventmanager.utils.objetos;

import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdContext {

    private final Player s;
    private final Command c;
    private final String[] a;

    public CmdContext(CommandSender sender, Command command, String[] args) {
        this.s = (Player) sender;
        this.c = command;
        this.a = args;
    }

    public Command getCommand() {
        return c;
    }

    public Player getSender() {
        return s;
    }

    public String[] getArgs() {
        return a;
    }

    public String getArg(int i) {
        return a[i];
    }

    public void reply(String msg,CommandType tipo,String... args) {
        String pref = Utils.getPref(tipo);
        msg = CustomMessages.getString(msg);

        if(args.length == 1) {
            msg = msg.replace("{0}",args[0]);
        } else if(args.length == 2) {
            msg = msg.replace("{0}",args[0]).replace("{1}",args[1]);
        }

        s.sendMessage(pref+" §f"+msg);
    }
    public void reply(String path,CommandType tipo) {
        String pref = Utils.getPref(tipo);

        path = CustomMessages.getString("commands."+path);

        s.sendMessage(pref+" §f"+path);
    }

    public void replyText(String msg,CommandType tipo) {
        String pref = Utils.getPref(tipo);
        s.sendMessage(pref + " §f" + msg);
    }

    public enum CommandType {
        MAIN, EVENTO, QUIZ, VOUF, BOLAO, LOTERIA
    }
}
