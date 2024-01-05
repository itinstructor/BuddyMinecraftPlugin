package com.billthecomputerguy.buddyutil.player_data;

import com.billthecomputerguy.buddyutil.Main;
import com.billthecomputerguy.buddyutil.PlayerStats;
import com.billthecomputerguy.buddyutil.PlayerStatsScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.sql.SQLException;
import java.util.UUID;

public class DbConnectionListener implements Listener {
    private Main main;
    private Database database;
    public DbConnectionListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Create player object
        Player player = event.getPlayer();
        // Get information about player
        UUID uuid = player.getUniqueId();
        String displayName = player.getDisplayName();
        try {
            // Create new CustomerPlayer object
            CustomPlayer playerData = new CustomPlayer(main, uuid, displayName);
            // Add player to hash map
            main.getPlayerManager().addCustomPlayer(uuid, playerData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method runs when the plugin is disabled when the world is shutdown
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Bukkit.getServer().getLogger().info("Buddy Util: On Player Quit");
        Player player = (Player) event.getPlayer();
        // Get the current custom player data
        // PlayerStatsScoreboard playerStats = main.getPlayerStatsScoreboard(player.getUniqueId());
        PlayerStats playerStats = main.getPlayerStats(player.getUniqueId());
        CustomPlayer player1 = main.getPlayerManager().getCustomPlayer(player.getUniqueId());

        player1.setBlocksBroken(playerStats.getBlocksBroken());
//        player1.setBlocksPlaced(playerStats.getBlocksPlaced());
//        player1.setSteps(playerStats.getSteps());

        Bukkit.getServer().getLogger().info("Buddy Util: Update db");

        // Remove player from hashmap when they quit the world
        // main.getPlayerManager().removeCustomPlayer(event.getPlayer().getUniqueId());
    }
}
