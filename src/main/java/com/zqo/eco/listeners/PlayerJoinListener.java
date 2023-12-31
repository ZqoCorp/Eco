package com.zqo.eco.listeners;

import com.zqo.eco.Eco;
import com.zqo.eco.data.EcoPlayerData;
import com.zqo.eco.misc.NumberUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.math.BigDecimal;

public final class PlayerJoinListener implements Listener
{
    private final Eco eco = Eco.getEco();

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final double defaultBalance = (double) eco.getConfigManager().get("Balance.default_balance");
        final BigDecimal defaultB = NumberUtils.parseBigDecimal(String.valueOf(defaultBalance));

        eco.getPlayerData().put(player, new EcoPlayerData(player, defaultB));
    }
}
