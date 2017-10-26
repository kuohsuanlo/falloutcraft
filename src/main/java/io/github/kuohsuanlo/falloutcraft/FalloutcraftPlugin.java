
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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Sample plugin for Bukkit
 *
 * @author Dinnerbone
 */
public class FalloutcraftPlugin extends JavaPlugin {
	protected final FalloutcraftPlayerListener playerListener = new FalloutcraftPlayerListener(this);
    private final FalloutcraftBlockListener blockListener = new FalloutcraftBlockListener();
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    private final FalloutcraftStatusCommand commander = new FalloutcraftStatusCommand(this);
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
    
    protected FalloutcraftSyncTaskPlayerStatusUpdate BukkitSchedulerSuck;
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
    private void addRecipe(){
    	
		///////////////////////////////  Radaway making
		ItemStack bottle = new ItemStack(Material.POTION);
		
		ItemMeta bottle_meta = bottle.getItemMeta();
		PotionMeta potion_meta =(PotionMeta) bottle_meta;
		
		bottle_meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true) ;
		potion_meta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1, 1), true);

		bottle_meta.setDisplayName(ChatColor.DARK_GREEN+"Radaway"+ChatColor.RESET);	
		bottle.setItemMeta(bottle_meta);
		
		ShapedRecipe radaway = new ShapedRecipe(bottle);

		////////////////////////////////
		radaway.shape("* ","B ");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle);
		radaway.shape("* "," B");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle);
		radaway.shape(" *","B ");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle);
		radaway.shape(" *"," B");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle);
		radaway.shape("B ","* ");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle);
		radaway.shape("B "," *");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle);
		radaway.shape(" B","* ");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle);
		radaway.shape(" B"," *");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle);
		radaway.shape("B*","  ");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);

		radaway = new ShapedRecipe(bottle);
		radaway.shape("*B","  ");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle);
		radaway.shape("  ","*B");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);

		radaway = new ShapedRecipe(bottle);
		radaway.shape("* ","B*");
		radaway.setIngredient('*', Material.INK_SACK,15);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		
		
		///////////////////////////////  Radaway Compact making
		ItemStack bottle2 = new ItemStack(Material.POTION);
		ItemMeta bottle_meta_2 = bottle2.getItemMeta();
		PotionMeta potion_meta_2 =(PotionMeta) bottle_meta_2;
		
		bottle_meta_2.addEnchant(Enchantment.FIRE_ASPECT, 2, true) ;
		potion_meta_2.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1, 1), true);
		

		bottle_meta_2.setDisplayName(ChatColor.DARK_GREEN+"Radaway-"+ChatColor.GREEN+"Double"+ChatColor.RESET);	

		bottle2.setItemMeta(bottle_meta_2);
		
		radaway = new ShapedRecipe(bottle2);
		
		////////////////////////////////
		radaway.shape("* ","B ");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle2);
		radaway.shape("* "," B");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle2);
		radaway.shape(" *","B ");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle2);
		radaway.shape(" *"," B");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle2);
		radaway.shape("B ","* ");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle2);
		radaway.shape("B "," *");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle2);
		radaway.shape(" B","* ");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle2);
		radaway.shape(" B"," *");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle2);
		radaway.shape("B*","  ");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);

		radaway = new ShapedRecipe(bottle2);
		radaway.shape("*B","  ");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle2);
		radaway.shape("  ","*B");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);

		radaway = new ShapedRecipe(bottle2);
		radaway.shape("* ","B*");
		radaway.setIngredient('*', Material.BONE);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		///////////////////////////
		
				 
		///////////////////////////////  Radaway Compact making
		ItemStack bottle3 = new ItemStack(Material.POTION);
		ItemMeta bottle_meta_3 = bottle3.getItemMeta();
		PotionMeta potion_meta_3 =(PotionMeta) bottle_meta_3;
		
		bottle_meta_3.addEnchant(Enchantment.FIRE_ASPECT, 4, true) ;
		potion_meta_3.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1, 1), true);
		
		bottle_meta_3.setDisplayName("q2Radaway-qaIVqf");	
		bottle3.setItemMeta(bottle_meta_3);
		
		radaway = new ShapedRecipe(bottle3);
		
		////////////////////////////////
		radaway.shape("* ","B ");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle3);
		radaway.shape("* "," B");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle3);
		radaway.shape(" *","B ");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle3);
		radaway.shape(" *"," B");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle3);
		radaway.shape("B ","* ");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle3);
		radaway.shape("B "," *");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle3);
		radaway.shape(" B","* ");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle3);
		radaway.shape(" B"," *");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle3);
		radaway.shape("B*","  ");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle3);
		radaway.shape("*B","  ");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle3);
		radaway.shape("  ","*B");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		radaway = new ShapedRecipe(bottle3);
		radaway.shape("* ","B*");
		radaway.setIngredient('*', Material.BONE_BLOCK);
		radaway.setIngredient('B', Material.POTION);
		getServer().addRecipe(radaway);
		
		///////////////////////////
				
	}
    @Override
    public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any events
    	this.addRecipe();
    	
    	 
        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerListener, this);
        pm.registerEvents(blockListener, this);
        
        if (pm.isPluginEnabled("PlaceholderAPI")) {
        	new AddPlaceholder(this).hook();
        } else {
            throw new RuntimeException("Could not find PlaceholderAPI!! Plugin can not work without it!");
        }
        
        // Register our commands
        this.getCommand("fostatus").setExecutor(commander);
        
        this.getCommand("fosetf").setExecutor(commander);
        this.getCommand("fosetr").setExecutor(commander);
        this.getCommand("fosetd").setExecutor(commander);
        
        this.getCommand("foupdate").setExecutor(new FalloutcraftEffectCommand(this));

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
        BukkitSchedulerSuck = new FalloutcraftSyncTaskPlayerStatusUpdate(0,this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, BukkitSchedulerSuck, 0, 20);

        

    }
    /*
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("TitleAPI") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }*/
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
