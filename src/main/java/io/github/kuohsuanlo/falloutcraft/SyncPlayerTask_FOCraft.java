package io.github.kuohsuanlo.falloutcraft;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;


class FOCraftPlayerStatusUpdateTriggerThread extends Thread {
	protected ArrayList<Player> MaintainedList = new ArrayList<Player>();
	private int sleepTime =0;
	private FalloutcraftPlugin focraft ;
	private int fatigueCounter = 0;
	private int fatigueCounter_MAX = 70;
	public void addPlayer(Player player){
		MaintainedList.add(player);
		
	}
	public void delPlayer(Player player){
		MaintainedList.remove(player);
		
	}
    public FOCraftPlayerStatusUpdateTriggerThread(int s,FalloutcraftPlugin f) {

    	sleepTime = s;
    	focraft = f;
    
    	//focraft.getServer().broadcastMessage("constructor in FOCraftPlayerStatusUpdateTriggerThread");
    }
 
    public void run() {
       while(true){
    	   //focraft.getServer().broadcastMessage("while in FOCraftPlayerStatusUpdateTriggerThread");
           try {
        	   	
		      	for(int i=0;i<MaintainedList.size();i++){
		      		//MaintainedList.get(i).sendMessage("SyncPlayerTask_FOCraft_PlayerLoop");
		  	    	focraft.playerListener.handleRadiationEffect(MaintainedList.get(i),focraft.falloutstatsRadiation.get(MaintainedList.get(i).getPlayerListName()));
		  	    	focraft.playerListener.handleThirstEffect(MaintainedList.get(i),focraft.falloutstatsThirst.get(MaintainedList.get(i).getPlayerListName()));
		  	    	focraft.playerListener.handleFatigueEffect(MaintainedList.get(i),focraft.falloutstatsFatigue.get(MaintainedList.get(i).getPlayerListName()));
		  	    	if(fatigueCounter < fatigueCounter_MAX  ){
		  	    		fatigueCounter++;
		  	    	}
		  	    	else{
			  	    	if( Math.random()>=0.0){
	                		int fatigueRand = (int) ((Math.random()+0.5)*focraft.playerListener.fatiguePerDozen);
	                		focraft.playerListener.handleFatigueDozen(MaintainedList.get(i),fatigueRand);
	                	}
		  	    		fatigueCounter=0;
		  	    	}
		       }
		      	
               sleep(sleepTime);
           } catch (InterruptedException e) {
           }

       }
       

     
    }
}

class SyncPlayerTask_FOCraft extends BukkitRunnable {
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
class SyncPlayerTask_Sleep extends BukkitRunnable {
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