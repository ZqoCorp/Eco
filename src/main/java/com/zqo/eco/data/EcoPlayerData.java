package com.zqo.eco.data;

import org.bukkit.entity.Player;

import java.math.BigDecimal;

public final class EcoPlayerData 
{
    private final Player player;
    private BigDecimal money;

    public EcoPlayerData(Player player, BigDecimal money)
    {
        this.player = player;
        this.money = money;
    }

    public BigDecimal getMoney()
    {
        return money;
    }

    public void addMoney(final BigDecimal toAdd)
    {
        this.money = this.money.add(toAdd);
    }

    public void removeMoney(final BigDecimal toRemove)
    {
        this.money = this.money.subtract(toRemove);
    }

    public void setMoney(final BigDecimal toSet)
    {
        this.money = toSet;
    }

    public void resetMoney()
    {
        this.money = BigDecimal.ZERO;
    }
}
