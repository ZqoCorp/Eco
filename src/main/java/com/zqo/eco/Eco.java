package com.zqo.eco;

import com.zqo.eco.config.ConfigManager;
import com.zqo.eco.data.EcoPlayerData;
import com.zqo.eco.handlers.CommandHandler;
import com.zqo.eco.handlers.ListenerHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Eco extends JavaPlugin
{
    private static Eco eco;
    private static ConfigManager configManager;
    private static Map<String, EcoPlayerData> playerData;

    @Override
    public void onEnable()
    {
        eco = this;
        configManager = new ConfigManager();

        configManager.init();

        new CommandHandler().register();
        new ListenerHandler().register();

        playerData = new HashMap<>();
    }

    @Override
    public void onDisable()
    {
        configManager.save();
    }

    public static Eco getEco()
    {
        return eco;
    }

    public ConfigManager getConfigManager()
    {
        return configManager;
    }

    public Map<String, EcoPlayerData> getPlayerData()
    {
        return playerData;
    }
}
