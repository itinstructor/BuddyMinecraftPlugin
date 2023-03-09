/*****************************************
 * Name: Main.java
 * Written by:
 * Written on:
 * Purpose:
 *****************************************/
package com.billthecomputerguy.buddyutil;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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
        getCommand("buddy").setExecutor(new BuddyCommand());
        getCommand("buddy").setTabCompleter(new BuddyTab());

        // Create BossBar object at the top of the screen
        buddyBossBar = Bukkit.createBossBar(ChatColor.YELLOW + "World Maps at lab.wncc.net", BarColor.BLUE, BarStyle.SEGMENTED_6);
        // Set progress to 100%
        buddyBossBar.setProgress(1.0);

        // Register plugin events with server
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new PlayerStatistics(), this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Get current player instance
        Player player = (Player) event.getPlayer();

        // Play chime at join at the pitch of G
        // e.getPlayer().playSound(e.getPlayer().getLocation(),
        // Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 1.0F, 1.059463F);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                // Send lab.wncc.net chat message
                TextComponent start = new TextComponent("§7Maps at ");

                TextComponent url = new TextComponent("§Blab.wncc.net");
                url.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://lab.wncc.net"));
                url.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click for world maps.")));

                TextComponent buddycommand = new TextComponent("\n/buddy tpspawn");
                buddycommand.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("/buddy help")));

                // Combine textcomponents into one command
                start.addExtra(url);
                start.addExtra(buddycommand);
                player.spigot().sendMessage(start);
            }
        }, 20 * 60 * 5); // 20 ticks per second * 60 seconds per minute * minutes 5 minutes

        // Send chat message
        //player.spigot().sendMessage(start);


        // Get motd of the current server
        String motd = this.getServer().getMotd();
        // Send message to player when they join Buddy's Lobby
        // fadeIn, stay, fadeOut: 20 ticks per second * seconds delay
        player.sendTitle(ChatColor.GOLD + motd, ChatColor.GOLD + "Welcome!", 20 * 1, 20 * 5, 20 * 1);

        // Display BossBar when player joins
        buddyBossBar.addPlayer(player);

        // Remove BossBar after delay
        // Create a scheduled task for this pluggable
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                buddyBossBar.removePlayer(player);
            }
        }, 20 * 15); // 20 ticks per second * seconds delay
    }
}
