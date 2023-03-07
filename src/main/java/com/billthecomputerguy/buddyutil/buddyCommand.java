package com.billthecomputerguy.buddyutil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class buddyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Is the command sender a Player?
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                // Test the first argument for a command
                if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage("Use /buddy tpspawn (tp to world spawnpoint)");
                } else if (args[0].equalsIgnoreCase("tpspawn")) {
                    // Get current world spawn location, teleport player to world spawn
                    player.teleport(player.getWorld().getSpawnLocation());
                    player.sendMessage("Teleported to World Spawn");
                } else {
                    player.sendMessage("Use /buddy tpspawn (tp to world spawnpoint)");
                }
            }
        }
        return false;
    }
}