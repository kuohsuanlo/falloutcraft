package io.github.kuohsuanlo.falloutcraft;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;



class SyncPlayerTask_FOCraft implements Runnable {
    Player player;
    FalloutcraftPlugin focraft;
    SyncPlayerTask_FOCraft(Player p,Plugin fo) {
        player = p;
        focraft = (FalloutcraftPlugin)fo;
    }
    public void run() {    
    	//player.sendMessage("SyncPlayerTask_RefreshStatus");
    	focraft.playerListener.handleRadiationEffect(player,focraft.falloutstatsRadiation.get(player.getPlayerListName()));
    	focraft.playerListener.handleThirstEffect(player,focraft.falloutstatsThirst.get(player.getPlayerListName()));
    	focraft.playerListener.handleFatigueEffect(player,focraft.falloutstatsFatigue.get(player.getPlayerListName()));
        
    }
}
class SyncPlayerTask_Sleep implements Runnable {
    Player player;
    FalloutcraftPlugin focraft;
    SyncPlayerTask_Sleep(Player p,Plugin fo) {
        player = p;
        focraft = (FalloutcraftPlugin)fo;
    }
       
    public void run() {  
    	//player.sendMessage("SyncPlayerTask_Sleep");
        for (Iterator<PotionEffect> i =player.getActivePotionEffects().iterator(); i.hasNext();) {
        	PotionEffect e = i.next();
        	player.removePotionEffect(e.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS , 50, 5),true);
		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION , 100, 5),true);
    }
}