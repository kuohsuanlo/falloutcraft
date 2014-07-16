
package io.github.kuohsuanlo.falloutcraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Handler for the /pos sample command.
 * @author SpaceManiac
 */
public class FalloutcraftStatusCommand implements CommandExecutor {
    private final FalloutcraftPlugin plugin;

    public FalloutcraftStatusCommand(FalloutcraftPlugin plugin) {
        this.plugin = plugin;
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("fostatus")) { // If the player typed /basic then do the following...
	        if (sender instanceof Player) {
	        	sender.sendMessage("§7-----------§2廢土輻射身體狀態§7-----------");
				sender.sendMessage("你目前的§c輻射計量§f:"+ plugin.falloutstatsRadiation.get(((Player)sender).getPlayerListName())+"/1000");
				sender.sendMessage("你目前的§3口渴程度§f:"+ plugin.falloutstatsThirst.get(((Player)sender).getPlayerListName())+"/1000");
				sender.sendMessage("你目前的§e疲倦程度§f:"+ plugin.falloutstatsFatigue.get(((Player)sender).getPlayerListName())+"/1000");
				
				return true;
	        }
	        else{
	        	return false;
	        }

		} else if (cmd.getName().equalsIgnoreCase("foreload")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
			} else {
				Player player = (Player) sender;
				// do something
			}
			return true;
		}
		return false;
	}
}
