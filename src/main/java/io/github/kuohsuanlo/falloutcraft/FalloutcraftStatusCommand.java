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
    private final FalloutcraftPlayerListener listener;
    public FalloutcraftStatusCommand(FalloutcraftPlugin plugin) {
        this.plugin = plugin;
        this.listener = plugin.playerListener;
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("fostatus")) { // If the player typed /basic then do the following...
	        if (sender instanceof Player) {
	        	sender.sendMessage(listener.FALLOUTCRART_STATUS);
				sender.sendMessage(listener.FALLOUTCRART_R_LEVEL_STATUS+ plugin.falloutstatsRadiation.get(((Player)sender).getPlayerListName())+"/1000");
				sender.sendMessage(listener.FALLOUTCRART_D_LEVEL_STATUS+ plugin.falloutstatsThirst.get(((Player)sender).getPlayerListName())+"/1000");
				sender.sendMessage(listener.FALLOUTCRART_T_LEVEL_STATUS+ plugin.falloutstatsFatigue.get(((Player)sender).getPlayerListName())+"/1000");

				return true;
	        }
	        else{
	        	return false;
	        }

		}
		return false;
	}
}