package com.zqo.eco.builders;

import com.zqo.eco.Eco;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public final class GuiBuilder
{
    private final Eco eco = Eco.getEco();

    private final String title;
    private final int size;
    private final Inventory inventory;
    private final Map<Integer, ClickAction> clickActions;

    public GuiBuilder()
    {
        this.title = "";
        this.size = 9;
        this.inventory = Bukkit.createInventory(null, size, title);
        this.clickActions = new HashMap<>();
    }

    public GuiBuilder title(final String title)
    {
        return new GuiBuilder(title, size);
    }

    public GuiBuilder slots(final int size)
    {
        return new GuiBuilder(title, size);
    }

    public GuiBuilder addItem(final ItemStack itemStack, final Consumer<InventoryClickEvent> onClick)
    {
        for (int i = 0; i < size; i++) {
            final ItemStack current = inventory.getItem(i);
            if (current == null || current.getType() == Material.AIR) {
                inventory.setItem(i, itemStack);
                clickActions.put(i, new ClickAction(onClick));
                return this;
            }
        }
        return this;
    }

    public GuiBuilder setItem(final int slot, final ItemStack itemStack, final Consumer<InventoryClickEvent> onClick)
    {
        inventory.setItem(slot, itemStack);
        clickActions.put(slot, new ClickAction(onClick));
        return this;
    }

    public GuiBuilder removeItem(final int slot)
    {
        inventory.setItem(slot, null);
        clickActions.remove(slot);
        return this;
    }

    public GuiBuilder addSeparator(final ItemStack separatorItem, final int row)
    {
        if (row >= 0 && row < size / 9) {
            final int startIndex = row * 9;
            final int endIndex = startIndex + 8;

            for (int i = startIndex; i <= endIndex; i++) {
                inventory.setItem(i, separatorItem);
                clickActions.remove(i);
            }
        }

        return this;
    }

    public GuiBuilder animateItem(final int slot, final List<ItemStack> animationFrames, final long tickDelay)
    {
        if (slot >= 0 && slot < size) {
            if (animationFrames.isEmpty()) {
                throw new IllegalArgumentException("Animation frames cannot be empty.");
            }

            final int animationSize = animationFrames.size();
            final AtomicInteger frameIndex = new AtomicInteger(0);

            Bukkit.getScheduler().runTaskTimer(eco, () -> {
                int index = frameIndex.getAndIncrement();
                if (index >= animationSize) {
                    frameIndex.set(0);
                    index = 0;
                }

                final ItemStack frame = animationFrames.get(index);
                inventory.setItem(slot, frame);
            }, 0L, tickDelay);
        }

        return this;
    }



    public GuiBuilder fill(Material paneMaterial)
    {
        final ItemStack pane = new ItemStack(paneMaterial);

        for (int i = 0; i < size; i++) {
            final ItemStack current = inventory.getItem(i);
            if (current == null || current.getType() == Material.AIR) {
                inventory.setItem(i, pane);
            }
        }

        return this;
    }

    public GuiBuilder fillSquare(final Material paneMaterial)
    {
        final ItemStack pane = new ItemStack(paneMaterial);
        final int numRows = size / 9;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < 9; col++) {
                if (row == 0 || row == numRows - 1) {
                    inventory.setItem(row * 9 + col, pane);
                } else if (col == 0 || col == 8) {
                    inventory.setItem(row * 9 + col, pane);
                }
            }
        }

        return this;
    }

    public void open(final Player player)
    {
        player.openInventory(inventory);
        GuiListener.registerGui(player, this);
    }

    public static class GuiListener implements Listener
    {
        private static final Map<Player, GuiBuilder> playerGuiMap = new HashMap<>();

        public static void registerGui(final Player player, final GuiBuilder guiBuilder)
        {
            playerGuiMap.put(player, guiBuilder);
        }

        public static void unregisterGui(final Player player)
        {
            playerGuiMap.remove(player);
        }

        @EventHandler
        public void onInventoryClick(final InventoryClickEvent event)
        {
            final Player player = (Player) event.getWhoClicked();
            final GuiBuilder gui = playerGuiMap.get(player);
            if (gui != null) {
                event.setCancelled(true);
                final ClickAction clickAction = gui.clickActions.get(event.getRawSlot());
                if (clickAction != null) {
                    clickAction.execute(event);
                }
            }
        }

        @EventHandler
        public void onInventoryClose(final InventoryCloseEvent event)
        {
            final Player player = (Player) event.getPlayer();
            unregisterGui(player);
        }
    }

    private GuiBuilder(final String title, final int size)
    {
        this.title = title;
        this.size = size;
        this.inventory = Bukkit.createInventory(null, size, title);
        this.clickActions = new HashMap<>();
    }

    private record ClickAction(Consumer<InventoryClickEvent> onClick)
    {
        void execute(InventoryClickEvent event) {
            onClick.accept(event);
        }
    }
}