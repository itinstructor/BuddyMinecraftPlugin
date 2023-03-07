package com.billthecomputerguy.buddyutil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

public class playerStatistics implements Listener {
    // A hashmap is like a Python dictionary, key value pairs
    private HashMap<UUID, Integer> blocksBrocken = new HashMap<>();
    private HashMap<UUID, Integer> blocksPlaced = new HashMap<>();
    private HashMap<UUID, Integer> steps = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Get player instance
        Player player = event.getPlayer();
        // Crseate scoreboard
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        /*
         * ------------------- STATIC SCOREBOARD TEXT -----------------------
         */
        Objective obj = board.registerNewObjective("playerboard", "dummy");

        // Set location and displayname
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.GREEN.toString() + player.getDisplayName());

        // A Score is a line of text
        Score website = obj.getScore(ChatColor.YELLOW + "lab.wncc.net");
        // Each line needs a number starting from the bottom
        website.setScore(1);

        Score space = obj.getScore(" ");
        space.setScore(2);

        /*
         * ------------------------------ BLOCKS BROKEN ------------------------------
         */
        // Teams are used for dynamic scoreboard text
        Team blocksBroken = board.registerNewTeam("blocksbroken");
        // Add unique chat color to distinguish between entries
        blocksBroken.addEntry(ChatColor.BOLD.toString());
        // Blocks broken: 5 prefix and suffix
        blocksBroken.setPrefix(ChatColor.BLUE + "Blocks broken: ");
        blocksBroken.setSuffix(ChatColor.YELLOW + "0");
        // Set to line 3 from top
        // This chatcolor must match the addentry chat color to display
        obj.getScore(ChatColor.BOLD.toString()).setScore(3);

        /*
         * ------------------------------ BLOCKS PLACED ------------------------------
         */
        Team blocksPlaced = board.registerNewTeam("blocksplaced");
        // Add unique chat color to distinguish between entries
        blocksPlaced.addEntry(ChatColor.BLUE.toString());
        // Blocks broken: 5 prefix and suffix
        blocksPlaced.setPrefix(ChatColor.BLUE + "Blocks placed: ");
        blocksPlaced.setSuffix(ChatColor.YELLOW + "0");
        // Set to line 4 from top
        // This chatcolor must match the addentry chat color to display
        obj.getScore(ChatColor.BLUE.toString()).setScore(4);

        /*
         * ------------------------------ STEPS --------------------------------------
         */
        Team steps = board.registerNewTeam("steps");
        // Add unique chat color to distinguish between entries
        steps.addEntry(ChatColor.AQUA.toString());
        // Blocks broken: 5 prefix and suffix
        steps.setPrefix(ChatColor.BLUE + "Steps: ");
        steps.setSuffix(ChatColor.YELLOW + "0");
        // Set to line 5 from top
        // This chatcolor must match the addentry chat color to display
        obj.getScore(ChatColor.AQUA.toString()).setScore(5);

        // Show the scoreboard
        player.setScoreboard(board);
        // Show the teams information
        this.blocksBrocken.put(player.getUniqueId(), 0);
        this.blocksPlaced.put(player.getUniqueId(), 0);
        this.steps.put(player.getUniqueId(), 0);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        // Get current player instance
        Player player = event.getPlayer();

        int amount = blocksPlaced.get(player.getUniqueId());
        amount++;
        // Update brocksplaced amount in hashmap
        blocksPlaced.put(player.getUniqueId(), amount);
        // Update scoreboard display
        player.getScoreboard().getTeam("blocksplaced").setSuffix(ChatColor.YELLOW.toString() + amount);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Get current player instance
        Player player = event.getPlayer();

        int amount = blocksBrocken.get(player.getUniqueId());
        amount++;
        // Update brocksbroken amount in hashmap
        blocksBrocken.put(player.getUniqueId(), amount);
        // Update scoreboard display
        player.getScoreboard().getTeam("blocksbroken").setSuffix(ChatColor.YELLOW.toString() + amount);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        // Get current player instance
        Player player = e.getPlayer();
        // If the player doesn't actually move from block to block, return and don't do
        // anything
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ()) {
            return;
        } else {
            // Update steps by one block
            int amount = steps.get(player.getUniqueId());
            amount++;
            // Update steps amount in hashmap
            steps.put(player.getUniqueId(), amount);
            // Update scoreboard display
            player.getScoreboard().getTeam("steps").setSuffix(ChatColor.YELLOW.toString() + amount);
            // steps.get(e.getPlayer()).setScore(steps.get(e.getPlayer()).getScore() + 1);
        }
    }
}
