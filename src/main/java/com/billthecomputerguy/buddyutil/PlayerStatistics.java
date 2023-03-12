package com.billthecomputerguy.buddyutil;
/*****************************************
 * Name: playerStatistics.java
 * Written by:
 * Written on:
 * Purpose: Display player statistics
 *****************************************/

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

public class PlayerStatistics implements Listener {
    // A hashmap is like a Python dictionary, key value pairs
    // Use Long to allow for large integers, especially for steps
    private HashMap<UUID, Long> blocksBrocken = new HashMap<>();
    private HashMap<UUID, Long> blocksPlaced = new HashMap<>();
    private HashMap<UUID, Long> steps = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Get player instance
        Player player = event.getPlayer();
        // Create scoreboard manager object
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        // Use scoreboardManager to create a new scoreboard object
        Scoreboard board = scoreboardManager.getNewScoreboard();

        /*****************************************************************************
         * STATIC SCOREBOARD TEXT
         *****************************************************************************/
        // Create scoreboard display name of current player
        String boardDisplayName = ChatColor.GREEN + player.getDisplayName();
        // Purpose and name of scoreboard
        Objective obj = board.registerNewObjective("playerboard", Criteria.DUMMY, boardDisplayName);

        // Set location of scoreboard
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        // A Score is a line of text
        Score website = obj.getScore(ChatColor.YELLOW + "lab.wncc.net");
        // Each line must have a number starting from the bottom
        website.setScore(1);

        // Insert a space before the next score
        Score space = obj.getScore(" ");
        space.setScore(2);

        /*****************************************************************************
         * BLOCKS BROKEN
         *****************************************************************************/
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

        /*****************************************************************************
         * BLOCKS PLACED
         *****************************************************************************/
        Team blocksPlaced = board.registerNewTeam("blocksplaced");
        // Add unique chat color to distinguish between entries
        blocksPlaced.addEntry(ChatColor.BLUE.toString());
        // Blocks broken: 5 prefix and suffix
        blocksPlaced.setPrefix(ChatColor.BLUE + "Blocks placed: ");
        blocksPlaced.setSuffix(ChatColor.YELLOW + "0");
        // Set to line 4 from top
        // This chatcolor must match the addentry chat color to display
        obj.getScore(ChatColor.BLUE.toString()).setScore(4);

        /*****************************************************************************
         * COUNT STEPS
         *****************************************************************************/
        Team steps = board.registerNewTeam("steps");
        // Add unique chat color to distinguish between entries
        steps.addEntry(ChatColor.AQUA.toString());
        // Blocks broken: 5 prefix and suffix
        steps.setPrefix(ChatColor.BLUE + "Steps: ");
        steps.setSuffix(ChatColor.YELLOW + "0");
        // Set to line 5 from top
        // This chatcolor must match the addentry chat color to display
        obj.getScore(ChatColor.AQUA.toString()).setScore(5);

        /*****************************************************************************
         * DISPLAY SCOREBOARD
         *****************************************************************************/
        // Show the scoreboard
        player.setScoreboard(board);
        // Show the teams information
        this.blocksBrocken.put(player.getUniqueId(), 0L);
        this.blocksPlaced.put(player.getUniqueId(), 0L);
        this.steps.put(player.getUniqueId(), 0L);
    }

    /*****************************************************************************
     * ONBLOCKPLACE
     *****************************************************************************/
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        // Get current player instance
        Player player = event.getPlayer();

        long amount = blocksPlaced.get(player.getUniqueId());
        amount++;
        // Update brocksplaced amount in hashmap
        blocksPlaced.put(player.getUniqueId(), amount);
        // Update scoreboard display
        player.getScoreboard().getTeam("blocksplaced").setSuffix(ChatColor.YELLOW.toString() + amount);
    }

    /*
     * ----------------------------------- ONBLOCKBREAK ----------------------------
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Get current player instance
        Player player = event.getPlayer();
        // Track the amount of blocks broken
        long amount = blocksBrocken.get(player.getUniqueId());
        amount++;
        // Update brocksbroken amount in hashmap
        blocksBrocken.put(player.getUniqueId(), amount);
        // Update scoreboard display
        player.getScoreboard().getTeam("blocksbroken").setSuffix(ChatColor.YELLOW.toString() + amount);
    }

    /*
     * ----------------------------------- ONPLAYERMOVE -----------------------------
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        // Get current player instance
        Player player = e.getPlayer();
        // If the player doesn't actually move from block to block, return and don't do anything
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ()) {
            return;
        } else {
            // Update steps by one block
            long amount = steps.get(player.getUniqueId());
            amount++;
            // Update steps amount in hashmap
            steps.put(player.getUniqueId(), amount);
            // Update scoreboard display
            player.getScoreboard().getTeam("steps").setSuffix(ChatColor.YELLOW.toString() + amount);
        }
    }
}
