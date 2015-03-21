package io.github.kuohsuanlo.falloutcraft;

import java.util.ArrayList;

import org.bukkit.entity.Player;

class SyncPlayerTask_FOCraft_StatusUpdate implements Runnable {
	public ArrayList<Player> MaintainedList = new ArrayList<Player>();
	private int sleepTime =0;
	private FalloutcraftPlugin focraft ;
	//public int fatigueCounter = 0;
	//public int fatigueCounter_MAX = 70;
    public SyncPlayerTask_FOCraft_StatusUpdate(int s,FalloutcraftPlugin f) {
    	sleepTime = s;
    	focraft = f;
    }
	public void addPlayer(Player player){
		MaintainedList.add(player);
		
	}
	public void delPlayer(Player player){
		MaintainedList.remove(player);
		
	}

    public void run() {
    	//focraft.getServer().broadcastMessage("SyncPlayerTask_FOCraft_StatusUpdate");
    	focraft.getServer().dispatchCommand(focraft.getServer().getConsoleSender(), "foupdate");

         
    }

}
