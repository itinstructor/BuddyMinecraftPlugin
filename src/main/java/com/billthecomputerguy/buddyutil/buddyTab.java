package com.billthecomputerguy.buddyutil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class buddyTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String label,
            String[] args) {

        // First command argument
        if (args.length == 1) {
            // A search type function that only shows the partial matches
            // As you type a character, the ones that don't match are eliminated
            return StringUtil.copyPartialMatches(
                    args[0],
                    Arrays.asList("help", "tpspawn"),
                    new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
