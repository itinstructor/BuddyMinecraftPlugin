package com.billthecomputerguy.buddyutil;

/**
 * Name: Main.java
 * Written by:
 * Written on:
 * Purpose:
*/

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;


public class Bar {
    // ID to identify runnable instance and return error
    private int taskID = -1;
    // Reference to Main class
    private final Main plugin;
    // Variable for BossBar instance
    private BossBar bar;

    // Constructor with 1 parameter of Main to access Main from this class
    public Bar(Main plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        // Add player to bossbar instance
        bar.addPlayer(player);
    }

    // Return an instance of the bar
    public BossBar getBar() {
        return bar;
    }

    public void createBar() {
        // Create the initial bossbar
        bar = Bukkit.createBossBar(ChatColor.YELLOW + "World Maps at lab.wncc.net", BarColor.BLUE, BarStyle.SOLID);
        // Show the bar
        bar.setVisible(true);
        // Start the bar runnable
        startBar();
    }

    public void startBar() {
        // How often the message changes in seconds
        final int MESSAGE_CHANGE_SECONDS = 60;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            // Handle the messages
            int count = -1;
            // The bossbar is initialized to 1.0 maximum progress
            double progress = 1.0;

            // This calculates the increments of the bossbar progress change
            // This time calculation updates progress every tick
            // Message change is how often the message changes, 20 is ticks per second
            final double time = 1.0 / (MESSAGE_CHANGE_SECONDS * 20);

            @Override
            public void run() {
                // Set bar progress to current calculated progress variable
                bar.setProgress(progress);
                switch (count) {
                    case -1:
                        break;
                    case 0:
                        bar.setColor(BarColor.PINK);
                        bar.setTitle(ChatColor.YELLOW + "World Maps at lab.wncc.net");
                        break;
                    case 1:
                        bar.setColor(BarColor.GREEN);
                        bar.setTitle(ChatColor.AQUA + "Buddy Command: /buddy tpspawn");
                        break;
                    case 2:
                        bar.setColor(BarColor.BLUE);
                        bar.setTitle(ChatColor.GOLD + "Buddy command: /buddy help");
                        break;
                    case 3:
                        bar.setColor(BarColor.PURPLE);
                        bar.setTitle(ChatColor.AQUA + "facebook.com/wnccitprogram");
                        break;
                    default:
                        bar.setColor(BarColor.PURPLE);
                        bar.setTitle(ChatColor.YELLOW + "Buddy Rules Minecraft!");
                        count = -1;
                        break;
                }
                // Move progress bar to the left each time around
                progress = progress - time;
                // When progress bar is down to 0, reset the progress bar back to 1.0
                if (progress <= 0) {
                    count++;
                    progress = 1.0;
                }
            }
        }, 0, 0);
    }
}