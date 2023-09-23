package com.zqo.eco.commands;

import com.zqo.eco.Eco;
import com.zqo.eco.builders.GuiBuilder;
import com.zqo.eco.data.EcoPlayerData;
import com.zqo.eco.misc.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class PayCommand extends BukkitCommand
{
    private final Eco eco = Eco.getEco();

    public PayCommand()
    {
        super("pay");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args)
    {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Cette commande est réservée aux joueurs !");
            return true;
        }

        final String currency = (String) eco.getConfigManager().get("Balance.currency");

        if (args.length == 0) {
            openPayMenu(player);
        } else if (args.length == 2) {
            final Player target = Bukkit.getPlayer(args[0]);
            final BigDecimal amount = NumberUtils.parseBigDecimal(args[1]);

            if (target == null) {
                player.sendMessage("Le joueur spécifié n'est pas en ligne !");
                return false;
            }

            if (target.equals(player)) {
                player.sendMessage("Vous ne pouvez pas vous payer vous-même !");
                return false;
            }

            if (Objects.equals(amount, BigDecimal.ZERO)) {
                player.sendMessage("Veuillez préciser un nombre valide !");
                return false;
            }

            final EcoPlayerData playerData = eco.getPlayerData().get(player);

            if (playerData.getMoney().compareTo(amount) < 0) {
                player.sendMessage("Vous n'avez pas assez d'argent !");
                return false;
            }

            final EcoPlayerData targetData = eco.getPlayerData().get(target);

            playerData.removeMoney(amount);
            targetData.addMoney(amount);

            final String formattedMoney = NumberUtils.formatMoney(amount);

            player.sendMessage("Vous avez envoyé " + formattedMoney + currency + " à " + target.getName());
            target.sendMessage("Vous avez reçu " + formattedMoney + currency + " de la part de " + player.getName());
        } else {
            player.sendMessage("Usage : /pay [joueur] [montant]");
        }

        return true;
    }

    private void openPayMenu(Player player) {
        final List<ItemStack> animationFrames = new ArrayList<>();
        animationFrames.add(new ItemStack(Material.APPLE));
        animationFrames.add(new ItemStack(Material.BREAD));
        animationFrames.add(new ItemStack(Material.CARROT));

        GuiBuilder gui = new GuiBuilder()
                .title("Menu de paiement")
                .slots(54)
                .setItem(15, new ItemStack(Material.DIAMOND), event -> {
                    player.sendMessage("Coucou");
                })
                .animateItem(3, animationFrames, 10);

        gui.open(player);
    }
}