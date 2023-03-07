package com.billthecomputerguy.buddyutil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public final class Main extends JavaPlugin implements Listener {
    private BossBar buddyBossBar;

    @Override
    public void onEnable() {
        // Create buddy command instances
        getCommand("buddy").setExecutor(new buddyCommand());
        getCommand("buddy").setTabCompleter(new buddyTab());

        // Create BossBar object at the top of the screen
        buddyBossBar = Bukkit.createBossBar(ChatColor.YELLOW + "World Maps at lab.wncc.net", BarColor.BLUE, BarStyle.SEGMENTED_6);
        // Set progress to 100%
        buddyBossBar.setProgress(1.0);

        // Register plugin events with server
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new playerStatistics(), this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Get player instance
        Player player = (Player) event.getPlayer();

        // Play chime at join at the pitch of G
        // e.getPlayer().playSound(e.getPlayer().getLocation(),
        // Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 1.0F, 1.059463F);

        // Get motd of the current server
        String motd = this.getServer().getMotd();
        // Send message to player when they join Buddy's Lobby
        // fadeIn, stay, fadeOut: 20 ticks per second * seconds delay
        player.sendTitle(ChatColor.GOLD + motd, ChatColor.GOLD + "Welcome!", 20 * 1, 20 * 5, 20 * 1);
        // player.sendTitle(
        // ChatColor.GOLD + "Buddy's Lobby!",
        // ChatColor.GOLD + "World Maps at lab.wncc.net",
        // 20,
        // 100,
        // 20
        // );
        // Display BossBar when player joins
        buddyBossBar.addPlayer(player);
        // Remove BossBar after delay
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                buddyBossBar.removePlayer(player);
            }
        }, 20 * 15); // 20 ticks per second * seconds delay
    }
}

// @EventHandler
// public void onMove(PlayerMoveEvent e) {
// // Remove BossBar when player moves
// buddyBossBar.removePlayer((e.getPlayer()));
// }
// }
