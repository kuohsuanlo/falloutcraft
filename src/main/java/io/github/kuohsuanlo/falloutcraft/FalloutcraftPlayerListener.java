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
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
    private int thirst_baked_potato = 10;
    private int thirst_bread = 5;
    private int thirst_carrot = 5;
    private int thirst_raw_fish = -10;
    private int thirst_cooked_chicken = 10;
    private int thirst_cooked_fish = -5;
    private int thirst_cooked_porkchop = 10;
    private int thirst_cookie = 1;
    private int thirst_golen_apple = -10;//curing
    private int thirst_golen_carrot = -10;//curing
    private int thirst_melon = -5;
    private int thirst_mushroom_stew = -10;
    private int thirst_poisonous_potato = 50;
    private int thirst_potato = 10;
    private int thirst_pumpkin_pie = 5;
    private int thirst_raw_beef = 15;
    private int thirst_raw_chicken = 15;
    private int thirst_raw_porkchop = 15;
    private int thirst_rotten_flesh = 15;
    private int thirst_spider_eye = 20;
    private int thirst_steak = 10;
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
    		return;
    	}
  
    	
    }
    private int thirst_environment_fire = 0;
    private int thirst_environment_fire_tick = 5;
    protected void handleThirstEnvironmentDozen(Player player,DamageCause d){
    	int dozenNum=0;
    	if (d==EntityDamageEvent.DamageCause.FIRE){
			dozenNum = thirst_environment_fire;
		}
		else if(d==EntityDamageEvent.DamageCause.FIRE_TICK){
			dozenNum = thirst_environment_fire_tick;
		}
    	int nowLevel=0;
    	int lastLevel =(int) plugin.falloutstatsThirst.get(player.getPlayerListName()).floatValue();

    	
    	nowLevel = lastLevel+dozenNum;
    	if(nowLevel<0){
    		nowLevel=0;
    	}
    	plugin.falloutstatsThirst.put(player.getPlayerListName(), (float) nowLevel);
    	
    	
		if(nowLevel>=1000){
			player.sendMessage("��2[�o�g�ͦs]��f : �A�]���N�ˡA������F");
			String message = (player.getPlayerListName() +" �]���N�ˡA������F�A����o�ܦ��@�Ӻ������6��D�족f");
			Server server = Bukkit.getServer();
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A�]���N�ˡA��c�Y�������f�A�ݭn�ߧY�ɥR�����A�קK���`");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��e�ܤU�Ĥ��A�]�t�@����~��f�ӸѴ�");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A�]���N�ˡA��c���ײ����f�A�ɱ`�C�U�ӳݤf��A����ı�o�Y�w");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��e�ܤU�Ĥ��A�]�t�@����~��f�ӸѴ�");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A�]���N�ˡA��c���ײ����f�A�ɱ`�C�U�ӳݤf��A����ı�o�Y�w");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��e�ܤU�Ĥ��A�]�t�@����~��f�ӸѴ�");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A�]���N�ˡAı�o��c���I�f����f�A�ɱ`�C�U�ӳݤf��");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L�ܤU��e�Ĥ���f�A�]�t�@�롱e���~��f�ӸѴ�");
			player.sendMessage("��7-----------------------------------------");
		}
		else if(nowLevel<200  && lastLevel>=200){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A���b�P��f��");
			player.sendMessage("��7-----------------------------------------");
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
    		player.sendMessage("��2[�o�g�ͦs]��f : �A���ΤF "+name+" , �f���{�ס�c�W�ɡ�f�F"+dozenNum+", "+"�ثe��3�f���{�ס�f:"+ plugin.falloutstatsThirst.get(player.getPlayerListName())+"/1000");
        	}
    	else if(dozenNum<0){
    		player.sendMessage("��2[�o�g�ͦs]��f : �A���ΤF "+name+" , �f���{�ס�b�U����f�F"+-1*dozenNum+", "+"�ثe��3�f���{�ס�f:"+ plugin.falloutstatsThirst.get(player.getPlayerListName())+"/1000");
    	       	
    	}
    	else {
    		player.sendMessage("��2[�o�g�ͦs]��f : �A���ΤF "+name+" ,����Ƥ]�S�o��");
    	    
    	}
		if(nowLevel>=1000){
			player.sendMessage("��2[�o�g�ͦs]��f : �A�����F");
			String message = (player.getPlayerListName() +" �����F�A����o�ܦ��@�Ӻ������6��D�족f");
			Server server = Bukkit.getServer();
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A��c�Y�������f�A�ݭn�ߧY�ɥR�����A�קK���`");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��e�ܤU�Ĥ��A�]�t�@����~��f�ӸѴ�");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A��c���ײ����f�A�ɱ`�C�U�ӳݤf��A����ı�o�Y�w");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��e�ܤU�Ĥ��A�]�t�@����~��f�ӸѴ�");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A��c���ײ����f�A�ɱ`�C�U�ӳݤf��A����ı�o�Y�w");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��e�ܤU�Ĥ��A�]�t�@����~��f�ӸѴ�");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �Aı�o��c���I�f����f�A�ɱ`�C�U�ӳݤf��");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L�ܤU��e�Ĥ���f�A�]�t�@�롱e���~��f�ӸѴ�");
			player.sendMessage("��7-----------------------------------------");
		}
		else if(nowLevel<200  && lastLevel>=200){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A���b�P��f��");
			player.sendMessage("��7-----------------------------------------");
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
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�Q��g�ͪ�����,��g���q��c�W�ɡ�f�F"+dozenNum+", "+"�ثe��a��g���q��f:"+ plugin.falloutstatsRadiation.get(player.getPlayerListName())+"/1000");
    	}
    	else if(dozenNum==0){
    		return false;
    	}
		if(nowLevel>=1000){
			player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�W�СA�o�X�@�D�j�P�����~�A�L�@�|�N�����F");
			String message = (player.getPlayerListName() +" �o�X�@�D�j�P�����~�A�ư��@���p������e����6����c����f�A�L�@�|�N�����F");
			Server server = Bukkit.getServer();
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�Ө� : ��c�L�q��");
    		player.sendMessage("��2[�o�g�ͦs]��f : ��o�ĪG : ��a�]�� / ��c���j  / ��c��z / ��c�����w�t  / ��0 ��s");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��eRad-Away��g����f�ӭ��C��g���q");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�Ө� : ��e���q��");
    		player.sendMessage("��2[�o�g�ͦs]��f : ��o�ĪG : ��a�]�� / ��c���j  / ��c��z / ��c�����w�t ");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��eRad-Away��g����f�ӭ��C��g���q");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�Ө� : ��a���q��");
    		player.sendMessage("��2[�o�g�ͦs]��f : ��o�ĪG : ��a�]�� / ��c��z");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��eRad-Away��g����f�ӭ��C��g���q");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�Ө� : ��b�L�q��");
    		player.sendMessage("��2[�o�g�ͦs]��f : ��o�ĪG : ��a�]��        ��7�z��! �ڪ������o�X�å��F!��f");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��eRad-Away��g����f�ӭ��C��g���q");
			player.sendMessage("��7-----------------------------------------");
		}
		else if(nowLevel<200){
			//player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�ثe���|�y������ĪG�C");

		}
		return true;

    	
    }

    private int randomFloat = 5;
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
    		player.sendMessage("��2[�o�g�ͦs]��f : �A���ΤF "+name+" , ��g���q��c�W�ɡ�f�F"+dozenNum+", "+"�ثe��a��g���q��f:"+ plugin.falloutstatsRadiation.get(player.getPlayerListName())+"/1000");
    	}
    	else if(dozenNum<0){
    		player.sendMessage("��2[�o�g�ͦs]��f : �A���ΤF "+name+" , ��g���q��b�U����f�F"+-1*dozenNum+", "+"�ثe��a��g���q��f:"+ plugin.falloutstatsRadiation.get(player.getPlayerListName())+"/1000");
    	    	
    	}
    	else {
    		player.sendMessage("��2[�o�g�ͦs]��f : �A���ΤF "+name+" ,����Ƥ]�S�o��");
    	    
    	}
		if(nowLevel>=1000){
			player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�W�СA�o�X�@�D�j�P�����~�A�L�@�|�N�����F");
			String message = (player.getPlayerListName() +" �o�X�@�D�j�P�����~�A�ư��@���p������e����6����c����f�A�L�@�|�N�����F");
			Server server = Bukkit.getServer();
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�Ө� : ��c�L�q��");
    		player.sendMessage("��2[�o�g�ͦs]��f : ��o�ĪG : ��a�]�� / ��c���j  / ��c��z / ��c�����w�t  / ��0 ��s");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��eRad-Away��g����f�ӭ��C��g���q");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�Ө� : ��e���q��");
    		player.sendMessage("��2[�o�g�ͦs]��f : ��o�ĪG : ��a�]�� / ��c���j  / ��c��z / ��c�����w�t ");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��eRad-Away��g����f�ӭ��C��g���q");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�Ө� : ��a���q��");
    		player.sendMessage("��2[�o�g�ͦs]��f : ��o�ĪG : ��a�]�� / ��c��z");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��eRad-Away��g����f�ӭ��C��g���q");
			player.sendMessage("��7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("��7-----------------------------------------");
			player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�Ө� : ��b�L�q��");
    		player.sendMessage("��2[�o�g�ͦs]��f : ��o�ĪG : ��a�]��        ��7�z��! �ڪ������o�X�å��F!��f");
    		player.sendMessage("��2[�o�g�ͦs]��f : �A�i�H�z�L��eRad-Away��g����f�ӭ��C��g���q");
			player.sendMessage("��7-----------------------------------------");
		}
		else if(nowLevel<200  && lastLevel>=200){
			player.sendMessage("��2[�o�g�ͦs]��f : �A����c��g���q��f�ثe���|�y������ĪG�C");

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
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e){
    	 if (e.getEntity() instanceof Player){
        	plugin.falloutstatsRadiation.put(e.getEntity().getPlayerListName(), (float) 0);
        	plugin.falloutstatsThirst.put(e.getEntity().getPlayerListName(), (float) 0);
        }
    }
}