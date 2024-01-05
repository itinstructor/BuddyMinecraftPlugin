package com.billthecomputerguy.buddyutil.player_data;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    // HashMap to store custom player data in memory
    private HashMap<UUID, CustomPlayer> customPlayers = new HashMap<>();

    public CustomPlayer getCustomPlayer(UUID uuid) {
        return customPlayers.get(uuid);
    }

    public void addCustomPlayer(UUID uuid, CustomPlayer player) {
        // Add player data to hashmap
        customPlayers.put(uuid, player);
    }

    public void removeCustomPlayer(UUID uuid) {
        // Remove player data from hashmap
        customPlayers.remove(uuid);
    }
}
