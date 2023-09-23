package com.zqo.eco.data;

import java.util.UUID;

public final class EcoPlayerData 
{
    private final String name;
    private final UUID uuid;
    private double money;

    public EcoPlayerData(String name, UUID uuid, double money) 
    {
        this.name = name;
        this.uuid = uuid;
        this.money = money;
    }

    public String getName() 
    {
        return name;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public double getMoney() 
    {
        return money;
    }

    public void addMoney(final double toAdd)
    {
        this.money += toAdd;
    }

    public void removeMoney(final double toRemove)
    {
        this.money -= toRemove;
    }
}
