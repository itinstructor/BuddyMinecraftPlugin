package com.billthecomputerguy.buddy;
/**
 * Name: Main.java
 * Written by:
 * Written on:
 * Purpose: Buddy's Minecraft Server spigot utility plugin
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {
    private BossBarDisplay bar;
    private SendScheduledChat sendScheduledChat;

    @Override
    public void onEnable() {
        // Register events for Main plugin listener
        this.getServer().getPluginManager().registerEvents(this, this);

        // Register and Enable events for PlayerStatistics
        Bukkit.getPluginManager().registerEvents(new PlayerStatsScoreboard(), this);

        // Create a new bar object
        bar = new BossBarDisplay(this);
        // Call createBar method from Bar class
        bar.createBar();

        // Create buddy command instances
        this.getCommand("buddy").setExecutor(new BuddyCommand(this));
        this.getCommand("buddy").setTabCompleter(new BuddyTabComplete());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Get current player instance
        Player player = (Player) event.getPlayer();

        // Add player to the bar if they are not part of the bar already
        if (!bar.getBar().getPlayers().contains(player))
            bar.addPlayer((player));

        // Send scheduled chat messages to the player
        sendScheduledChat = new SendScheduledChat(this);
        sendScheduledChat.createWNCCChatMessageRunnable(player);

        /****************************************************************************
         * Message when player joins world
         */
        // Get motd of the current world
        String motd = this.getServer().getMotd();

        // Get default gamemode, creative, survival, etc.
        String gameMode = this.getServer().getDefaultGameMode().name();

        // fadeIn, stay, fadeOut: 20 ticks per second * seconds delay
        // Send message to player when they join a server with Server info
        player.sendTitle(ChatColor.GOLD + motd, ChatColor.GOLD + gameMode,
                20 * 2, 15 * 5, 20 * 2);
    }
}
