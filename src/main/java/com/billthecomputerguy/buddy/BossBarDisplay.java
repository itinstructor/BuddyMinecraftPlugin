package com.billthecomputerguy.buddy;

/**
 * Name: BossBarDisplay.java
 * Written by:
 * Written on:
 * Purpose: This class defines and manages a BossBar,
 * which is a graphical display element used in Minecraft plugins.
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarDisplay {
    // ID to identify runnable instance and return error
    private int taskID = -1;

    // Reference to access Main class
    private final Main plugin;

    // Reference for BossBar instance
    private BossBar bar;

    // Constructor with parameter of Main to access Main from this class
    public BossBarDisplay(Main plugin) {
        this.plugin = plugin;
    }

    // Method to add a player to the BossBar
    public void addPlayer(Player player) {
        // Add player to bossbar instance
        bar.addPlayer(player);
    }

    // Return an instance of the bar
    public BossBar getBar() {
        return bar;
    }

    // Method to create and initialize the BossBar
    public void createBar() {
        // Create the initial BossBar with a title and styling
        bar = Bukkit.createBossBar(ChatColor.YELLOW + "World Maps at lab.wncc.net", BarColor.BLUE, BarStyle.SOLID);

        // Show the bar
        bar.setVisible(true);

        // Start the BossBar runnable
        startBar();
    }

    public void startBar() {
        // How often the message changes in seconds
        final int MESSAGE_CHANGE_SECONDS = 60;

        /**
         * Schedules a synchronous repeating task using Bukkit's scheduler.
         *
         * @param plugin The plugin instance associated with the task.
         * @param task The task represented as a Runnable with the code to be executed.
         * @param initialDelay The initial delay before the task starts running (in server ticks).
         * @param repeatDelay The delay between subsequent runs of the task (in server ticks).
         * @return The unique ID of the scheduled task.
         *
         * getScheduler(): This is a method provided by the Bukkit API that allows you to access the Bukkit scheduler,
         *     which is used for scheduling a repeated task
         * scheduleSyncRepeatingTask(plugin, new Runnable() { ... }, 0, 0): Method used to schedule a repeating task.
         *   plugin: This is the plugin instance to which the task is associated. It's the instance of the
         *       Main class that was passed as a parameter when creating the BossBarDisplay object.
         *   new Runnable() { ... }: Here, an anonymous inner class is created. It implements the Runnable interface,
         *       which means it has a run() method that contains the code to be executed when the task runs.
         *   0: This is the initial delay before the task starts running for the first time.
         *       In this case, it's set to 0, meaning the task starts immediately.
         *   0: This is the delay between subsequent runs of the task.
         *       Also set to 0 in this case, which means the task runs as frequently as possible.
         */

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            // Track which bar we are currently displaying
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
                        bar.setColor(BarColor.YELLOW);
                        //  𝄞 𝅘𝅥𝅮 https://www.unicode.org/charts/beta/nameslist/n_1D100.html
                        bar.setTitle(ChatColor.GREEN + "\uD834\uDD60\uD834\uDD60 Buddy command: /playsong");
                        break;

                    case 4:
                        bar.setColor(BarColor.BLUE);
                        bar.setTitle(ChatColor.BLUE + "Buddy command: /buddy randomblock");
                        break;

                    case 5:
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
        }, 0, 0); // 0, 0 indicates running the task immediately and repeatedly.
    }
}