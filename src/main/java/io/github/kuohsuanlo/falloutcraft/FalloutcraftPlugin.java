
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

/**
 * Sample plugin for Bukkit
 *
 * @author Dinnerbone
 */
public class FalloutcraftPlugin extends JavaPlugin {
	protected final FalloutcraftPlayerListener playerListener = new FalloutcraftPlayerListener(this);
    private final FalloutcraftBlockListener blockListener = new FalloutcraftBlockListener();
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    protected HashMap<String, Float> falloutstatsHunger = new HashMap<String, Float>();
    protected HashMap<String, Float> falloutstatsThirst = new HashMap<String, Float>();
    protected HashMap<String, String> falloutstatsThirstFromDB = new HashMap<String, String>();
    protected HashMap<String, Float> falloutstatsFatigue = new HashMap<String, Float>();
    protected HashMap<String, String> falloutstatsFatigueFromDB = new HashMap<String, String>();
    protected HashMap<String, Float> falloutstatsRadiation = new HashMap<String, Float>();
    protected HashMap<String, String> falloutstatsRadiationFromDB = new HashMap<String, String>();
    
    protected String pathOfFalloutcraftDB_Fatigue="./plugins/Falloutcraft/FalloutcraftDB_Fatigue";
    protected String pathOfFalloutcraftDB_Radiation="./plugins/Falloutcraft/FalloutcraftDB_Radiation";
    protected String pathOfFalloutcraftDB_Thirst="./plugins/Falloutcraft/FalloutcraftDB_Thirst";
    
    protected SyncPlayerTask_FOCraft_StatusUpdate BukkitSchedulerSuck;
    @Override
    public void onDisable() {
        try {
      	   	File file = new File(pathOfFalloutcraftDB_Radiation);
     	    file.createNewFile();
     	   

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            /*
            for(String p:plugin.falloutstatsRadiation.keySet()){
                bw.write(p + "\t" + plugin.falloutstatsRadiation.get(p));
                bw.newLine();
            }*/
      	   for (Iterator<Entry<String, Float>> i = falloutstatsRadiation.entrySet().iterator(); i.hasNext();) {
      		   Map.Entry<String, Float> entry = i.next();
      		   
               bw.write(entry.getKey() + "\t" + entry.getValue());
               bw.newLine();
     	    }
     	    
            bw.flush();
 			bw.close();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        
        try {
      	   	File file = new File(pathOfFalloutcraftDB_Thirst);
     	    file.createNewFile();
     	   
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
       	   for (Iterator<Entry<String, Float>> i = falloutstatsThirst.entrySet().iterator(); i.hasNext();) {
      		   Map.Entry<String, Float> entry = i.next();
               bw.write(entry.getKey() + "\t" + entry.getValue());
               bw.newLine();
     	    }
            bw.flush();
 			bw.close();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        
        try {
      	   	File file = new File(pathOfFalloutcraftDB_Fatigue);
     	    file.createNewFile();
     	   
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for (Iterator<Entry<String, Float>> i = falloutstatsFatigue.entrySet().iterator(); i.hasNext();) {
	      		   Map.Entry<String, Float> entry = i.next();
	               bw.write(entry.getKey() + "\t" + entry.getValue());
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
    public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any events

        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerListener, this);
        pm.registerEvents(blockListener, this);

        // Register our commands
        getCommand("fostatus").setExecutor(new FalloutcraftStatusCommand(this));
        getCommand("foupdate").setExecutor(new FalloutcraftEffectCommand(this));
        getCommand("debug").setExecutor(new FalloutcraftDebugCommand(this));

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
        
        new File("./plugins/Falloutcraft").mkdirs();
        
      
        
        
        try {
      	   	String path = pathOfFalloutcraftDB_Radiation;
      	   	File file = new File(path);
      	   	if(file.exists()){
      	   		BufferedReader in = new BufferedReader(new FileReader(path));
      	   		String line = "";
      	   		while ((line = in.readLine()) != null) {
      	   			String parts[] = line.split("\t");
      	   			falloutstatsRadiation.put(parts[0], Float.valueOf(parts[1]));
      	   		}
      	   		in.close();
      	   	}
            

   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
        
        try {
      	   	String path = pathOfFalloutcraftDB_Thirst;
      	   	File file = new File(path);
      	   	if(file.exists()){
      	   		BufferedReader in = new BufferedReader(new FileReader(path));
      	   		String line = "";
      	   		while ((line = in.readLine()) != null) {
      	   			String parts[] = line.split("\t");
      	   			falloutstatsThirst.put(parts[0], Float.valueOf(parts[1]));
      	   		}
      	   		in.close();
      	   	}
            

   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
        
        try {
      	   	String path = pathOfFalloutcraftDB_Fatigue;
      	   	File file = new File(path);
      	   	if(file.exists()){
      	   		BufferedReader in = new BufferedReader(new FileReader(path));
      	   		String line = "";
      	   		while ((line = in.readLine()) != null) {
      	   			String parts[] = line.split("\t");
      	   			falloutstatsFatigue.put(parts[0], Float.valueOf(parts[1]));
      	   		}
      	   		in.close();
      	   	}
            

   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
        BukkitSchedulerSuck = new SyncPlayerTask_FOCraft_StatusUpdate(0,this);
    	//player.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SyncPlayerTask_FOCraft(player,plugin), 1);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, BukkitSchedulerSuck, 0, 20);

        

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
