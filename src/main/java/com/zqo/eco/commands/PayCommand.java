package com.zqo.eco.commands;

import com.zqo.eco.Eco;
import com.zqo.eco.data.EcoPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

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
        if (sender instanceof Player player) {
            if (args.length > 1) {
                final Player target = Bukkit.getPlayer(args[0]);
                final double toPay = Double.parseDouble(args[1]);

                if (target == null || toPay == 0.0) {
                    return false;
                }

                final EcoPlayerData ecoPlayerData = eco.getPlayerData().get(player.getName());
                final EcoPlayerData ecoTargetData = eco.getPlayerData().get(target.getName());

                ecoPlayerData.removeMoney(toPay);
                ecoTargetData.addMoney(toPay);
            }

        }

        return false;
    }
}
