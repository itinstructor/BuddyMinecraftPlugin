/*****************************************
 * Name: Main.java
 * Written by:
 * Written on:
 * Purpose:
 *****************************************/
package com.billthecomputerguy.buddyutil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {
    // Variable for Bar object
    private Bar bar;
    private SendChat sendChat;

    @Override
    public void onEnable() {
        // Register and Enable events for PlayerStatistics
        Bukkit.getPluginManager().registerEvents(new PlayerStatistics(), this);
        // Create a new bar object
        bar = new Bar(this);
        // Call createBar method from Bar class
        bar.createBar();

        // Create buddy command instances
        getCommand("buddy").setExecutor(new BuddyCommand());
        getCommand("buddy").setTabCompleter(new BuddyTab());

        // Register events for plugin listener
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Remove bar when server is shutting down
        bar.getBar().removeAll();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Get current player instance
        Player player = (Player) event.getPlayer();
        // Add player to the bar if they are not part of the bar already
        if (!bar.getBar().getPlayers().contains(player))
            bar.addPlayer((player));

        sendChat = new SendChat(this);
        sendChat.createChatMessage(player);

        // Get motd of the current server
        String motd = this.getServer().getMotd();
        // Send message to player when they join Buddy's Lobby
        // fadeIn, stay, fadeOut: 20 ticks per second * seconds delay
        player.sendTitle(ChatColor.GOLD + motd, ChatColor.GOLD + "Welcome!", 20 * 2, 15 * 5, 20 * 2);
    }
}
