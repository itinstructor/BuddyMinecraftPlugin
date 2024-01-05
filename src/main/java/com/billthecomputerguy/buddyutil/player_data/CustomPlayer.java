package com.billthecomputerguy.buddyutil.player_data;

import com.billthecomputerguy.buddyutil.Main;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CustomPlayer {
    private Main main;
    private UUID uuid;
    private String displayName;
    private long steps;
    private long blocksBroken;
    private long blocksPlaced;
    private Database database;

    public CustomPlayer(Main main, UUID uuid, String displayName) throws SQLException {
        this.main = main;
        this.uuid = uuid;
        this.displayName = displayName;

        // Try to retrieve player information from database
        String SQL = "SELECT STEPS, BLOCKS_BROKEN, BLOCKS_PLACED FROM players WHERE UUID = ?;";
        PreparedStatement ps = main.getDatabase().getConnection().prepareStatement(SQL);
        ps.setString(1, uuid.toString());
        ResultSet rs = ps.executeQuery();
        // Is there a player record coming back in the ResultSet
        if (rs.next()) {
            // Load record for current player into class attributes
            this.steps = rs.getLong("STEPS");
            this.blocksBroken = rs.getLong("BLOCKS_BROKEN");
            this.blocksPlaced = rs.getLong("BLOCKS_PLACED");

        } else {
            // Create new record in players table with default values for new player
            this.steps = 0L;
            this.blocksBroken = 0L;
            this.blocksPlaced = 0L;
            SQL = "INSERT INTO players (ID, UUID, DISPLAY_NAME, STEPS, BLOCKS_BROKEN, BLOCKS_PLACED) VALUES(" +
                    "default," +
                    "'" + this.uuid + "'," +
                    "'" + this.displayName + "'," +
                    "'" + this.steps + "'," +
                    "'" + this.blocksBroken + "'," +
                    this.blocksPlaced + ");";
            ps = main.getDatabase().getConnection().prepareStatement(SQL);
            ps.executeUpdate();
            // Log to console successful addition of player to database
            Bukkit.getServer().getLogger().info("Buddy Util: " + displayName + " successfully added to database.");
        }
    }

    public void setSteps(long steps) {
        this.steps = steps;
        // Update steps in the database for this player
        try {
            PreparedStatement ps = main.getDatabase().getConnection().prepareStatement("UPDATE players SET STEPS = '" +
                    steps + "' WHERE UUID = '" + this.uuid + "';");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBlocksBroken(long blocksBroken) {
        this.blocksBroken = blocksBroken;
        // Update blocksBroken in the database for this player
        try {
            PreparedStatement ps = main.getDatabase().getConnection().prepareStatement("UPDATE players SET BLOCKS_BROKEN = '" +
                    blocksBroken + "' WHERE UUID = '" + this.uuid + "';");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBlocksPlaced(long blocksPlaced) {
        this.blocksPlaced = blocksPlaced;
        // Update blocksBroken in the database for this player
        try {
            PreparedStatement ps = main.getDatabase().getConnection().prepareStatement("UPDATE players SET BLOCKS_PLACED = '" +
                    blocksPlaced + "' WHERE UUID = '" + this.uuid + "';");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getSteps() {
        return this.steps;
    }

    public long getBlocksBroken() {
        return this.blocksBroken;
    }

    public long getBlocksPlaced() {
        return this.blocksPlaced;
    }
}
