package com.henriquenapimo1.eventmanager.utils.objetos;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Utils;
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
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("§8[§6§lQuiz§8] §7O(a) ganhador(a) do quiz foi: §f" + ganhador.getName() + "§7! Parabéns!"));

        ganhador.sendMessage("§aVocê ganhou o quiz e recebeu R$"+premio);
        Main.getEconomy().depositPlayer(ganhador,premio);

        for (int i=0; i<5; i++) {
            Utils.spawnFirework(ganhador);
        }

        Main.getMain().quiz = null;
    }
}
