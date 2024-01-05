package com.billthecomputerguy.buddy;
/**
 * Name: playerStatisticsScoreBoard.java
 * Written by:
 * Written on:
 * Purpose: Display player statistics
 */

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

public class PlayerStatsScoreboard implements Listener {

    private PlayerStatsClass playerStats;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Get player instance
        Player player = event.getPlayer();

        // Create new PlayerStats object for current player
        playerStats = new PlayerStatsClass(player.getUniqueId());

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
        Objective objective = board.registerNewObjective("playerboard", Criteria.DUMMY, boardDisplayName);

        // Set location of scoreboard
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        /*****************************************************************************
         * DISPAY BLOCKS BROKEN
         *****************************************************************************/
        // Teams are used for dynamic scoreboard text
        Team blocksBroken = board.registerNewTeam("blocksbroken");

        // Add unique chat color to distinguish between entries
        blocksBroken.addEntry(ChatColor.BOLD.toString());

        // Blocks broken: 5 prefix and suffix
        blocksBroken.setPrefix(ChatColor.BLUE + "Blocks broken: ");
        blocksBroken.setSuffix(ChatColor.YELLOW + "0");

        // Set to line 1 from top
        // This chatcolor must match the addentry chat color to display
        objective.getScore(ChatColor.BOLD.toString()).setScore(1);

        /*****************************************************************************
         * DISPLAY BLOCKS PLACED
         *****************************************************************************/
        Team blocksPlaced = board.registerNewTeam("blocksplaced");

        // Add unique chat color to distinguish between entries
        blocksPlaced.addEntry(ChatColor.BLUE.toString());

        // Blocks broken: 5 prefix and suffix
        blocksPlaced.setPrefix(ChatColor.BLUE + "Blocks placed: ");
        blocksPlaced.setSuffix(ChatColor.YELLOW + "0");

        // Set to line 2 from top
        // This chatcolor must match the addentry chat color to display
        objective.getScore(ChatColor.BLUE.toString()).setScore(2);

        /*****************************************************************************
         * DISPLAY COUNT STEPS
         *****************************************************************************/
        Team steps = board.registerNewTeam("steps");

        // Add unique chat color to distinguish between entries
        steps.addEntry(ChatColor.AQUA.toString());

        // Blocks broken: 5 prefix and suffix
        steps.setPrefix(ChatColor.BLUE + "Steps: ");
        steps.setSuffix(ChatColor.YELLOW + "0");

        // Set to line 3 from top
        // This chatcolor must match the addentry chat color to display
        objective.getScore(ChatColor.AQUA.toString()).setScore(3);

        /*****************************************************************************
         * DISPLAY SCOREBOARD
         *****************************************************************************/
        // Show the scoreboard
        player.setScoreboard(board);
    }

    /*****************************************************************************
     * ONBLOCKPLACE
     *****************************************************************************/
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        // Get current player instance
        Player player = event.getPlayer();

        long amount = playerStats.getBlocksPlaced();
        amount++;

        // Update brocksplaced amount in PlayerStats class
        playerStats.setBlocksPlaced(amount);

        // Update scoreboard display
        player.getScoreboard().getTeam("blocksplaced").setSuffix(ChatColor.YELLOW.toString() + amount);
    }

    /*****************************************************************************
     * ONBLOCKBREAK
     ****************************************************************************/
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Get current player instance
        Player player = event.getPlayer();

        // Track the amount of blocks broken
        long amount = playerStats.getBlocksBroken();
        amount++;

        // Update brocksbroken amount in PlayerStats
        playerStats.setBlocksBroken(amount);

        // Update scoreboard display
        player.getScoreboard().getTeam("blocksbroken").setSuffix(ChatColor.YELLOW.toString() + amount);
    }

    /*****************************************************************************
     * ONPLAYERMOVE
     ****************************************************************************/
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        // Get current player instance
        Player player = e.getPlayer();

        // If the player doesn't actually move from block to block, return and don't do anything
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ()) {
            return;
        } else {
            // Update steps by one block
            long amount = playerStats.getSteps();
            amount++;

            // Update steps amount in PlayerStats class
            playerStats.setSteps(amount);

            // Update scoreboard display
            player.getScoreboard().getTeam("steps").setSuffix(ChatColor.YELLOW.toString() + amount);
        }
    }
}
