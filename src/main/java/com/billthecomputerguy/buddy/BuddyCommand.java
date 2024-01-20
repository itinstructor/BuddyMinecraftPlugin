package com.billthecomputerguy.buddy;
/**
 * Name: BuddyCommand.java
 * Written by:
 * Written on:
 * Purpose: Parse Buddy command arguments
 */

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BuddyCommand implements CommandExecutor {
    private final Main plugin; // Reference to access Main class
    private RandomBlockCommand randomBlockCommand;
    // Create a cooldown cache (Like a hashmap) to store the UUID of the player and the cooldown time
    // This is a hashmap type cache that has an automatic time limit
    private Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(this.cooldownTime, TimeUnit.SECONDS).build();
    private final long cooldownTime = 120;

    public BuddyCommand(Main plugin) {
        this.plugin = plugin;
    }  // Reference to Main class with plugin

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        // Is the command sender a Player?
        if (commandSender instanceof Player) {
            // Create player instance of current player, cast to sender (the source of the command)
            Player player = (Player) commandSender;

            // Test the first argument for a command
            if (args.length == 1) {
                /**************************************************************************
                 * HELP
                 * Show buddy command help
                 */
                if (args[0].equalsIgnoreCase("help")) {
                    // Display buddy command help
                    displayHelp(player);
                    /****************************************************************************
                     * TPSPAWN
                     * Teleport to world spawn location
                     */
                } else if (args[0].equalsIgnoreCase("tpspawn")) {
                    // Get current world spawn location, teleport player to world spawn
                    player.teleport(player.getWorld().getSpawnLocation());
                    player.sendMessage(ChatColor.GOLD + "Teleported to World Spawn.");
                    player.playSound(player.getLocation(), Sound.BLOCK_CONDUIT_ACTIVATE, // Minecraft sound
                            1.0F,  // Controls how far away the sound is heard. 1.0 just the player
                            2.0F   // Controls how fast the sound plays, .5 half speed, 2.0 twice speed, 1.0 normal
                    );

                    /****************************************************************************
                     * DOCTOR
                     * Run this command to access the heal command
                     */
                } else if (args[0].equalsIgnoreCase("doctor")) {
                    // Create chat message to click to heal the player
                    TextComponent message = new TextComponent(("Would you like to be healed? (Click here)"));
                    message.setColor(ChatColor.GOLD);
                    message.setBold(true);
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/buddy heal"));
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here to be healed!").color(ChatColor.GRAY).italic(true).create()));

                    // Have spigot send the textcomponent message to the player
                    player.spigot().sendMessage(message);
                    return true;

                    /****************************************************************************
                     * HEAL
                     * Heal the player
                     */
                } else if (args[0].equalsIgnoreCase("heal")) {
                    // Set player health to 20 (100%);
                    player.setHealth(20.0);
                    player.sendMessage(ChatColor.GREEN + "Your health has been restored!");
                    return true;

                    /****************************************************************************
                     * JUMP
                     * Player jumps up and forward
                     */
                } else if (args[0].equalsIgnoreCase("jump")) {
                    // Player jumps up and forward
                    jump(player);
                    return true;

                    /****************************************************************************
                     * RANDOMBLOCK:
                     * Give player a random high value block, or not
                     */
                } else if (args[0].equalsIgnoreCase("randomblock")) {
                    // If the player is not in the cooldown cache
                    if (!cooldown.asMap().containsKey(player.getUniqueId())) {

                        // Give player a random high value block, or not.
                        randomBlockCommand = new RandomBlockCommand(player);
                        randomBlockCommand.randomBlock();

                        // Put player in cooldown, add time in cooldown to current Sytstem time in milliseconds
                        // Store cooldown time with player in Millseconds
                        cooldown.put(player.getUniqueId(), System.currentTimeMillis() + (cooldownTime * 500));
                        return true;
                    } else {
                        // Calculate how much time is left in the player cooldown
                        long distance = cooldown.asMap().get(player.getUniqueId()) - System.currentTimeMillis();

                        // Player is in the cooldown Cache, send them a message with the time left in the cooldown
                        // player.sendMessage(org.bukkit.ChatColor.RED + "You must wait " + TimeUnit.MILLISECONDS.toSeconds(distance) + " seconds to gamble again.");
                        player.sendMessage(org.bukkit.ChatColor.RED + "You must wait " + formatTime(distance) + " seconds to gamble again.");
                        return true;
                    }
                } else {
                    // If there aren't any arguments, display buddy command help
                    displayHelp(player);
                }
            }
        }
        return false;
    }

    /****************************************************************************
     DISPLAY BUDDY COMMAND HELP
     */
    private void displayHelp(Player player) {
        player.sendMessage(ChatColor.BLUE + "Use /playsong to play a song");
        player.sendMessage(ChatColor.BLUE + "Use /buddy doctor (health)");
        player.sendMessage(ChatColor.BLUE + "Use /buddy jump (jump forward and up in the air!)");
        player.sendMessage(ChatColor.BLUE + "Use /buddy randomblock (receive random high value block)");
        player.sendMessage(ChatColor.BLUE + "Use /buddy tpspawn (teleport to world spawnpoint)");
    }

    /****************************************************************************
     JUMP
     Jump into the air and move forward
     */
    private void jump(Player player) {
        player.sendMessage(org.bukkit.ChatColor.LIGHT_PURPLE + "" + org.bukkit.ChatColor.BOLD + " W H O O S H ! ! !");
        // Default launch configuration forward multiplier 2, Y (up) of 2 blocks
        player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
        // Play sound while jumping
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, // Minecraft sound
                1.0F,  // Controls how far away the sound is heard. 1.0 just the player
                1.5F   // Controls how fast the sound plays, .5 half speed, 2.0 twice speed, 1.0 normal
        );
    }

    /****************************************************************************
     FORMAT TIME
     Format Minecraft time into 12 hour clock
     */
    private String formatTime(long millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        return String.format("%d minutes and %d seconds", minutes, seconds);
    }
}