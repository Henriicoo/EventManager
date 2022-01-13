package com.henriquenapimo1.eventmanager.utils.objetos.events;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Quiz {

    private final String pergunta;
    private String resposta;
    private final int premio;

    private int taskId;

    public Quiz(String pergunta, int premio) {
        this.pergunta = pergunta;
        this.premio = premio;
    }

    public int getPremio() {
        return premio;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void start(String resposta) {
        this.resposta = resposta;
        anunciar();
    }

    public String getResposta() {
        return resposta;
    }

    public void anunciar() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), () ->
                Bukkit.getOnlinePlayers().forEach(p -> p.spigot().sendMessage(Utils.getAnuncio(this).create())),
                0,Utils.getInt("anunciar-quiz")*20L);
    }

    public void finalizar(Player ganhador) {
        Bukkit.getScheduler().cancelTask(taskId);

        if(ganhador == null) {
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Utils.getPref(CmdContext.CommandType.QUIZ) + " " +
                    CustomMessages.getString("events.quiz.cancel",resposta)));
            return;
        }
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Utils.getPref(CmdContext.CommandType.QUIZ) + " " +
                        CustomMessages.getString("events.quiz.ganhador",ganhador.getName(),resposta)));

        ganhador.sendMessage(CustomMessages.getString("commands.quiz.resposta.success", String.valueOf(premio)));
        Main.getEconomy().depositPlayer(ganhador,premio);

        Utils.spawnFirework(ganhador,5);


        Main.getMain().quiz = null;
    }
}
