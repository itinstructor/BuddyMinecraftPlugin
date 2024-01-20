package com.billthecomputerguy.buddy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

// public class RandomBlockCommand implements CommandExecutor {
public class RandomBlockCommand {
    private final List<Material> highValueBlocks = Arrays.asList(
            Material.DIAMOND_BLOCK,
            Material.EMERALD_BLOCK,
            Material.GOLD_BLOCK,
            Material.OBSIDIAN,
            Material.COAL_BLOCK,
            Material.ELYTRA
    );

    private final Random random = new Random();

    // Create Player reference to reference player from other class
    private final Player player;

    public RandomBlockCommand(Player player) {
        this.player = player;
    }

    /****************************************************************************
     * RANDOMBLOCK: Give player a random high value block, or not
     */
    public boolean randomBlock() {
        // 50% chance of getting a block
        if (random.nextDouble() < 0.5) {
            // Get a random block from the highValueBlocks list
            Material randomBlock;
            randomBlock = highValueBlocks.get(random.nextInt(highValueBlocks.size()));

            // Create Block ItemStack using randomBlock
            ItemStack blockItem;
            blockItem = new ItemStack(randomBlock, 1);

            // Add block to player's inventory
            this.player.getInventory().addItem(blockItem);
            String playerMessage;
            playerMessage = ChatColor.GREEN + "Congratulations! You received a random high-value block: " + ChatColor.YELLOW + randomBlock;
            this.player.sendMessage(playerMessage);
            this.player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        } else {
            this.player.sendMessage(ChatColor.RED + "Better luck next time! You didn't receive any blocks.");
        }
        return true;
    }
}
