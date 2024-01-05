package com.billthecomputerguy.buddyutil;
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
import com.billthecomputerguy.buddyutil.player_data.Database;
import com.billthecomputerguy.buddyutil.player_data.PlayerManager;
import com.billthecomputerguy.buddyutil.player_data.DbConnectionListener;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {
    private Bar bar;
    private SendChat sendChat;
    private Database database;
    private PlayerManager playerManager;
//    private PlayerStatsScoreboard playerStatsScoreboard;
private PlayerStats playerStats;
    @Override
    public void onEnable() {
        // Register and Enable events for PlayerStatistics
        Bukkit.getPluginManager().registerEvents(new PlayerStatsScoreboard(), this);
        // Create a new bar object
        bar = new Bar(this);
        // Call createBar method from Bar class
        bar.createBar();

        // Create buddy command instances
        Objects.requireNonNull(getCommand("buddy")).setExecutor(new BuddyCommand());
        Objects.requireNonNull(getCommand("buddy")).setTabCompleter(new BuddyTab());

        // Create database object to connect to the MySQL database
        database = new Database();
        // Try to connect to the database
        try {
            database.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Create a new instance of playermanager for use by Main
        playerManager = new PlayerManager();
//        playerStatsScoreboard = new PlayerStatsScoreboard();
        //playerStats = new PlayerStats();
        this.getServer().getPluginManager().registerEvents(new DbConnectionListener(this), this);
        // Register events for Main plugin listener
        this.getServer().getPluginManager().registerEvents(this, this);

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

    public Database getDatabase() {
        return database;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
//    public PlayerStatsScoreboard getPlayerStatsScoreboard(UUID uniqueId) {
//        return playerStatsScoreboard;
//    }
public PlayerStats getPlayerStats(UUID uniqueId) {
        return playerStats;
    }
    /*
     * This method runs when the plugin is disabled when the world is shutdown
     */
//    @Override
    public void onDisable() {
        // Disconnect from database when plugin is disabled when the world is shutdown
        database.disconnect();
    }
}
