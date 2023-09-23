package com.zqo.eco.handlers;

import com.google.common.reflect.ClassPath;
import com.zqo.eco.Eco;
import com.zqo.eco.builders.GuiBuilder;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public final class ListenerHandler
{
    private final Eco eco = Eco.getEco();

    public void register()
    {
        final PluginManager pm = eco.getServer().getPluginManager();

        try {
            final ClassPath classPath = ClassPath.from(eco.getClass().getClassLoader());

            classPath.getTopLevelClassesRecursive("com.zqo.eco.listeners").forEach((classInfo -> {
                try {
                    final Class c = Class.forName(classInfo.getName());
                    final Object obj = c.getDeclaredConstructor().newInstance();

                    if (obj instanceof Listener listener) {
                        pm.registerEvents(listener, eco);
                    }
                } catch (IllegalAccessException | ClassNotFoundException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }));

            pm.registerEvents(new GuiBuilder.GuiListener(), eco);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
