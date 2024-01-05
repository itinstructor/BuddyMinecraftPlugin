package com.billthecomputerguy.buddyutil;
/**
 * Name: Main.java
 * Written by:
 * Written on:
 * Purpose:
 */

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SendChat {

    // ID to identify runnable instance and return error
    private int taskID = -1;
    // Reference to Main class
    private final Main plugin;
    // Variable for BossBar instance
    private SendChat sendChat;

    public SendChat(Main plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        // Add player to send instance
        sendChat.addPlayer(player);
    }

    public void createChatMessage(Player player) {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                sendWNCCChatMessage(player);
//              // Play chime at the pitch of G
                // player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 1.0F, 1.059463F);
            }
        }, 60L, 1200L * 20L); // 1200 ticks per minute * minutes 5 minutes
    }

    public void sendWNCCChatMessage(Player player) {
        // Send lab.wncc.net chat message
        TextComponent start = new TextComponent("\n§7World maps at: ");

        TextComponent url = new TextComponent("§Blab.wncc.net");
        url.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://lab.wncc.net"));
        url.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click for world maps.")));

        TextComponent buddyCommand = new TextComponent("\nBuddy Command: /buddy tpspawn\n");
        buddyCommand.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Buddy Command: /buddy help")));

        // Combine textcomponents into one command
        start.addExtra(url);
        start.addExtra(buddyCommand);
        player.spigot().sendMessage(start);
    }
}
