
package io.github.kuohsuanlo.falloutcraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Sample plugin for Bukkit
 *
 * @author Dinnerbone
 */
public class FalloutcraftPlugin extends JavaPlugin {
	
    private final FalloutcraftPlayerListener playerListener = new FalloutcraftPlayerListener(this);
    private final FalloutcraftBlockListener blockListener = new FalloutcraftBlockListener();
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    protected HashMap<String, PlayerFOStatus> playerFOOnlineList = new HashMap<String, PlayerFOStatus>();
    //protected final HashMap<String, PlayerFOStatus> playerFOList = new HashMap<String, PlayerFOStatus>();
    
    protected String pathOfFalloutcraftDB="Falloutcraft.db";
    private int fatigueCounter = 0;
    private int effectTicksPerRefresh = 150; 
    @Override
    public void onDisable() {
        try {
      	   	File file = new File(pathOfFalloutcraftDB);
     	    file.createNewFile();
     	   

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            
       	   	for (Iterator<Entry<String, PlayerFOStatus>> i = this.playerFOOnlineList.entrySet().iterator(); i.hasNext();) {
      		   Entry<String, PlayerFOStatus> entry = i.next();
               bw.write(entry.getKey() + "%" + 
            		   	entry.getValue().PlayerThirst + "%" + 
            		   	entry.getValue().PlayerFatigue + "%" + 
            		   	entry.getValue().PlayerRadiation);
               bw.newLine();
     	    }
            
            bw.flush();
 			bw.close();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}

    }

    @Override
    synchronized public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any events

        // Register our events
    	 
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerListener, this);
        pm.registerEvents(blockListener, this);

        // Register our commands
        getCommand("fostatus").setExecutor(new FalloutcraftStatusCommand(this));
        getCommand("debug").setExecutor(new FalloutcraftDebugCommand(this));

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
        
        try {
      	   	String path = pathOfFalloutcraftDB;
      	   	File file = new File(path);
      	   	if(file.exists()){
      	   		BufferedReader in = new BufferedReader(new FileReader(path));
      	   		String line = "";
      	   		while ((line = in.readLine()) != null) {
      	   			String parts[] = line.split("%");
      	   			playerFOOnlineList.put(parts[0],new PlayerFOStatus(parts[1],parts[2],parts[3]));
      	   		}
      	   		in.close();
      	   	}
            

   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
        BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new  Runnable(){
    		@Override
        	public void run(){
        		Player[] nowList = getServer().getOnlinePlayers().clone();
                for(int i=0;i<nowList.length;i++){

                	playerListener.handleRadiationEffect(nowList[i], playerFOOnlineList.get(nowList[i]).PlayerRadiation);
                	playerListener.handleThirstEffect(nowList[i], playerFOOnlineList.get(nowList[i]).PlayerThirst);
                	playerListener.handleFatigueEffect(nowList[i], playerFOOnlineList.get(nowList[i]).PlayerFatigue);
                	
                }
        		fatigueCounter++;
        		if(fatigueCounter>=60){
        			fatigueCounter=0;
            		Player[] fatigueNowList = getServer().getOnlinePlayers().clone();
                    for(int i=0;i<fatigueNowList.length;i++){
                    	if(Math.random()>=0.0){
                    		int fatigueRand = (int) ((Math.random()+0.5)*playerListener.fatiguePerDozen);
                    		playerListener.handleFatigueDozen(fatigueNowList[i].getPlayer(),fatigueRand);
                    	}
                    }
        		}
        		

        	}}, 0L, 20L);
    	
    	 
    }

    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}
