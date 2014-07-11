package io.github.kuohsuanlo.falloutcraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Handler for the /debug sample command.
 * @author SpaceManiac
 */
public class FalloutcraftDebugCommand implements CommandExecutor {
    private final FalloutcraftPlugin plugin;

    public FalloutcraftDebugCommand(FalloutcraftPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            plugin.setDebugging(player, !plugin.isDebugging(player));

            return true;
        } else {
            return false;
        }
    }
}
