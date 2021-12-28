package com.henriquenapimo1.eventmanager.utils.objetos;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Flags {

    private final Evento e;

    private GameMode gm = GameMode.ADVENTURE;
    private boolean pvp = false;
    private boolean invul = true;
    private boolean respawn = false;
    private boolean fly = false;

    public Flags(Evento evento) {
        this.e = evento;
    }

    public void addPlayerFlags(Player p) {
        p.setGameMode(gm);
        p.setFlying(false);
        p.setAllowFlight(fly);
    }

    public GameMode getGamemode() {
        return gm;
    }

    public void setGamemode(GameMode gm) {
        this.gm = gm;
        e.setGamemode(gm);
    }

    public void changeGamemode() {
        switch (gm) {
            case SURVIVAL: gm = GameMode.CREATIVE; break;
            case CREATIVE: gm = GameMode.ADVENTURE; break;
            case ADVENTURE: gm = GameMode.SPECTATOR; break;
            case SPECTATOR: gm = GameMode.SURVIVAL; break;
        }
    }

    public boolean getPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    public boolean getInvul() {
        return invul;
    }

    public void setInvul(boolean inv) {
        this.invul = inv;
    }

    public boolean getRespawn() {
        return respawn;
    }

    public void setRespawn(boolean resp) {
        this.respawn = resp;
    }

    public boolean getFly() {
        return fly;
    }

    public void setFly(boolean fly) {
        this.fly = fly;
        e.getPlayers().forEach(p -> p.setAllowFlight(fly));
    }

}
