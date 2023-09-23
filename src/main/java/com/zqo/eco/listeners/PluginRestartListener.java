package com.zqo.eco.listeners;

import com.zqo.eco.Eco;
import com.zqo.eco.data.EcoPlayerData;
import com.zqo.eco.misc.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import java.math.BigDecimal;

public final class PluginRestartListener implements Listener
{
    private final Eco eco = Eco.getEco();

    @EventHandler
    public void onLoadPlugin(final PluginEnableEvent event)
    {
        final double defaultBalance = (double) eco.getConfigManager().get("Balance.default_balance");
        final BigDecimal defaultB = NumberUtils.parseBigDecimal(String.valueOf(defaultBalance));

        for (Player player : Bukkit.getOnlinePlayers()) {
            eco.getPlayerData().put(player, new EcoPlayerData(player, defaultB));
        }
    }
}
