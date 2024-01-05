package com.billthecomputerguy.buddyutil;
/**
 * Name: PlayerStats.java
 * Written by:
 * Written on:
 * Purpose: Store Player Statistics
 */

import java.util.UUID;

public class PlayerStats {
    // Class data attributes
    private UUID uuid;
    // Use Long to allow for large integers, especially for steps
    private long blocksBroken;
    private long blocksPlaced;
    private long steps;

    // Constructor with uuid pararmeter
    public PlayerStats(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public long getBlocksBroken() {
        return blocksBroken;
    }

    public void setBlocksBroken(long blocksBroken) {
        this.blocksBroken = blocksBroken;
    }

    public long getBlocksPlaced() {
        return blocksPlaced;
    }

    public void setBlocksPlaced(long blocksPlaced) {
        this.blocksPlaced = blocksPlaced;
    }

    public long getSteps() {
        return steps;
    }

    public void setSteps(long steps) {
        this.steps = steps;
    }
}
