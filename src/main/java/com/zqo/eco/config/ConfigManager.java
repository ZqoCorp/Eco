package com.zqo.eco.config;

import com.zqo.eco.Eco;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public final class ConfigManager
{
    private final Eco eco = Eco.getEco();
    private File configFile;
    private FileConfiguration config;

    public void init()
    {
        configFile = new File(eco.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            eco.saveResource("config.yml", false);
        }

        config = eco.getConfig();

        final String categoryDatabase = "Database.";
        config.addDefault(categoryDatabase + "hostname", "");
        config.addDefault(categoryDatabase + "username", "");
        config.addDefault(categoryDatabase + "password", "");
        config.addDefault(categoryDatabase + "port", 0);

        final String categoryBalance = "Balance.";
        config.addDefault(categoryBalance + "currency", "$");
        config.addDefault(categoryBalance + "default_balance", 1000.0);

        config.options().copyDefaults(true);
        eco.saveConfig();
    }

    public Object get(final String key)
    {
        config = eco.getConfig();

            if (config.contains(key)) {
                if (config.isString(key)) {
                    return config.getString(key);
                } else if (config.isInt(key)) {
                    return config.getInt(key);
                } else if (config.isDouble(key)) {
                    return config.getDouble(key);
                } else if (config.isBoolean(key)) {
                    return config.getBoolean(key);
                } else {
                    config.get(key);
                }
        }
        return null;
    }

    public void save()
    {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
