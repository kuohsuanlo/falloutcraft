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
public class FalloutcraftEffectCommand implements CommandExecutor {
    private final FalloutcraftPlugin plugin;
    private final FalloutcraftPlayerListener listener;
    private int fatigueCounter = 0;
    private int fatigueCounter_MAX = 1200;//seconds
    private int statusCounter = 0;
    private int statusCounter_MAX = 20;
    public FalloutcraftEffectCommand(FalloutcraftPlugin plugin) {
        this.plugin = plugin;
        this.listener = plugin.playerListener;
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("foupdate")) {
	        if (!(sender instanceof Player)) {
				//sender.sendMessage("Players in server status updated ");
	        	fatigueCounter++;
	        	statusCounter++;
	        	if(statusCounter > statusCounter_MAX  ){
	        		for(int i=0;i<plugin.BukkitSchedulerSuck.MaintainedList.size();i++){
	        			//Add  if (maintained player in getnowonlineplayer ) do vvvvvv
			      		//plugin.BukkitSchedulerSuck.MaintainedList.get(i).sendMessage("SyncPlayerTask_FOCraft_PlayerLoop");
			      		plugin.playerListener.handleRadiationEffect(plugin.BukkitSchedulerSuck.MaintainedList.get(i),plugin.falloutstatsRadiation.get(plugin.BukkitSchedulerSuck.MaintainedList.get(i).getPlayerListName()));
			      		plugin.playerListener.handleThirstEffect(plugin.BukkitSchedulerSuck.MaintainedList.get(i),plugin.falloutstatsThirst.get(plugin.BukkitSchedulerSuck.MaintainedList.get(i).getPlayerListName()));
			      		plugin.playerListener.handleFatigueEffect(plugin.BukkitSchedulerSuck.MaintainedList.get(i),plugin.falloutstatsFatigue.get(plugin.BukkitSchedulerSuck.MaintainedList.get(i).getPlayerListName()));
	        		}
	        		statusCounter=0;
	        	}
	        	
	  	    	if(fatigueCounter > fatigueCounter_MAX  ){
					//sender.sendMessage("MaintainedList.size() : "+plugin.BukkitSchedulerSuck.MaintainedList.size());
	  	    		for(int i=0;i<plugin.BukkitSchedulerSuck.MaintainedList.size();i++){
			  	    	if( Math.random()>=0.0){
	                		int fatigueRand = (int) ((Math.random()+0.5)*plugin.playerListener.fatiguePerDozen);
	                		plugin.playerListener.handleFatigueDozen(plugin.BukkitSchedulerSuck.MaintainedList.get(i),fatigueRand);
		                	
	                		fatigueCounter=0;
				  	    }
	  	    		}
		        }

				return true;
			}
		}
		return false;
	}
}
