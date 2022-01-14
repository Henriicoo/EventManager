package com.henriquenapimo1.eventmanager.utils;

import com.henriquenapimo1.eventmanager.Main;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

public class AutoUpdater {

    private final String version;
    private final Main plugin;
    private final Logger log;

    public AutoUpdater(Main m) {
        plugin = m;
        version = m.getDescription().getVersion();
        log = m.getLogger();

        checkPluginUpdate();
        checkConfigUpdate();
    }

    private void checkPluginUpdate() {
        try {
            log.info("[EventManager] Procurando por uma nova versão...");
            URL url = new URL("https://raw.githubusercontent.com/HenriqueNapimo1/EventManager/main/version");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            String newVer = br.readLine();
            if(version.equalsIgnoreCase(newVer)) {
               log.info("[EventManager] Você está usando a versão mais atualizada do plugin!");
            } else {
                log.info("[EventManager] Há uma nova versão disponível para update!");
            }
            br.close();
        } catch (IOException e) {
            log.severe("[EventManager] Erro ao tentar procurar por uma nova versão!");
        }
    }

    private void checkConfigUpdate() {
        String confVersion = Utils.getString("config-version");
        log.info("[EventManager] Você está usando o arquivo de configuração config.yml com uma versão desatualizada. Atualizando o arquivo...");
        try {
            if (!confVersion.equalsIgnoreCase(version)) {
                CustomMessages.messageUpdate();
                configUpdate();
                log.info("[EventManager] Todas as configurações foram atualizadas com sucesso!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.severe("[EventManager] Erro ao tentar atualizar as configurações! Por favor, reporte este bug.");
        }
    }

    private void configUpdate() throws IOException {
        boolean change = false;
        FileConfiguration config = plugin.getConfig();

        Configuration defaults = plugin.getConfig().getDefaults();

        //noinspection ConstantConditions
        for (String defaultKey : defaults.getKeys(true)) {
            if (!config.contains(defaultKey)) {
                config.set(defaultKey, defaults.get(defaultKey));
                config.save(new File(plugin.getDataFolder(),"config.yml"));
                change = true;
            }
        }
        if (change) {
            config.set("config-version",version);
            config.save(new File(plugin.getDataFolder(),"config.yml"));
        }
    }
}
