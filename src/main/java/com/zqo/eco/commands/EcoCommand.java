package com.zqo.eco.commands;

import com.zqo.eco.Eco;
import com.zqo.eco.data.EcoPlayerData;
import com.zqo.eco.misc.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Objects;

public final class EcoCommand extends BukkitCommand
{
    private final Eco eco = Eco.getEco();

    public EcoCommand()
    {
        super("eco");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args)
    {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Cette commande est réservée aux joueurs !");
            return false;
        }

        final String currency = (String) eco.getConfigManager().get("Balance.currency");

        if (args.length < 2) {
            player.sendMessage("Usage : /eco <reset|add|set|remove> <joueur> [montant]");
            return false;
        }

        final Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            player.sendMessage("Le joueur spécifié n'est pas en ligne !");
            return false;
        }

        final EcoPlayerData ecoTargetData = eco.getPlayerData().get(target);
        BigDecimal amount = BigDecimal.ZERO;

        if (args.length > 2) {
            amount = NumberUtils.parseBigDecimal(args[2]);

            if (Objects.equals(amount, BigDecimal.ZERO)) {
                player.sendMessage("Veuillez préciser un nombre valide !");
                return false;
            }
        }

        final String formattedMoney = NumberUtils.formatMoney(amount);

        switch (args[0].toLowerCase()) {
            case "reset":
                ecoTargetData.resetMoney();
                player.sendMessage("L'argent de " + target.getName() + " a été réinitialisé.");
                break;
            case "add":
                ecoTargetData.addMoney(amount);
                player.sendMessage("L'argent de " + target.getName() + " a été augmenté de " + formattedMoney + currency);
                break;
            case "set":
                ecoTargetData.setMoney(amount);
                player.sendMessage("L'argent de " + target.getName() + " a été défini à " + formattedMoney + currency);
                break;
            case "remove":
                ecoTargetData.removeMoney(amount);
                player.sendMessage("L'argent de " + target.getName() + " a été réduit de " + formattedMoney + currency);
                break;
            default:
                player.sendMessage("Argument invalide !");
                break;
        }

        return true;
    }
}