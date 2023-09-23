package com.zqo.eco.commands;

import com.zqo.eco.Eco;
import com.zqo.eco.data.EcoPlayerData;
import com.zqo.eco.misc.NumberUtils;
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
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Cette commande est réservée aux joueurs !");
            return false;
        }

        final String currency = (String) eco.getConfigManager().get("Balance.currency");

        if (args.length > 0) {
            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                player.sendMessage("Le joueur spécifié n'est pas en ligne !");
                return false;
            }

            final EcoPlayerData ecoTargetData = eco.getPlayerData().get(target);
            final String formattedMoney = NumberUtils.formatMoney(ecoTargetData.getMoney());

            player.sendMessage("Voici l'argent de " + target.getName() + ": " + formattedMoney + currency);
        } else {
            final EcoPlayerData ecoPlayerData = eco.getPlayerData().get(player);
            final String formattedMoney = NumberUtils.formatMoney(ecoPlayerData.getMoney());

            player.sendMessage("Voici votre argent: " + formattedMoney + currency);
        }

        return true;
    }
}