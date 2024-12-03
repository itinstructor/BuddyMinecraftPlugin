package com.billthecomputerguy.buddy;
/**
 * Name: SendScheduledChat.java
 * Written by:
 * Written on:
 * Purpose: Send scheduled chat messages to player
 */

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SendScheduledChat {
    private int taskID = -1; // ID to identify runnable instance and return error
    private final Main plugin; // Reference to access Main class
    private SendScheduledChat sendScheduledChat; // Variable for chat

    public SendScheduledChat(Main plugin) {
        this.plugin = plugin;
    }

    /****************************************************************************
     CREATE WNCC CHAT MESSAGE RUNNABLE FOR REPEATING MESSAGES
     */
    public void createWNCCChatMessageRunnable(Player player) {
        // 1200 ticks per minute
        final long TICKS_PER_MINUTE = 1200l;

        // How many minutes between chat messages to player
        final long MINUTES_BETWEEN_MESSAGES = 15l;

        // Multiply ticks per minute * minutes
        final long CHAT_DELAY = TICKS_PER_MINUTE * MINUTES_BETWEEN_MESSAGES;

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int count = 0;

            @Override
            public void run() {
                switch (count) {
                    case 0:
                        // Send a chat message with a clickable link to world maps at lab.wncc.net

                        // Create a new TextComponent for the introductory text
                        TextComponent start = new TextComponent("\n§7World maps at: ");

                        // Create a new TextComponent for the clickable link with specified color
                        TextComponent url = new TextComponent("§B§6lab.wncc.net");

                        // Set the click event to open the specified URL when clicked
                        url.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://lab.wncc.net"));

                        // Set the hover event to display a tooltip when hovered over
                        url.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click for world maps.")));

                        // Combine the introductory text and the clickable link into one TextComponent
                        start.addExtra(url);

                        // Send the composed TextComponent as a chat message to the player
                        player.spigot().sendMessage(start);
                        break;

                    case 1:
                        // Create a new TextComponent for a chat message
                        TextComponent tpspawn = new TextComponent(ChatColor.GREEN + "\n§6Buddy Command: /buddy tpspawn\n");

                        // Add a hover event to the TextComponent to show additional information when hovered over
                        tpspawn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§6Buddy Command: /buddy help")));

                        // Send the chat message to the player using the Spigot API
                        player.spigot().sendMessage(tpspawn);
                        break;

                    case 2:
                        // Create a new TextComponent for a chat message
                        TextComponent doctor = new TextComponent(ChatColor.GOLD + "\n§6Buddy Command: /buddy doctor\n");

                        // Add a hover event to the TextComponent to show additional information when hovered over
                        doctor.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§6Buddy Command: /buddy help")));

                        // Send the chat message to the player using the Spigot API
                        player.spigot().sendMessage(doctor);
                        break;

                    case 3:
                        // Create a new TextComponent for a chat message
                        TextComponent jump = new TextComponent(ChatColor.BLUE + "\n§2Buddy Command: /buddy jump\n");

                        // Add a hover event to the TextComponent to show additional information when hovered over
                        jump.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§6Buddy Command: /buddy help")));

                        // Send the chat message to the player using the Spigot API
                        player.spigot().sendMessage(jump);
                        break;

                    case 4:
                        // Create a new TextComponent for a chat message
                        TextComponent buddySong = new TextComponent(ChatColor.RED + "\n§6Buddy Command: /buddysong\n");

                        // Add a hover event to the TextComponent to show additional information when hovered over
                        buddySong.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§6Buddy Command: /buddysong")));

                        // Send the chat message to the player using the Spigot API
                        player.spigot().sendMessage(buddySong);
                        break;

                    case 5:
                        // Create a new TextComponent for a chat message
                        TextComponent randomBlock = new TextComponent(ChatColor.GREEN + "\n§6Buddy Command: /buddy randomblock\n");

                        // Add a hover event to the TextComponent to show additional information when hovered over
                        randomBlock.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§6Buddy Command: /randomblock")));

                        // Send the chat message to the player using the Spigot API
                        player.spigot().sendMessage(randomBlock);
                        break;

                    default:
                        // Reset count and start over with the first chat message
                        count = 0;
                }
                count++;
            }
        }, 60L, CHAT_DELAY);
    }
}