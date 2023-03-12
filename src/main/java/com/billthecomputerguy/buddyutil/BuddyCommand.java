/*****************************************
 * Name: buddyCommand.java
 * Written by:
 * Written on:
 * Purpose: Parse Buddy command arguments
 *****************************************/
package com.billthecomputerguy.buddyutil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuddyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Is the command sender a Player
        if (sender instanceof Player) {
            // Create player instance of current player
            Player player = (Player) sender;
            if (args.length == 1) {
                // Test the first argument for a command
                if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage("Use /buddy tpspawn (teleport to world spawnpoint)");
                    player.sendMessage("Go to lab.wncc.net for world maps.)");
                } else if (args[0].equalsIgnoreCase("tpspawn")) {
                    // Get current world spawn location
                    // teleport player to world spawn
                    player.teleport(player.getWorld().getSpawnLocation());
                    player.sendMessage("Teleported to World Spawn");
                } else {
                    // If the command did not have an argument
                    player.sendMessage("Use /buddy tpspawn (teleport to world spawnpoint)");
                    player.sendMessage("Go to lab.wncc.net for world maps.)");
                }
            }
        }
        return false;
    }
}