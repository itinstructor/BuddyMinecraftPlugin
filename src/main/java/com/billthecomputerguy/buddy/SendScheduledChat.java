package com.billthecomputerguy.buddy;
/**
 * Name: SendScheduledChat.java
 * Written by:
 * Written on:
 * Purpose: Send scheduled chat messages to player
 */

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
                        // Send world map at lab.wncc.net chat message
                        TextComponent start = new TextComponent("\n§7World maps at: ");
                        TextComponent url = new TextComponent("§B§6lab.wncc.net");
                        url.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://lab.wncc.net"));
                        url.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click for world maps.")));
                        // Combine text components into one command
                        start.addExtra(url);
                        player.spigot().sendMessage(start);
                        break;

                    case 1:
                        TextComponent tpspawn = new TextComponent("\n§6Buddy Command: /buddy tpspawn\n");
                        tpspawn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§6Buddy Command: /buddy help")));
                        // Send chat message to player
                        player.spigot().sendMessage(tpspawn);
                        break;

                    case 2:
                        TextComponent doctor = new TextComponent("\n§6Buddy Command: /buddy doctor\n");
                        doctor.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§6Buddy Command: /buddy help")));
                        // Send chat message to player
                        player.spigot().sendMessage(doctor);
                        break;

                    case 3:
                        TextComponent jump = new TextComponent("\n§2Buddy Command: /buddy jump\n");
                        jump.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§6Buddy Command: /buddy help")));
                        // Send chat message to player
                        player.spigot().sendMessage(jump);
                        break;

                    case 4:
                        TextComponent buddySong = new TextComponent("\n§6Buddy Command: /buddysong\n");
                        // Add music unicode 𝄞 𝅘𝅥𝅮 https://www.unicode.org/charts/beta/nameslist/n_1D100.html
                        buddySong.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§6\uD834\uDD60 \uD834\uDD60 Buddy Command: /buddysong")));
                        // Send chat message to player
                        player.spigot().sendMessage(buddySong);
                        break;

                    case 5:
                        TextComponent randomBlock = new TextComponent("\n§6Buddy Command: /buddy randomblock\n");
                        randomBlock.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§6Buddy Command: /randomblock")));
                        // Send chat message to player
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