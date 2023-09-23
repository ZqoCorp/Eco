package com.zqo.eco.listeners;

import com.zqo.eco.Eco;
import com.zqo.eco.data.EcoPlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinListener implements Listener
{
    private final Eco eco = Eco.getEco();

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final String name = player.getName();
        final int defaultBalance = (int) eco.getConfigManager().get("Balance.default_balance");
        final EcoPlayerData ecoPlayerData = new EcoPlayerData(name, player.getUniqueId(), defaultBalance);

        eco.getPlayerData().put(name, ecoPlayerData);
    }
}
