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
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public final class Main extends JavaPlugin implements Listener {
    // Variable for Bar object
    private Bar bar;

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

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                sendWNCCChatMessage(player);
                // Play chime at join at the pitch of G
                player.playSound(event.getPlayer().getLocation(),
                        Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 1.0F, 1.059463F);
            }
        }, 60L, 1200L * 5L); // 1200 ticks per minute * minutes 5 minutes

        // Get motd of the current server
        String motd = this.getServer().getMotd();
        // Send message to player when they join Buddy's Lobby
        // fadeIn, stay, fadeOut: 20 ticks per second * seconds delay
        player.sendTitle(ChatColor.GOLD + motd, ChatColor.GOLD + "Welcome!", 20 * 1, 20 * 5, 20 * 1);
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
