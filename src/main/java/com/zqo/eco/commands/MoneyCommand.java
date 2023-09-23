package com.zqo.eco.commands;

import com.zqo.eco.Eco;
import com.zqo.eco.data.EcoPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public final class MoneyCommand extends BukkitCommand
{
    private final Eco eco = Eco.getEco();

    public MoneyCommand()
    {
        super("money");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args)
    {
        if (sender instanceof Player player) {
            final String currency = (String) eco.getConfigManager().get("Balance.currency");

            if (args.length > 0) {
                final Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    return false;
                }

                final EcoPlayerData ecoTargetData = eco.getPlayerData().get(target.getName());

                player.sendMessage("Voici l'argent de " + target.getName() + ": " + ecoTargetData.getMoney() + currency);
                return true;
            }
            final EcoPlayerData ecoPlayerData = eco.getPlayerData().get(player.getName());

            player.sendMessage("Voici votre argent: " + ecoPlayerData.getMoney() + currency);
        }

        return false;
    }
}
