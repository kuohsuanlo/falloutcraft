package io.github.kuohsuanlo.falloutcraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Handle events for all Player related events
 * @author Dinnerbone
 */
public class FalloutcraftPlayerListener implements Listener {
    private final FalloutcraftPlugin plugin;
    public FalloutcraftPlayerListener(FalloutcraftPlugin instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //plugin.getLogger().info(event.getPlayer().getName() + " joined the server! :D");
    	
    	Player player = event.getPlayer();
    	if(plugin.falloutstatsRadiation.containsKey(player.getPlayerListName())){
    	}
    	else{
    		plugin.falloutstatsRadiation.put(player.getPlayerListName(), (float) 0);
    	}
    	if(plugin.falloutstatsThirst.containsKey(player.getPlayerListName())){
    	}
    	else{
    		plugin.falloutstatsThirst.put(player.getPlayerListName(), (float) 0);
    	}
    	if(plugin.falloutstatsFatigue.containsKey(player.getPlayerListName())){
    	}
    	else{
    		plugin.falloutstatsFatigue.put(player.getPlayerListName(), (float) 0);
    	}
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        try {
      	   	File file = new File(plugin.pathOfFalloutcraftDB_Radiation);
     	    file.createNewFile();
     	   
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(String p:plugin.falloutstatsRadiation.keySet()){
                bw.write(p + "\t" + plugin.falloutstatsRadiation.get(p));
                bw.newLine();
            }
            bw.flush();
 			bw.close();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        
        try {
      	   	File file = new File(plugin.pathOfFalloutcraftDB_Thirst);
     	    file.createNewFile();
     	   
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(String p:plugin.falloutstatsThirst.keySet()){
                bw.write(p + "\t" + plugin.falloutstatsThirst.get(p));
                bw.newLine();
            }
            bw.flush();
 			bw.close();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        
        try {
      	   	File file = new File(plugin.pathOfFalloutcraftDB_Fatigue);
     	    file.createNewFile();
     	   
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(String p:plugin.falloutstatsFatigue.keySet()){
                bw.write(p + "\t" + plugin.falloutstatsFatigue.get(p));
                bw.newLine();
            }
            bw.flush();
 			bw.close();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    }
    
    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent e) {
    	
    	Player player = e.getPlayer();
    	handleRadiationFoodDozen(player,e.getItem());
    	handleRadiationEffect(player,plugin.falloutstatsRadiation.get(player.getPlayerListName()));
    	
    	handleThirstFoodDozen(player,e.getItem());
    	handleThirstEffect(player,plugin.falloutstatsThirst.get(player.getPlayerListName()));
    }
    private int thirst_apple = -5;
    private int thirst_baked_potato = 40;
    private int thirst_bread = 25;
    private int thirst_carrot = 25;
    private int thirst_raw_fish = -10;
    private int thirst_cooked_chicken = 40;
    private int thirst_cooked_fish = -5;
    private int thirst_cooked_porkchop = 40;
    private int thirst_cookie = 1;
    private int thirst_golen_apple = -10;//curing
    private int thirst_golen_carrot = -10;//curing
    private int thirst_melon = -5;
    private int thirst_mushroom_stew = -10;
    private int thirst_poisonous_potato = 50;
    private int thirst_potato = 10;
    private int thirst_pumpkin_pie = 60;
    private int thirst_raw_beef = 40;
    private int thirst_raw_chicken = 40;
    private int thirst_raw_porkchop = 40;
    private int thirst_rotten_flesh = 60;
    private int thirst_spider_eye = 50;
    private int thirst_steak = 40;
    private int determineFoodThirst(ItemStack i){
    	int randNum = (int) (Math.random()*randomFloat);
    	int dozenNum=0;
    	//dozenNum <0 == curing item
    	if(i.getType().equals(Material.POTION)){
    		if(i.getItemMeta().hasEnchant(Enchantment.FIRE_ASPECT)){
    		}
    		else if(i.getItemMeta().hasEnchant(Enchantment.ARROW_DAMAGE)){
    		}
    		else if(i.getItemMeta().hasEnchant(Enchantment.ARROW_FIRE)){
    		}
    		else if(i.getItemMeta().hasEnchant(Enchantment.ARROW_KNOCKBACK)){
    		}
    		else if(i.getItemMeta().hasEnchants()==false){
    			dozenNum = -(int) ((Math.random()+0.5)*40);
    		};
    	}
    	else if(i.getType().equals(Material.APPLE)){
    		dozenNum = (int) (thirst_apple + randNum*(Math.signum(thirst_apple)));
    	}
    	else if(i.getType().equals(Material.BAKED_POTATO)){
    		dozenNum = (int) (thirst_baked_potato + randNum*(Math.signum(thirst_baked_potato)));
    	}
    	else if(i.getType().equals(Material.BREAD)){
    		dozenNum = (int) (thirst_bread + randNum*(Math.signum(thirst_bread)));
    	}    
    	else if(i.getType().equals(Material.CARROT)){
    		dozenNum = (int) (thirst_carrot + randNum*(Math.signum(thirst_carrot)));
    	} 
    	else if(i.getType().equals(Material.RAW_FISH)){
    		dozenNum = (int) (thirst_raw_fish + randNum*(Math.signum(thirst_raw_fish)));
    	} 
    	else if(i.getType().equals(Material.COOKED_CHICKEN)){
    		dozenNum = (int) (thirst_cooked_chicken + randNum*(Math.signum(thirst_cooked_chicken)));
    	} 
    	else if(i.getType().equals(Material.COOKED_FISH)){
    		dozenNum = (int) (thirst_cooked_fish + randNum*(Math.signum(thirst_cooked_fish)));
    	} 
    	else if(i.getType().equals(Material.GRILLED_PORK)){
    		dozenNum = (int) (thirst_cooked_porkchop + randNum*(Math.signum(thirst_cooked_porkchop)));
    	} 
    	else if(i.getType().equals(Material.COOKIE)){
    		dozenNum = (int) (thirst_cookie + randNum*(Math.signum(thirst_cookie)));
    	} 
    	else if(i.getType().equals(Material.GOLDEN_APPLE)){
    		dozenNum = (int) (thirst_golen_apple + randNum*(Math.signum(thirst_golen_apple)));
    	} 
    	else if(i.getType().equals(Material.GOLDEN_CARROT)){
    		dozenNum = (int) (thirst_golen_carrot + randNum*(Math.signum(thirst_golen_carrot)));
    	}
    	else if(i.getType().equals(Material.MELON)){
    		dozenNum = (int) (thirst_melon + randNum*(Math.signum(thirst_melon)));
    	}
    	else if(i.getType().equals(Material.MUSHROOM_SOUP)){
    		dozenNum = (int) (thirst_mushroom_stew + randNum*(Math.signum(thirst_mushroom_stew)));
    	}
    	else if(i.getType().equals(Material.POISONOUS_POTATO)){
    		dozenNum = (int) (thirst_poisonous_potato + randNum*(Math.signum(thirst_poisonous_potato)));
    	}
    	else if(i.getType().equals(Material.POTATO)){
    		dozenNum = (int) (thirst_potato + randNum*(Math.signum(thirst_potato)));
    	}
    	else if(i.getType().equals(Material.PUMPKIN_PIE)){
    		dozenNum = (int) (thirst_pumpkin_pie + randNum*(Math.signum(thirst_pumpkin_pie)));
    	}
    	else if(i.getType().equals(Material.RAW_BEEF)){
    		dozenNum = (int) (thirst_raw_beef + randNum*(Math.signum(thirst_raw_beef)));
    	}
    	else if(i.getType().equals(Material.RAW_CHICKEN)){
    		dozenNum = (int) (thirst_raw_chicken + randNum*(Math.signum(thirst_raw_chicken)));
    	}
    	else if(i.getType().equals(Material.PORK)){
    		dozenNum = (int) (thirst_raw_porkchop + randNum*(Math.signum(thirst_raw_porkchop)));
    	}
    	else if(i.getType().equals(Material.ROTTEN_FLESH)){
    		dozenNum = (int) (thirst_rotten_flesh + randNum*(Math.signum(thirst_rotten_flesh)));
    	}
    	else if(i.getType().equals(Material.SPIDER_EYE)){
    		dozenNum = (int) (thirst_spider_eye + randNum*(Math.signum(thirst_spider_eye)));
    	}
    	else if(i.getType().equals(Material.COOKED_BEEF)){
    		dozenNum = (int) (thirst_steak + randNum*(Math.signum(thirst_steak)));
    	}
    	else{
    		dozenNum = (int) (Math.random()*30);
    	}
    	return dozenNum;
    	
    }
    
    @EventHandler
    public void onEntityDamagedByEnvironment(EntityDamageEvent e) {
    	Player player;
    	if((!(e.getEntity() instanceof Player))){ // not a player hit , or damage from player;
    		return;
    	}
    	else{
    		player = (Player)e.getEntity();
    		handleThirstEnvironmentDozen(player,e.getCause());
    		handleThirstEffect(player,plugin.falloutstatsThirst.get(player.getPlayerListName()));
    		return;
    	}
  
    	
    }
    private int thirst_environment_fire = 0;
    private int thirst_environment_fire_tick = 20;
    private int thirst_environment_fire_tick_random = 20;
    protected void handleThirstEnvironmentDozen(Player player,DamageCause d){
    	int dozenNum=0;
    	if (d==EntityDamageEvent.DamageCause.FIRE){
			dozenNum = thirst_environment_fire;
		}
		else if(d==EntityDamageEvent.DamageCause.FIRE_TICK){
			dozenNum = thirst_environment_fire_tick+(int)(Math.random()*thirst_environment_fire_tick_random);
		}
    	int nowLevel=0;
    	int lastLevel =(int) plugin.falloutstatsThirst.get(player.getPlayerListName()).floatValue();

    	
    	nowLevel = lastLevel+dozenNum;
    	if(nowLevel<0){
    		nowLevel=0;
    	}
    	plugin.falloutstatsThirst.put(player.getPlayerListName(), (float) nowLevel);
    	
    	
		if(nowLevel>=1000){
			player.sendMessage("§2[廢土生存]§f : 你因為燒傷，脫水死了");
			String message = (player.getPlayerListName() +" 因為燒傷，脫水死了，乾燥得變成一個精美的§6木乃伊§f");
			Server server = Bukkit.getServer();
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你因為燒傷，§c嚴重脫水§f，需要立即補充水分，避免死亡");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§e喝下藥水，包含一般水瓶§f來解渴");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你因為燒傷，§c中度脫水§f，時常慢下來喘口氣，不時覺得頭暈");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§e喝下藥水，包含一般水瓶§f來解渴");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你因為燒傷，§c輕度脫水§f，時常慢下來喘口氣，不時覺得頭暈");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§e喝下藥水，包含一般水瓶§f來解渴");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你因為燒傷，覺得§c有點口渴§f，時常慢下來喘口氣");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過喝下§e藥水§f，包含一般§e水瓶§f來解渴");
			player.sendMessage("§7-----------------------------------------");
		}
		else if(nowLevel<200  && lastLevel>=200){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你不再感到口渴");
			player.sendMessage("§7-----------------------------------------");
		}

    	
    }
    protected void handleThirstFoodDozen(Player player,ItemStack i){
    	String name = i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : i.getType().toString().replace("_", " ").toLowerCase();
    	int dozenNum=determineFoodThirst(i);
    	int nowLevel=0;
    	int lastLevel =(int) plugin.falloutstatsThirst.get(player.getPlayerListName()).floatValue();

    	
    	nowLevel = lastLevel+dozenNum;
    	if(nowLevel<0){
    		nowLevel=0;
    	}
    	plugin.falloutstatsThirst.put(player.getPlayerListName(), (float) nowLevel);
    	
    	
    	if(dozenNum>0){
    		player.sendMessage("§2[廢土生存]§f : 你食用了 "+name+" , 口渴程度§c上升§f了"+dozenNum+", "+"目前§3口渴程度§f:"+ plugin.falloutstatsThirst.get(player.getPlayerListName())+"/1000");
        	}
    	else if(dozenNum<0){
    		player.sendMessage("§2[廢土生存]§f : 你食用了 "+name+" , 口渴程度§b下降§f了"+-1*dozenNum+", "+"目前§3口渴程度§f:"+ plugin.falloutstatsThirst.get(player.getPlayerListName())+"/1000");
    	       	
    	}
    	else {
    		player.sendMessage("§2[廢土生存]§f : 你食用了 "+name+" ,什麼事也沒發生");
    	    
    	}
		if(nowLevel>=1000){
			player.sendMessage("§2[廢土生存]§f : 你渴死了");
			String message = (player.getPlayerListName() +" 渴死了，乾燥得變成一個精美的§6木乃伊§f");
			Server server = Bukkit.getServer();
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你§c嚴重脫水§f，需要立即補充水分，避免死亡");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§e喝下藥水，包含一般水瓶§f來解渴");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你§c中度脫水§f，時常慢下來喘口氣，不時覺得頭暈");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§e喝下藥水，包含一般水瓶§f來解渴");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你§c輕度脫水§f，時常慢下來喘口氣，不時覺得頭暈");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§e喝下藥水，包含一般水瓶§f來解渴");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你覺得§c有點口渴§f，時常慢下來喘口氣");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過喝下§e藥水§f，包含一般§e水瓶§f來解渴");
			player.sendMessage("§7-----------------------------------------");
		}
		else if(nowLevel<200  && lastLevel>=200){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你不再感到口渴");
			player.sendMessage("§7-----------------------------------------");
		}

    	
    }
    
    protected void handleFatigueDozen(Player player,int fatigueIncreasedDozen){
    	int dozenNum=fatigueIncreasedDozen;
    	int nowLevel=0;
    	int lastLevel =(int) plugin.falloutstatsFatigue.get(player.getPlayerListName()).floatValue();

    	
    	nowLevel = lastLevel+dozenNum;
    	if(nowLevel<0){
    		nowLevel=0;
    	}
    	if(nowLevel>1000){
    		nowLevel=1000;
    	}
    	plugin.falloutstatsFatigue.put(player.getPlayerListName(), (float) nowLevel);
    	
    	
    	if(dozenNum>0){
    		player.sendMessage("§2[廢土生存]§f : 活動了一段時間  , 你的疲倦程度§c上升§f了"+dozenNum+", "+"目前§e疲倦程度§f:"+ plugin.falloutstatsFatigue.get(player.getPlayerListName())+"/1000");
        	}
    	else if(dozenNum<0){
    		player.sendMessage("§2[廢土生存]§f : 休息了一段時間  , 你的疲倦程度§b下降§f了"+(-1)*dozenNum+", "+"目前§e疲倦程度§f:"+ plugin.falloutstatsFatigue.get(player.getPlayerListName())+"/1000");
        	   	
    	}
    	else {
    		player.sendMessage("§2[廢土生存]§f : 過了一段時間，你覺得體力沒有下降太多");
    	    
    	}
		if(nowLevel>=1000  && lastLevel<1000){
			player.sendMessage("§2[廢土生存]§f : 你覺得你在夢遊");
			String message = (player.getPlayerListName() +" §c正§d在§e夢§f遊");
			Server server = Bukkit.getServer();
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你§c極度疲倦§f，幾乎把眼睛給閉上了");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§e躺在床上§f，休息恢復精神");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你§c非常疲倦§f，不時恍神，覺得頭暈");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§e躺在床上§f，休息恢復精神");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你§c有些疲倦§f，不時恍神");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§e躺在床上§f，休息恢復精神");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你覺得精神不錯");
    		player.sendMessage("§2[廢土生存]§f : 狀態回到正常");
			player.sendMessage("§7-----------------------------------------");
		}
		else if(nowLevel<200  && lastLevel>=200){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你充分休息，覺得精神百倍");
    		player.sendMessage("§2[廢土生存]§f : 獲得抗性  : §b減少所有傷害§f 20%");
			player.sendMessage("§7-----------------------------------------");
		}

    	
    }
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e){
    	 if (e.getEntity() instanceof Player){
        	plugin.falloutstatsRadiation.put(e.getEntity().getPlayerListName(), (float) 0);
        	plugin.falloutstatsThirst.put(e.getEntity().getPlayerListName(), (float) 0);
        	plugin.falloutstatsFatigue.put(e.getEntity().getPlayerListName(), (float) 0);
    	 }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		if (action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.BED_BLOCK){
			World w = player.getWorld();
			Long time = w.getTime();
			if (time >= 0 && time <= 13000){
				player.teleport(block.getLocation().add(0, 1, 0));		
		    	handleFatigueDozen(player,-1000);
				for (PotionEffect effect : player.getActivePotionEffects()){
			        player.removePotionEffect(effect.getType());
				}
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS , 50, 5),true);
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION , 100, 5),true);

			}

		}
	}
    @EventHandler
    public void onPlayerOnBedResting(PlayerBedEnterEvent e) {
    	Player player = e.getPlayer();
    	handleFatigueDozen(player,-1000);
		for (PotionEffect effect : player.getActivePotionEffects()){
	        player.removePotionEffect(effect.getType());
		}
	}
    @EventHandler
    public void onEntityDamgePlayerEvent(EntityDamageByEntityEvent e) {
    	Player player;
    	if((!(e.getEntity() instanceof Player))  ||  (e.getDamager() instanceof Player)){ // not a player hit , or damage from player;
    		return;
    	}
    	else{
    		
    		player = (Player)e.getEntity();
    		if(handleRadiationHitDozen(player,e.getDamager())){
    			handleRadiationEffect(player,plugin.falloutstatsRadiation.get(player.getPlayerListName()));
    		}
    		return;
    	}
  
    	
    }
    private int hitDozen_creeper = 20;
    private int hitDozen_skeleton = 1;
    private int hitDozen_spider = 1;
    private int hitDozen_zombie = 1;
    private int hitDozen_slime = 1;
    private int hitDozen_ghast = 10;
    private int hitDozen_zombie_pigman = 1;
    private int hitDozen_ender = 1;
    private int determineHitDozen(Entity e){
    	if(e.getType()==EntityType.CREEPER){// hit by creeper
        	return hitDozen_creeper;
    	}
    	else if(e.getType()==EntityType.SKELETON){// hit by skeleton
        	return hitDozen_skeleton;
    	}
    	else if(e.getType()==EntityType.SPIDER  ||  e.getType()==EntityType.CAVE_SPIDER){// hit by spider
        	return hitDozen_spider;
    	}
    	else if(e.getType()==EntityType.ZOMBIE){// hit by zombie
        	return hitDozen_zombie;
    	}
    	else if(e.getType()==EntityType.SLIME){// hit by slime
        	return hitDozen_slime;
    	}
    	else if(e.getType()==EntityType.GHAST){// hit by ghast
        	return hitDozen_ghast;
    	}
    	else if(e.getType()==EntityType.PIG_ZOMBIE){// hit by zombie_pigman
        	return hitDozen_zombie_pigman;
    	}
    	else if(e.getType()==EntityType.ENDERMAN){// hit by ender
        	return hitDozen_ender;
    	}
    	else if(e.getType()==EntityType.SILVERFISH){
        	return hitDozen_ender;
    	}
    	else{
    		return 5;
    	}
    }
    protected boolean handleRadiationHitDozen(Player player,Entity e){
    	int dozenNum=0;
    	int nowLevel=0;
    	int lastLevel =(int) plugin.falloutstatsRadiation.get(player.getPlayerListName()).floatValue();

    	dozenNum = determineHitDozen(e);
    	nowLevel = lastLevel+dozenNum;
    	if(nowLevel<0){
    		nowLevel=0;
    	}
    	plugin.falloutstatsRadiation.put(player.getPlayerListName(), (float) nowLevel);
    	
    	
    	if(dozenNum>0){
    		player.sendMessage("§2[廢土生存]§f : 你被輻射生物攻擊,輻射劑量§c上升§f了"+dozenNum+", "+"目前§a輻射劑量§f:"+ plugin.falloutstatsRadiation.get(player.getPlayerListName())+"/1000");
    	}
    	else if(dozenNum==0){
    		return false;
    	}
		if(nowLevel>=1000){
			player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f超標，發出一道強烈的光芒，過一會就消失了");
			String message = (player.getPlayerListName() +" 發出一道強烈的光芒，化做一陀小型的§e蕈§6狀§c雲§f，過一會就消失了");
			Server server = Bukkit.getServer();
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f來到 : §c過量級");
    		player.sendMessage("§2[廢土生存]§f : 獲得效果 : §a夜視 / §c飢餓  / §c虛弱 / §c挖掘緩速  / §0 凋零");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f來到 : §e中量級");
    		player.sendMessage("§2[廢土生存]§f : 獲得效果 : §a夜視 / §c飢餓  / §c虛弱 / §c挖掘緩速 ");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f來到 : §a輕量級");
    		player.sendMessage("§2[廢土生存]§f : 獲得效果 : §a夜視 / §c虛弱");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f來到 : §b微量級");
    		player.sendMessage("§2[廢土生存]§f : 獲得效果 : §a夜視        §7哇嗚! 我的眼睛發出螢光了!§f");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量");
			player.sendMessage("§7-----------------------------------------");
		}
		else if(nowLevel<200){
			//player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f目前不會造成任何效果。");

		}
		return true;

    	
    }

    private int randomFloat = 20;
    private int foodDozen_apple = 3;
    private int foodDozen_baked_potato = 10;
    private int foodDozen_bread = 5;
    private int foodDozen_carrot = 3;
    private int foodDozen_raw_fish = 10;
    private int foodDozen_cooked_chicken = 10;
    private int foodDozen_cooked_fish = 5;
    private int foodDozen_cooked_porkchop = 10;
    private int foodDozen_cookie = 1;
    private int foodDozen_golen_apple = -40;//curing
    private int foodDozen_golen_carrot = -30;//curing
    private int foodDozen_melon = 3;
    private int foodDozen_mushroom_stew = 10;
    private int foodDozen_poisonous_potato = 50;
    private int foodDozen_potato = 5;
    private int foodDozen_pumpkin_pie = 5;
    private int foodDozen_raw_beef = 15;
    private int foodDozen_raw_chicken = 15;
    private int foodDozen_raw_porkchop = 15;
    private int foodDozen_rotten_flesh = 60;
    private int foodDozen_spider_eye = 40;
    private int foodDozen_steak = 10;
    private int determineFoodDozen(ItemStack i){
    	int randNum = (int) (Math.random()*randomFloat);
    	int dozenNum=0;
    	//dozenNum <0 == curing item
    	if(i.getType().equals(Material.POTION)){
    		if(i.getItemMeta().hasEnchant(Enchantment.FIRE_ASPECT)){
    			dozenNum = -1*(int) ((Math.random()+0.5)*40);
    		}
    		else if(i.getItemMeta().hasEnchant(Enchantment.ARROW_DAMAGE)){
    			dozenNum = -1*(int) ((Math.random()+0.5)*80);
    		}
    		else if(i.getItemMeta().hasEnchant(Enchantment.ARROW_FIRE)){
    			dozenNum = -1*(int) ((Math.random()+0.5)*200);
    		}
    		else if(i.getItemMeta().hasEnchant(Enchantment.ARROW_KNOCKBACK)){
    			dozenNum = -1*(int) ((Math.random()+0.5)*2000);
    		}
    		else if(i.getItemMeta().hasEnchants()==false){
    			dozenNum = (int) ((Math.random()-0.3)*20);
    		};
    	}
    	else if(i.getType().equals(Material.APPLE)){
    		dozenNum = (int) (foodDozen_apple + randNum*(Math.signum(foodDozen_apple)));
    	}
    	else if(i.getType().equals(Material.BAKED_POTATO)){
    		dozenNum = (int) (foodDozen_baked_potato + randNum*(Math.signum(foodDozen_baked_potato)));
    	}
    	else if(i.getType().equals(Material.BREAD)){
    		dozenNum = (int) (foodDozen_bread + randNum*(Math.signum(foodDozen_bread)));
    	}    
    	else if(i.getType().equals(Material.CARROT)){
    		dozenNum = (int) (foodDozen_carrot + randNum*(Math.signum(foodDozen_carrot)));
    	} 
    	else if(i.getType().equals(Material.RAW_FISH)){
    		dozenNum = (int) (foodDozen_raw_fish + randNum*(Math.signum(foodDozen_raw_fish)));
    	} 
    	else if(i.getType().equals(Material.COOKED_CHICKEN)){
    		dozenNum = (int) (foodDozen_cooked_chicken + randNum*(Math.signum(foodDozen_cooked_chicken)));
    	} 
    	else if(i.getType().equals(Material.COOKED_FISH)){
    		dozenNum = (int) (foodDozen_cooked_fish + randNum*(Math.signum(foodDozen_cooked_fish)));
    	} 
    	else if(i.getType().equals(Material.GRILLED_PORK)){
    		dozenNum = (int) (foodDozen_cooked_porkchop + randNum*(Math.signum(foodDozen_cooked_porkchop)));
    	} 
    	else if(i.getType().equals(Material.COOKIE)){
    		dozenNum = (int) (foodDozen_cookie + randNum*(Math.signum(foodDozen_cookie)));
    	} 
    	else if(i.getType().equals(Material.GOLDEN_APPLE)){
    		dozenNum = (int) (foodDozen_golen_apple + randNum*(Math.signum(foodDozen_golen_apple)));
    	} 
    	else if(i.getType().equals(Material.GOLDEN_CARROT)){
    		dozenNum = (int) (foodDozen_golen_carrot + randNum*(Math.signum(foodDozen_golen_carrot)));
    	}
    	else if(i.getType().equals(Material.MELON)){
    		dozenNum = (int) (foodDozen_melon + randNum*(Math.signum(foodDozen_melon)));
    	}
    	else if(i.getType().equals(Material.MUSHROOM_SOUP)){
    		dozenNum = (int) (foodDozen_mushroom_stew + randNum*(Math.signum(foodDozen_mushroom_stew)));
    	}
    	else if(i.getType().equals(Material.POISONOUS_POTATO)){
    		dozenNum = (int) (foodDozen_poisonous_potato + randNum*(Math.signum(foodDozen_poisonous_potato)));
    	}
    	else if(i.getType().equals(Material.POTATO)){
    		dozenNum = (int) (foodDozen_potato + randNum*(Math.signum(foodDozen_potato)));
    	}
    	else if(i.getType().equals(Material.PUMPKIN_PIE)){
    		dozenNum = (int) (foodDozen_pumpkin_pie + randNum*(Math.signum(foodDozen_pumpkin_pie)));
    	}
    	else if(i.getType().equals(Material.RAW_BEEF)){
    		dozenNum = (int) (foodDozen_raw_beef + randNum*(Math.signum(foodDozen_raw_beef)));
    	}
    	else if(i.getType().equals(Material.RAW_CHICKEN)){
    		dozenNum = (int) (foodDozen_raw_chicken + randNum*(Math.signum(foodDozen_raw_chicken)));
    	}
    	else if(i.getType().equals(Material.PORK)){
    		dozenNum = (int) (foodDozen_raw_porkchop + randNum*(Math.signum(foodDozen_raw_porkchop)));
    	}
    	else if(i.getType().equals(Material.ROTTEN_FLESH)){
    		dozenNum = (int) (foodDozen_rotten_flesh + randNum*(Math.signum(foodDozen_rotten_flesh)));
    	}
    	else if(i.getType().equals(Material.SPIDER_EYE)){
    		dozenNum = (int) (foodDozen_spider_eye + randNum*(Math.signum(foodDozen_spider_eye)));
    	}
    	else if(i.getType().equals(Material.COOKED_BEEF)){
    		dozenNum = (int) (foodDozen_steak + randNum*(Math.signum(foodDozen_steak)));
    	}
    	else{
    		dozenNum = (int) (Math.random()*30);
    	}
    	return dozenNum;
    	
    }

 
    protected void handleRadiationFoodDozen(Player player,ItemStack i){
    	String name = i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : i.getType().toString().replace("_", " ").toLowerCase();
    	int dozenNum=determineFoodDozen(i);
    	int nowLevel=0;
    	int lastLevel =(int) plugin.falloutstatsRadiation.get(player.getPlayerListName()).floatValue();

    	
    	nowLevel = lastLevel+dozenNum;
    	if(nowLevel<0){
    		nowLevel=0;
    	}
    	plugin.falloutstatsRadiation.put(player.getPlayerListName(), (float) nowLevel);
    	
    	
    	if(dozenNum>0){
    		player.sendMessage("§2[廢土生存]§f : 你食用了 "+name+" , 輻射劑量§c上升§f了"+dozenNum+", "+"目前§a輻射劑量§f:"+ plugin.falloutstatsRadiation.get(player.getPlayerListName())+"/1000");
    	}
    	else if(dozenNum<0){
    		player.sendMessage("§2[廢土生存]§f : 你食用了 "+name+" , 輻射劑量§b下降§f了"+-1*dozenNum+", "+"目前§a輻射劑量§f:"+ plugin.falloutstatsRadiation.get(player.getPlayerListName())+"/1000");
    	    	
    	}
    	else {
    		player.sendMessage("§2[廢土生存]§f : 你食用了 "+name+" ,什麼事也沒發生");
    	    
    	}
		if(nowLevel>=1000){
			player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f超標，發出一道強烈的光芒，過一會就消失了");
			String message = (player.getPlayerListName() +" 發出一道強烈的光芒，化做一陀小型的§e蕈§6狀§c雲§f，過一會就消失了");
			Server server = Bukkit.getServer();
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f來到 : §c過量級");
    		player.sendMessage("§2[廢土生存]§f : 獲得效果 : §a夜視 / §c飢餓  / §c虛弱 / §c挖掘緩速  / §0 凋零");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f來到 : §e中量級");
    		player.sendMessage("§2[廢土生存]§f : 獲得效果 : §a夜視 / §c飢餓  / §c虛弱 / §c挖掘緩速 ");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f來到 : §a輕量級");
    		player.sendMessage("§2[廢土生存]§f : 獲得效果 : §a夜視 / §c虛弱");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量");
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f來到 : §b微量級");
    		player.sendMessage("§2[廢土生存]§f : 獲得效果 : §a夜視        §7哇嗚! 我的眼睛發出螢光了!§f");
    		player.sendMessage("§2[廢土生存]§f : 你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量");
			player.sendMessage("§7-----------------------------------------");
		}
		else if(nowLevel<200  && lastLevel>=200){
			player.sendMessage("§2[廢土生存]§f : 你的§c輻射劑量§f目前不會造成任何效果。");

		}

    	
    }

    protected int fatiguePerDozen = 100;
    protected int fatigueSecondsPerDozen = 600; //20mins
    protected void handleFatigueEffect(Player player , float nowLevel){
    	if(nowLevel>=1000){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION , 400, 5),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS , 400, 5),true);
    		/*
    		for (PotionEffect effect : player.getActivePotionEffects())
    	        player.removePotionEffect(effect.getType());
    		plugin.falloutstatsFatigue.put(player.getPlayerListName(), (float) 0);
     		player.setHealth(0);*/
     	}
    	else if(nowLevel>=800){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION , 400, 4),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS , 400, 5),true);
    	}
    	else if(nowLevel>=600){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION , 200, 2),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS , 200, 5),true);
    	}
    	else if(nowLevel>=400){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION , 100, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS , 100, 5),true);
    	}
    	else if(nowLevel>=200){

    	}
    	else if(nowLevel<200){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE , 1000, 0),true);
    	}
    	
    }
    protected void handleThirstEffect(Player player , float nowLevel){
    	if(nowLevel>=1000){
    		for (PotionEffect effect : player.getActivePotionEffects())
    	        player.removePotionEffect(effect.getType());
    		plugin.falloutstatsThirst.put(player.getPlayerListName(), (float) 0);
     		player.setHealth(0);
     	}
    	else if(nowLevel>=800){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION , 400, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW , 400, 2),true);
    	}
    	else if(nowLevel>=600){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION , 200, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW , 200, 2),true);
    	}
    	else if(nowLevel>=400){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION , 100, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW , 100, 1),true);
    	}
    	else if(nowLevel>=200){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW , 50, 1),true);
    	}
    	else if(nowLevel<200){
    		
    	}
    	
    }
    protected void handleRadiationEffect(Player player , float nowLevel){
    	if(nowLevel>=1000){
    		for (PotionEffect effect : player.getActivePotionEffects())
    	        player.removePotionEffect(effect.getType());
    		plugin.falloutstatsRadiation.put(player.getPlayerListName(), (float) 0);
    		player.getWorld().createExplosion(player.getLocation(), 0);
     		player.setHealth(0);
     	}
    	else if(nowLevel>=800){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION , 1000, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER , 300, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS , 300, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING , 300, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER , 100, 1),true);
    	}
    	else if(nowLevel>=600){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION , 1000, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER , 300, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS , 300, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING , 300, 1),true);
    	}
    	else if(nowLevel>=400){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION , 1000, 1),true);
    		player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS , 300, 1),true);
    	}
    	else if(nowLevel>=200){
    		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION , 1000, 1),true);
    	}
    	else if(nowLevel<200){
    		
    	}
    	
    }

}