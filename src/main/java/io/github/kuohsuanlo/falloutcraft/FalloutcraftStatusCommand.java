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


	        if (sender instanceof Player ) {
	        	if(sender.hasPermission("falloutcraft.fostatus")){
	        		sender.sendMessage(listener.FALLOUTCRART_STATUS);
					sender.sendMessage(listener.FALLOUTCRART_R_LEVEL_STATUS+ plugin.falloutstatsRadiation.get(((Player)sender).getPlayerListName())+"/1000");
					sender.sendMessage(listener.FALLOUTCRART_D_LEVEL_STATUS+ plugin.falloutstatsThirst.get(((Player)sender).getPlayerListName())+"/1000");
					sender.sendMessage(listener.FALLOUTCRART_T_LEVEL_STATUS+ plugin.falloutstatsFatigue.get(((Player)sender).getPlayerListName())+"/1000");
	        	}
	        	else{
	        		sender.sendMessage("¡±cYou don't have the permission.");
	        	}
	        	
				return true;
	        }
	        else{
	        	sender.sendMessage("¡±7cYou must be a player to inspect your FOStatus!");
	        	return false;
	        }

		}
		else if (cmd.getName().equalsIgnoreCase("fosetf")) { // If the player typed /basic then do the following...
	        if (args.length == 2 ) {
	        	if (sender.hasPermission("falloutcraft.setfatigue")){
		            if (Integer.valueOf(args[1]) >=0  &&  Integer.valueOf(args[1])  <=1000){
		            	if(plugin.falloutstatsFatigue.containsKey(args[0])){
		            		plugin.falloutstatsFatigue.put(args[0], (float) Integer.valueOf(args[1]));
		            		sender.sendMessage("¡±cSuccessfully set player "+args[0]+" 's fatigue to "+args[1]);
		            		return true;
		            	}
		            	else{
		            		sender.sendMessage("¡±cNo such user.");
		            	}
		            	
		            }
	        	}
	        	else{
	        		sender.sendMessage("¡±cYou don't have the permission.");
	        		return false;
	        	}
	        }
		}
		else if (cmd.getName().equalsIgnoreCase("fosetd")) { // If the player typed /basic then do the following...
	        if (args.length == 2 ) {
	        	if (sender.hasPermission("falloutcraft.setdehydration")){
		            if (Integer.valueOf(args[1]) >=0  &&  Integer.valueOf(args[1])  <=1000){
		            	if(plugin.falloutstatsThirst.containsKey(args[0])){
		            		plugin.falloutstatsThirst.put(args[0], (float) Integer.valueOf(args[1]));
		            		sender.sendMessage("¡±cSuccessfully set player "+args[0]+" 's dehydration to "+args[1]);
		            		return true;
		            	}
		            	else{
		            		sender.sendMessage("¡±cNo such user.");
		            	}
		            	
		            }
	        	}
	        	else{
	        		sender.sendMessage("¡±cYou don't have the permission.");
	        		return false;
	        	}
	        }
		}
		else if (cmd.getName().equalsIgnoreCase("fosetr")) { // If the player typed /basic then do the following...
	        if (args.length == 2 ) {
	        	if (sender.hasPermission("falloutcraft.setradiation")){
		            if (Integer.valueOf(args[1]) >=0  &&  Integer.valueOf(args[1])  <=1000){
		            	if(plugin.falloutstatsRadiation.containsKey(args[0])){
		            		plugin.falloutstatsRadiation.put(args[0], (float) Integer.valueOf(args[1]));
		            		sender.sendMessage("¡±cSuccessfully set player "+args[0]+" 's radiation to "+args[1]);
		            		return true;
		            	}
		            	else{
		            		sender.sendMessage("¡±cNo such user.");
		            	}
		            	
		            }
	        	}
	        	else{
	        		sender.sendMessage("¡±cYou don't have the permission.");
	        		return false;
	        	}
	        }
		}
		sender.sendMessage("Wrong usage");
		return false;
	}
}