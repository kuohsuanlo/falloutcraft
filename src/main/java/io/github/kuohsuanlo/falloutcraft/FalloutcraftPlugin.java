
package io.github.kuohsuanlo.falloutcraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    private final FalloutcraftPlayerListener playerListener = new FalloutcraftPlayerListener(this);
    private final FalloutcraftBlockListener blockListener = new FalloutcraftBlockListener();
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    protected HashMap<String, Float> falloutstatsHunger = new HashMap<String, Float>();
    protected HashMap<String, Float> falloutstatsThirst = new HashMap<String, Float>();
    protected HashMap<String, String> falloutstatsThirstFromDB = new HashMap<String, String>();
    protected HashMap<String, Float> falloutstatsFatigue = new HashMap<String, Float>();
    protected HashMap<String, String> falloutstatsFatigueFromDB = new HashMap<String, String>();
    protected HashMap<String, Float> falloutstatsRadiation = new HashMap<String, Float>();
    protected HashMap<String, String> falloutstatsRadiationFromDB = new HashMap<String, String>();
    
    protected String pathOfFalloutcraftDB_Radiation="FalloutcraftDB_Radiation";
    protected String pathOfFalloutcraftDB_Thirst="FalloutcraftDB_Thirst";
    @Override
    public void onDisable() {
        try {
      	   	String path = pathOfFalloutcraftDB_Radiation;
      	   	File file = new File(path);
     	    file.createNewFile();
     	   
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(String p:falloutstatsRadiation.keySet()){
                bw.write(p + "\t" + falloutstatsRadiation.get(p));
                bw.newLine();
            }
            bw.flush();
 			bw.close();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        try {
      	   	String path = pathOfFalloutcraftDB_Thirst;
      	   	File file = new File(path);
     	    file.createNewFile();
     	   
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(String p:falloutstatsThirst.keySet()){
                bw.write(p + "\t" + falloutstatsThirst.get(p));
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
        getCommand("debug").setExecutor(new FalloutcraftDebugCommand(this));

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
        
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
        
        
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new  Runnable(){
        	public void run(){
        		Player[] nowList = getServer().getOnlinePlayers().clone();
                for(int i=0;i<nowList.length;i++){
                	playerListener.handleRadiationEffect(nowList[i], falloutstatsRadiation.get(nowList[i].getPlayerListName()).floatValue());
                	playerListener.handleThirstEffect(nowList[i], falloutstatsThirst.get(nowList[i].getPlayerListName()).floatValue());
                    
                }
        	}},0,150);
        
        
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
