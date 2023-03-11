package com.billthecomputerguy.buddyutil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Bar {
    private int taskID = -1;
    // Reference to Main class
    private final Main plugin;
    private BossBar bar;

    // Constructor with 1 parameter of Main
    public Bar(Main plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        // Add bossbar to player
        bar.addPlayer(player);
    }

    public BossBar getBar() {
        return bar;
    }

    public void createBar() {
        bar = Bukkit.createBossBar(ChatColor.YELLOW + "World Maps at lab.wncc.net", BarColor.BLUE, BarStyle.SOLID);
        bar.setVisible(true);
        cast();
    }

    public void cast() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            // Handle the messages
            int count = -1;
            // The bar changes to 1.0 maximum progress
            double progress = 1.0;
            // Bossbar changes message every 30 seconds
            // Bossbar updates every second
            // This calculates the increments of the bossbar change
            double time = 1.0 / 30;

            @Override
            public void run() {
                bar.setProgress(progress);
                switch (count) {
                    case -1:
                        break;
                    case 0:
                        bar.setColor(BarColor.PINK);
                        bar.setTitle(ChatColor.YELLOW + "World Maps at lab.wncc.net");
                        break;
                    case 1:
                        bar.setColor(BarColor.BLUE);
                        bar.setTitle(ChatColor.AQUA + "Command usage: /buddy tpspawn");
                        break;
                    case 2:
                        bar.setColor(BarColor.RED);
                        bar.setTitle(ChatColor.GOLD + "Buddy command help: /buddy help");
                        break;
                    default:
                        bar.setColor(BarColor.RED);
                        bar.setTitle(format("&cBuddy Rules!"));
                        count = -1;
                        break;
                }
                // Move progress bar to the left each time around
                progress = progress - time;
                // Reset the progress bar back to 1
                if (progress <= 0) {
                    count++;
                    progress = 1.0;
                }
            }
        }, 0, 20);
    }

    // Format message with color code
    private String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}