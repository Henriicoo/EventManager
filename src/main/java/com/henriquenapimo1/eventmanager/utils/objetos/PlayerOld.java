package com.henriquenapimo1.eventmanager.utils.objetos;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerOld {

    private final Player p;
    private final Location location;
    private final GameMode gm;
    private final ItemStack[] content;
    private final ItemStack[] armor;
    private final boolean fly;

    public PlayerOld(Player player) {
        this.p = player;
        this.location = p.getLocation();
        this.gm = p.getGameMode();
        this.fly = p.isFlying();
        this.content = p.getInventory().getContents();
        this.armor = p.getInventory().getArmorContents();
    }

    public void restaurar() {
        p.getActivePotionEffects().forEach(e -> p.removePotionEffect(e.getType()));
        p.getInventory().clear();

        p.updateInventory();

        p.setFlying(fly);
        p.setGameMode(gm);
        p.teleport(location);

        p.getInventory().setContents(content);
        p.getInventory().setArmorContents(armor);
        p.updateInventory();
    }
}
