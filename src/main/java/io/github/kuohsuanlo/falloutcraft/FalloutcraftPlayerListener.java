package io.github.kuohsuanlo.falloutcraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Handle events for all Player related events
 * @author Dinnerbone
 */
public class FalloutcraftPlayerListener implements Listener {
    private final FalloutcraftPlugin plugin;
    private final Server server;
    
    public FalloutcraftPlayerListener(FalloutcraftPlugin instance) {
        plugin = instance;
        server = instance.getServer();
        loadMessages();
        loadConfig();

    }
    protected String pathOfMessageyml="./plugins/Falloutcraft/messages.yml";
    protected String pathOfMessage_zhTWyml="./plugins/Falloutcraft/messages_zhTW.yml";
    public static HashMap<String, String> messageData_zhTW = new HashMap<String, String>();
    public static HashMap<String, String> messageData = new HashMap<String, String>();
    private void loadMessages(){
    	File f_zhTW = new File(pathOfMessage_zhTWyml);
        if (!f_zhTW.exists()) {
            try {
            	f_zhTW.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            configConstructing_zhTW();
        }
       
        FileConfiguration config_zhTW = YamlConfiguration.loadConfiguration(f_zhTW);
        for (String message : config_zhTW.getConfigurationSection("").getKeys(false)) {
        	messageData_zhTW.put(message, config_zhTW.getString(message));
        }
        
        
    	File f = new File(pathOfMessageyml);
        if (!f.exists()) {
            try {
            	f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            configConstructing();
        }
       
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        for (String message : config.getConfigurationSection("").getKeys(false)) {
        	messageData.put(message, config.getString(message));
        }
    }
    private void loadConfig(){
    	
    	FALLOUTCRAFT = messageData.get("FALLOUTCRAFT");
    	YOU_HAVE_EATEN = messageData.get("YOU_HAVE_EATEN");
    	THRIST_LEVEL_INCREASE = messageData.get("THRIST_LEVEL_INCREASE");
    	THRIST_LEVEL_DECREASE = messageData.get("THRIST_LEVEL_DECREASE");
    	YOUR_THRIST_LEVEL = messageData.get("YOUR_THRIST_LEVEL");
    	NOTHING_HAPPENED = messageData.get("NOTHING_HAPPENED");
    	HAS_DIED_DUE_TO_THRIST = messageData.get("HAS_DIED_DUE_TO_THRIST");
    	YOUR_DEHYDRATION_0_200_MES = messageData.get("YOUR_DEHYDRATION_0_200_MES");
    	YOUR_DEHYDRATION_201_400_MES = messageData.get("YOUR_DEHYDRATION_201_400_MES");
    	YOUR_DEHYDRATION_401_600_MES = messageData.get("YOUR_DEHYDRATION_401_600_MES");
    	YOUR_DEHYDRATION_601_800_MES = messageData.get("YOUR_DEHYDRATION_601_800_MES");
    	YOUR_DEHYDRATION_801_999_MES = messageData.get("YOUR_DEHYDRATION_801_999_MES");
    	YOUR_DEHYDRATION_DEATH_MES = messageData.get("YOUR_DEHYDRATION_DEATH_MES");
    	YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL = messageData.get("YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL");
    	    
    	YOUR_TIREDNESS_INCREASE = messageData.get("YOUR_TIREDNESS_INCREASE");
    	YOUR_TIREDNESS_DECREASE = messageData.get("YOUR_TIREDNESS_DECREASE");
    	YOUR_TIREDNESS_THE_SAME = messageData.get("YOUR_TIREDNESS_THE_SAME");
    	YOUR_TIRERNESS_LEVEL = messageData.get("YOUR_TIRERNESS_LEVEL");
    	    
    	YOUR_TIRERNESS_0_200_MES = messageData.get("YOUR_TIRERNESS_0_200_MES");
    	YOUR_TIRERNESS_201_400_MES = messageData.get("YOUR_TIRERNESS_201_400_MES");
    	YOUR_TIRERNESS_401_600_MES = messageData.get("YOUR_TIRERNESS_401_600_MES");
    	YOUR_TIRERNESS_601_800_MES = messageData.get("YOUR_TIRERNESS_601_800_MES");
    	YOUR_TIRERNESS_801_999_MES = messageData.get("YOUR_TIRERNESS_801_999_MES");
    	YOUR_TIRERNESS_1000_MES = messageData.get("YOUR_TIRERNESS_1000_MES");
    	IS_SLEEP_WALKING = messageData.get("IS_SLEEP_WALKING");
    	YOUR_STATUS_IS_NORMAL = messageData.get("YOUR_STATUS_IS_NORMAL");
    	YOU_GAIN_RESISTANCE_BUFF_BECAUSE_OF_WELL_RESTING = messageData.get("YOU_GAIN_RESISTANCE_BUFF_BECAUSE_OF_WELL_RESTING");
    	YOU_CAN_DECREASE_TIRENESS_THROUGH_SLEEPING = messageData.get("YOU_CAN_DECREASE_TIRENESS_THROUGH_SLEEPING");
    	    
    	YOUR_RADIATION_INCREASE_BECAUSE_ATTACK_BY_CREATURE = messageData.get("YOUR_RADIATION_INCREASE_BECAUSE_ATTACK_BY_CREATURE");
    	YOUR_RADIATION_LEVEL = messageData.get("YOUR_RADIATION_LEVEL");
    	//YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY = "你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量";
    	YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY = messageData.get("YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY");
    	YOU_DIED_BECAUSE_OF_RADIATION = messageData.get("YOU_DIED_BECAUSE_OF_RADIATION");
    	SOMEONE_DIED_BECAUSE_OF_RADIATION = messageData.get("SOMEONE_DIED_BECAUSE_OF_RADIATION");
    	YOUR_RADIATION_0_200_MES = messageData.get("YOUR_RADIATION_0_200_MES");
    	YOUR_RADIATION_201_400_MES = messageData.get("YOUR_RADIATION_201_400_MES");
    	YOUR_RADIATION_401_600_MES = messageData.get("YOUR_RADIATION_401_600_MES");
    	YOUR_RADIATION_601_800_MES = messageData.get("YOUR_RADIATION_601_800_MES");
    	YOUR_RADIATION_801_999_MES = messageData.get("YOUR_RADIATION_801_999_MES");
    	YOUR_GAIN_NO_EFFECT = messageData.get("YOUR_GAIN_NO_EFFECT");
    	YOU_GAIN_EFFECT_N = messageData.get("YOU_GAIN_EFFECT_N");
    	YOU_GAIN_EFFECT_N_F = messageData.get("YOU_GAIN_EFFECT_N_F");
    	YOU_GAIN_EFFECT_N_F_H_P = messageData.get("YOU_GAIN_EFFECT_N_F_H_P");
    	YOU_GAIN_EFFECT_N_F_H_W = messageData.get("YOU_GAIN_EFFECT_N_F_H_W");
    	YOU_REST_WELL = messageData.get("YOU_REST_WELL");
    	RADIATION_LEVEL_INCREASE = messageData.get("RADIATION_LEVEL_INCREASE");
    	RADIATION_LEVEL_DECREASE = messageData.get("RADIATION_LEVEL_DECREASE");
    	RADIATION_THRIST_LEVEL = messageData.get("RADIATION_THRIST_LEVEL");

    	FALLOUTCRART_STATUS = messageData.get("FALLOUTCRART_STATUS");
    	FALLOUTCRART_R_LEVEL_STATUS = messageData.get("FALLOUTCRART_R_LEVEL_STATUS");
    	FALLOUTCRART_D_LEVEL_STATUS = messageData.get("FALLOUTCRART_D_LEVEL_STATUS");
    	FALLOUTCRART_T_LEVEL_STATUS = messageData.get("FALLOUTCRART_T_LEVEL_STATUS");
    }
    private void setMessage(String name, String message) {
        File f = new File(pathOfMessageyml);
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        if (!config.isSet(name)) {
            config.set(name, message);
            try {
                config.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        
    }
    private void setMessage_zhTW(String name, String message) {
        File f = new File(pathOfMessage_zhTWyml);
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        if (!config.isSet(name)) {
            config.set(name, message);
            try {
                config.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        
    }
    private void configConstructing(){
    	//https://bukkit.org/threads/tutorial-creating-a-messages-yml-file.154337/
    	setMessage("thirst_apple","-5");
    	setMessage("thirst_baked_potato","40");
    	setMessage("thirst_bread","25");
    	setMessage("thirst_carrot","25");
    	setMessage("thirst_raw_fish","-10");
    	setMessage("thirst_cooked_chicken","40");
    	setMessage("thirst_cooked_fish","-5");
    	setMessage("thirst_cooked_porkchop","40");
    	setMessage("thirst_cookie","1");
    	setMessage("thirst_golen_apple","-10");//curing
    	setMessage("thirst_golen_carrot","-10");//curing
    	setMessage("thirst_melon","-5");
    	setMessage("thirst_mushroom_stew","-10");
    	setMessage("thirst_poisonous_potato","50");
    	setMessage("thirst_potato","10");
    	setMessage("thirst_pumpkin_pie","60");
    	setMessage("thirst_raw_beef","40");
    	setMessage("thirst_raw_chicken","40");
    	setMessage("thirst_raw_porkchop","40");
    	setMessage("thirst_rotten_flesh","60");
    	setMessage("thirst_spider_eye","50");
    	setMessage("thirst_steak","40");
    	         
    	setMessage("thirst_environment_fire","0");
    	setMessage("thirst_environment_fire_tick","20");
    	setMessage("thirst_environment_fire_tick_random","20");
    	         
    	setMessage("hitDozen_creeper","20");
    	setMessage("hitDozen_skeleton","1");
    	setMessage("hitDozen_spider","1");
    	setMessage("hitDozen_zombie","1");
    	         
    	setMessage("randomFloat","20");
    	setMessage("foodDozen_apple","3");
    	setMessage("foodDozen_baked_potato","10");
    	setMessage("foodDozen_bread","5");
    	setMessage("foodDozen_carrot","3");
    	setMessage("foodDozen_raw_fish","10");
    	setMessage("foodDozen_cooked_chicken","10");
    	setMessage("foodDozen_cooked_fish","5");
    	setMessage("foodDozen_cooked_porkchop","10");
    	setMessage("foodDozen_cookie","1");
    	setMessage("foodDozen_golen_apple","-40");//curing
    	setMessage("foodDozen_golen_carrot","-30");//curing
    	setMessage("foodDozen_melon","3");
    	setMessage("foodDozen_mushroom_stew","10");
    	setMessage("foodDozen_poisonous_potato","50");
    	setMessage("foodDozen_potato","5");
    	setMessage("foodDozen_pumpkin_pie","5");
    	setMessage("foodDozen_raw_beef","15");
    	setMessage("foodDozen_raw_chicken","15");
    	         
    	setMessage("FALLOUTCRAFT","§2[Falloutcraft]§f : ");
    	setMessage("YOU_HAVE_EATEN","You have eaten : ");
    	setMessage("THRIST_LEVEL_INCREASE","dehydration §cincreased§f ");
    	setMessage("THRIST_LEVEL_DECREASE","dehydration §bdecreased§f ");   
    	setMessage("YOUR_THRIST_LEVEL","\nCurrent §3dehydration level§f");

    	setMessage("NOTHING_HAPPENED","nothing happened.");
    	setMessage("HAS_DIED_DUE_TO_THRIST","has died due to §6dehydration§f");
    	setMessage("YOUR_DEHYDRATION_0_200_MES","You no longer feel thristy.");
    	setMessage("YOUR_DEHYDRATION_201_400_MES","You feel §cthirsty§f. You sometimes take a rest.");
    	setMessage("YOUR_DEHYDRATION_401_600_MES","You are §cslightly dehydrated§f, feeling dizzy sometimes.");
    	setMessage("YOUR_DEHYDRATION_601_800_MES","You are §cdehydrated§f, feeling dizzy sometimes.");
    	setMessage("YOUR_DEHYDRATION_801_999_MES","You are §cseverely dehydrated§f, feeling dizzy sometimes.");
    	setMessage("YOUR_DEHYDRATION_DEATH_MES","You died due to the dehydration.");
    	setMessage("YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL","You can decrease the dehydration through §edrinking potion or water§f");
    	 
    	setMessage("YOUR_TIREDNESS_INCREASE","Working for a while, your fatigue level §cincreased§f ");
    	setMessage("YOUR_TIREDNESS_DECREASE","Resting for a while, your fatigue level §bdecreased§f ");
    	setMessage("YOUR_TIREDNESS_THE_SAME","Working for a while, your energy doesn't drain much.");
    	setMessage("YOUR_TIRERNESS_LEVEL","\nCurrent §efatigue level§f");
    	 
    	setMessage("YOUR_TIRERNESS_0_200_MES","You are well-rested and feel energetic.");
    	setMessage("YOUR_TIRERNESS_201_400_MES","You feel energetic.");
    	setMessage("YOUR_TIRERNESS_401_600_MES","you are §ca little tired§f. You sometimes close your eyes.");
    	setMessage("YOUR_TIRERNESS_601_800_MES","you are §cvery tired§f. You sometimes close your eyes and feel dizzy.");
    	setMessage("YOUR_TIRERNESS_801_999_MES","you are §eextremly tired§f. You feel very dizzy.");
    	setMessage("YOUR_TIRERNESS_1000_MES","You seem to dreamwalking.");
    	setMessage("IS_SLEEP_WALKING","§cdre§damw§ealk§fing");
    	setMessage("YOUR_STATUS_IS_NORMAL","Your status is now normal.");
    	setMessage("YOU_GAIN_RESISTANCE_BUFF_BECAUSE_OF_WELL_RESTING","gain resistance buff : §bdecrease damage from all sources §f 20%");
    	setMessage("YOU_CAN_DECREASE_TIRENESS_THROUGH_SLEEPING", "You can §elie in a bed§f to rest and recuperate energy.");
    	 
    	setMessage("YOUR_RADIATION_INCREASE_BECAUSE_ATTACK_BY_CREATURE","You are under radiant creature attacks. Radiation level §cincreased §f");
    	setMessage("YOUR_RADIATION_LEVEL","\nCurrent §aradiation level§f");
    	//setMessage_eng("   YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY","你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量");
    	setMessage("YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY","You can decrease the radiation level through using §2Radaway§f. The recipe is \"§bwater bottle§f and §7bone_meal§f\"");
    	setMessage("YOU_DIED_BECAUSE_OF_RADIATION","You have excessvie §cradiation level§f. Your body exploded, and disappeared after a while.");
    	setMessage("SOMEONE_DIED_BECAUSE_OF_RADIATION","'s body exploded, becoming a §emushroom§6-shaped§c-cloud§f, and disappeared after a while.");
    	setMessage("YOUR_RADIATION_0_200_MES","");
    	setMessage("YOUR_RADIATION_201_400_MES","Your §cradiation sickness§f : §b*---");
    	setMessage("YOUR_RADIATION_401_600_MES","Your §cradiation sickness§f : §a**--");
    	setMessage("YOUR_RADIATION_601_800_MES","Your §cradiation sickness§f : §e***-");
    	setMessage("YOUR_RADIATION_801_999_MES","Your §cradiation sickness§f : §c****");
    	setMessage("YOUR_GAIN_NO_EFFECT","Your current §cradiation level§f doesn't effect anything.");
    	setMessage("YOU_GAIN_EFFECT_N","You gain effects : §anight-vision§7 Wow! my eyes emit fluorescence!§f");
    	setMessage("YOU_GAIN_EFFECT_N_F","You gain effects : §anight-vision / §cfatigue");
    	setMessage("YOU_GAIN_EFFECT_N_F_H_P","You gain effects : §anight-vision / §chunger / §cfatigue / §bpoisoned ");
    	setMessage("YOU_GAIN_EFFECT_N_F_H_W","You gain effects : §anight-vision / §chunger / §cfatigue / §0withered ");
    	setMessage("YOU_REST_WELL","you are well rested and refreshed");
    	setMessage("RADIATION_LEVEL_INCREASE","radiation §cincreased§f ");
    	setMessage("RADIATION_LEVEL_DECREASE","radiation §bdecreased§f ");   
    	setMessage("RADIATION_THRIST_LEVEL","\nCurrent §aradiation level§f:");

    	setMessage("FALLOUTCRART_STATUS","§7-----------§2Falloutcraft status§7-----------");
    	setMessage("FALLOUTCRART_R_LEVEL_STATUS","§cRadiation   level§f:");
    	setMessage("FALLOUTCRART_D_LEVEL_STATUS","§3Dehydration level§f:");
    	setMessage("FALLOUTCRART_T_LEVEL_STATUS","§eFatigue     level§f:");
    }
    private void configConstructing_zhTW(){
    	//https://bukkit.org/threads/tutorial-creating-a-messages-yml-file.154337/
    	setMessage_zhTW("thirst_apple","-5");
    	setMessage_zhTW("thirst_baked_potato","40");
    	setMessage_zhTW("thirst_bread","25");
    	setMessage_zhTW("thirst_carrot","25");
    	setMessage_zhTW("thirst_raw_fish","-10");
    	setMessage_zhTW("thirst_cooked_chicken","40");
    	setMessage_zhTW("thirst_cooked_fish","-5");
    	setMessage_zhTW("thirst_cooked_porkchop","40");
    	setMessage_zhTW("thirst_cookie","1");
    	setMessage_zhTW("thirst_golen_apple","-10");//curing
    	setMessage_zhTW("thirst_golen_carrot","-10");//curing
    	setMessage_zhTW("thirst_melon","-5");
    	setMessage_zhTW("thirst_mushroom_stew","-10");
    	setMessage_zhTW("thirst_poisonous_potato","50");
    	setMessage_zhTW("thirst_potato","10");
    	setMessage_zhTW("thirst_pumpkin_pie","60");
    	setMessage_zhTW("thirst_raw_beef","40");
    	setMessage_zhTW("thirst_raw_chicken","40");
    	setMessage_zhTW("thirst_raw_porkchop","40");
    	setMessage_zhTW("thirst_rotten_flesh","60");
    	setMessage_zhTW("thirst_spider_eye","50");
    	setMessage_zhTW("thirst_steak","40");
    	         
    	setMessage_zhTW("thirst_environment_fire","0");
    	setMessage_zhTW("thirst_environment_fire_tick","20");
    	setMessage_zhTW("thirst_environment_fire_tick_random","20");
    	         
    	setMessage_zhTW("hitDozen_creeper","20");
    	setMessage_zhTW("hitDozen_skeleton","1");
    	setMessage_zhTW("hitDozen_spider","1");
    	setMessage_zhTW("hitDozen_zombie","1");
    	         
    	setMessage_zhTW("randomFloat","20");
    	setMessage_zhTW("foodDozen_apple","3");
    	setMessage_zhTW("foodDozen_baked_potato","10");
    	setMessage_zhTW("foodDozen_bread","5");
    	setMessage_zhTW("foodDozen_carrot","3");
    	setMessage_zhTW("foodDozen_raw_fish","10");
    	setMessage_zhTW("foodDozen_cooked_chicken","10");
    	setMessage_zhTW("foodDozen_cooked_fish","5");
    	setMessage_zhTW("foodDozen_cooked_porkchop","10");
    	setMessage_zhTW("foodDozen_cookie","1");
    	setMessage_zhTW("foodDozen_golen_apple","-40");//curing
    	setMessage_zhTW("foodDozen_golen_carrot","-30");//curing
    	setMessage_zhTW("foodDozen_melon","3");
    	setMessage_zhTW("foodDozen_mushroom_stew","10");
    	setMessage_zhTW("foodDozen_poisonous_potato","50");
    	setMessage_zhTW("foodDozen_potato","5");
    	setMessage_zhTW("foodDozen_pumpkin_pie","5");
    	setMessage_zhTW("foodDozen_raw_beef","15");
    	setMessage_zhTW("foodDozen_raw_chicken","15");
    	         
    	setMessage_zhTW("FALLOUTCRAFT","§2[廢土生存]§f : ");
    	setMessage_zhTW("YOU_HAVE_EATEN","你食用了");
    	setMessage_zhTW("THRIST_LEVEL_INCREASE","口渴程度§c上升§f了");
    	setMessage_zhTW("THRIST_LEVEL_DECREASE","口渴程度§b下降§f了");   
    	setMessage_zhTW("YOUR_THRIST_LEVEL","目前§3口渴程度§f");
    	setMessage_zhTW("NOTHING_HAPPENED","什麼事也沒發生");
    	setMessage_zhTW("HAS_DIED_DUE_TO_THRIST","脫水死了，乾燥得變成一個精美的§6木乃伊§f");
    	setMessage_zhTW("YOUR_DEHYDRATION_0_200_MES","你不再感到口渴");
    	setMessage_zhTW("YOUR_DEHYDRATION_201_400_MES","你覺得§c有點口渴§f，時常慢下來喘口氣");
    	setMessage_zhTW("YOUR_DEHYDRATION_401_600_MES","你§c輕度脫水§f，時常慢下來喘口氣，不時覺得頭暈");
    	setMessage_zhTW("YOUR_DEHYDRATION_601_800_MES","你§c中度脫水§f，時常慢下來喘口氣，不時覺得頭暈");
    	setMessage_zhTW("YOUR_DEHYDRATION_801_999_MES","你§c嚴重脫水§f，需要立即補充水分，避免死亡");
    	setMessage_zhTW("YOUR_DEHYDRATION_DEATH_MES","你脫水死亡了");
    	setMessage_zhTW("YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL","你可以透過§e喝下藥水，包含一般水瓶§f來解渴");
    	 
    	setMessage_zhTW("YOUR_TIREDNESS_INCREASE","活動了一段時間  , 你的疲倦程度§c上升§f了");
    	setMessage_zhTW("YOUR_TIREDNESS_DECREASE","休息了一段時間  , 你的疲倦程度§b下降§f了");
    	setMessage_zhTW("YOUR_TIREDNESS_THE_SAME","過了一段時間，你覺得體力沒有下降太多");
    	setMessage_zhTW("YOUR_TIRERNESS_LEVEL","目前§e疲倦程度§f");
    	 
    	setMessage_zhTW("YOUR_TIRERNESS_0_200_MES","你充分休息，覺得精神百倍");
    	setMessage_zhTW("YOUR_TIRERNESS_201_400_MES","你覺得精神不錯");
    	setMessage_zhTW("YOUR_TIRERNESS_401_600_MES","你§c有些疲倦§f，不時恍神");
    	setMessage_zhTW("YOUR_TIRERNESS_601_800_MES","你§c非常疲倦§f，不時恍神，覺得頭暈");
    	setMessage_zhTW("YOUR_TIRERNESS_801_999_MES","你§c極度疲倦§f，幾乎把眼睛給閉上了");
    	setMessage_zhTW("YOUR_TIRERNESS_1000_MES","你覺得你在夢遊");
    	setMessage_zhTW("IS_SLEEP_WALKING","§c正§d在§e夢§f遊");
    	setMessage_zhTW("YOUR_STATUS_IS_NORMAL","狀態回到正常");
    	setMessage_zhTW("YOU_GAIN_RESISTANCE_BUFF_BECAUSE_OF_WELL_RESTING","獲得抗性  : §b減少所有傷害§f 20%");
    	setMessage_zhTW("YOU_CAN_DECREASE_TIRENESS_THROUGH_SLEEPING","你可以透過§e躺在床上§f，休息恢復精神");
    	 
    	setMessage_zhTW("YOUR_RADIATION_INCREASE_BECAUSE_ATTACK_BY_CREATURE","你被輻射生物攻擊,輻射劑量§c上升§f了");
    	setMessage_zhTW("YOUR_RADIATION_LEVEL","目前§a輻射劑量§f");
    	//setMessage_zhTW("   YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY","你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量");
    	setMessage_zhTW("YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY","你可以使用§2Radaway§f來降低輻射劑量，製作方法為 §b水瓶§f 加上 §7骨粉§f");
    	setMessage_zhTW("YOU_DIED_BECAUSE_OF_RADIATION","你的§c輻射劑量§f超標，發出一道強烈的光芒，過一會就消失了");
    	setMessage_zhTW("SOMEONE_DIED_BECAUSE_OF_RADIATION","發出一道強烈的光芒，化做一陀小型的§e蕈§6狀§c雲§f，過一會就消失了");
    	setMessage_zhTW("YOUR_RADIATION_0_200_MES","");
    	setMessage_zhTW("YOUR_RADIATION_201_400_MES","你的§c輻射劑量§f來到 : §b微量級");
    	setMessage_zhTW("YOUR_RADIATION_401_600_MES","你的§c輻射劑量§f來到 : §a輕量級");
    	setMessage_zhTW("YOUR_RADIATION_601_800_MES","你的§c輻射劑量§f來到 : §e中量級");
    	setMessage_zhTW("YOUR_RADIATION_801_999_MES","你的§c輻射劑量§f來到 : §c過量級");
    	setMessage_zhTW("YOUR_GAIN_NO_EFFECT","你的§c輻射劑量§f目前不會造成任何效果");
    	setMessage_zhTW("YOU_GAIN_EFFECT_N","獲得效果 : §a夜視        §7哇嗚! 我的眼睛發出螢光了!§f");
    	setMessage_zhTW("YOU_GAIN_EFFECT_N_F","獲得效果 : §a夜視 / §c虛弱");
    	setMessage_zhTW("YOU_GAIN_EFFECT_N_F_H_P","獲得效果 : §a夜視 / §c飢餓  / §c虛弱 / §b中毒 ");
    	setMessage_zhTW("YOU_GAIN_EFFECT_N_F_H_W","獲得效果 : §a夜視 / §c飢餓  / §c虛弱 / §0 凋零");
    	setMessage_zhTW("YOU_REST_WELL","你充分的休息，恢復了體力");
    	setMessage_zhTW("RADIATION_LEVEL_INCREASE","輻射劑量§c上升§f了");
    	setMessage_zhTW("RADIATION_LEVEL_DECREASE","輻射劑量§b下降§f了");   
    	setMessage_zhTW("RADIATION_THRIST_LEVEL","目前§a輻射劑量§f:");

    	setMessage_zhTW("FALLOUTCRART_STATUS","§7-----------§2廢土輻射狀態§7-----------");
    	setMessage_zhTW("FALLOUTCRART_R_LEVEL_STATUS","§c輻射計量§f:");
    	setMessage_zhTW("FALLOUTCRART_D_LEVEL_STATUS","§3口渴程度§f:");
    	setMessage_zhTW("FALLOUTCRART_T_LEVEL_STATUS","§e疲倦程度§f:");
    }
    //Configuration
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
    
    private int thirst_environment_fire = 0;
    private int thirst_environment_fire_tick = 20;
    private int thirst_environment_fire_tick_random = 20;
    
    private int hitDozen_creeper = 50;
    private int hitDozen_skeleton = 10;
    private int hitDozen_spider = 5;
    private int hitDozen_zombie = 10;
    private int hitDozen_slime = 5;
    private int hitDozen_ghast = 20;
    private int hitDozen_zombie_pigman = 15;
    private int hitDozen_ender = 25;
    
    
    private int randomFloat = 20;
    private int foodDozen_apple = 5;
    private int foodDozen_baked_potato = 10;
    private int foodDozen_bread = 5;
    private int foodDozen_carrot = 5;
    private int foodDozen_raw_fish = 10;
    private int foodDozen_cooked_chicken = 10;
    private int foodDozen_cooked_fish = 5;
    private int foodDozen_cooked_porkchop = 10;
    private int foodDozen_steak = 15;
    private int foodDozen_cookie = 3;
    private int foodDozen_golen_apple = -40;//curing
    private int foodDozen_golen_carrot = -30;//curing
    private int foodDozen_melon = 5;
    private int foodDozen_mushroom_stew = 10;
    private int foodDozen_poisonous_potato = 50;
    private int foodDozen_potato = 5;
    private int foodDozen_pumpkin_pie = 5;
    private int foodDozen_raw_beef = 15;
    private int foodDozen_raw_chicken = 15;
    private int foodDozen_raw_porkchop = 15;
    private int foodDozen_rotten_flesh = 60;
    private int foodDozen_spider_eye = 40;

    
    public String FALLOUTCRAFT = "§2[廢土生存]§f : ";
    public String YOU_HAVE_EATEN = "你食用了";
    public String THRIST_LEVEL_INCREASE = "口渴程度§c上升§f了";
    public String THRIST_LEVEL_DECREASE = "口渴程度§b下降§f了";   
    public String YOUR_THRIST_LEVEL = "目前§3口渴程度§f";
    public String NOTHING_HAPPENED = "什麼事也沒發生";
    public String HAS_DIED_DUE_TO_THRIST="脫水死了，乾燥得變成一個精美的§6木乃伊§f";
    public String YOUR_DEHYDRATION_0_200_MES = "你不再感到口渴";
    public String YOUR_DEHYDRATION_201_400_MES = "你覺得§c有點口渴§f，時常慢下來喘口氣";
    public String YOUR_DEHYDRATION_401_600_MES = "你§c輕度脫水§f，時常慢下來喘口氣，不時覺得頭暈";
    public String YOUR_DEHYDRATION_601_800_MES = "你§c中度脫水§f，時常慢下來喘口氣，不時覺得頭暈";
    public String YOUR_DEHYDRATION_801_999_MES = "你§c嚴重脫水§f，需要立即補充水分，避免死亡";
    public String YOUR_DEHYDRATION_DEATH_MES = "你脫水死亡了";
    public String YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL = "你可以透過§e喝下藥水，包含一般水瓶§f來解渴";
    
    public String YOUR_TIREDNESS_INCREASE = "活動了一段時間  , 你的疲倦程度§c上升§f了";
    public String YOUR_TIREDNESS_DECREASE = "休息了一段時間  , 你的疲倦程度§b下降§f了";
    public String YOUR_TIREDNESS_THE_SAME = "過了一段時間，你覺得體力沒有下降太多";
    public String YOUR_TIRERNESS_LEVEL = "目前§e疲倦程度§f";
    
    public String YOUR_TIRERNESS_0_200_MES = "你充分休息，覺得精神百倍";
    public String YOUR_TIRERNESS_201_400_MES = "你覺得精神不錯";
    public String YOUR_TIRERNESS_401_600_MES = "你§c有些疲倦§f，不時恍神";
    public String YOUR_TIRERNESS_601_800_MES = "你§c非常疲倦§f，不時恍神，覺得頭暈";
    public String YOUR_TIRERNESS_801_999_MES = "你§c極度疲倦§f，幾乎把眼睛給閉上了";
    public String YOUR_TIRERNESS_1000_MES = "你覺得你在夢遊";
    public String IS_SLEEP_WALKING=" §c正§d在§e夢§f遊";
    public String YOUR_STATUS_IS_NORMAL = "狀態回到正常";
    public String YOU_GAIN_RESISTANCE_BUFF_BECAUSE_OF_WELL_RESTING = "獲得抗性  : §b減少所有傷害§f 20%";
    public String YOU_CAN_DECREASE_TIRENESS_THROUGH_SLEEPING = "你可以透過§e躺在床上§f，休息恢復精神";
    
    public String YOUR_RADIATION_INCREASE_BECAUSE_ATTACK_BY_CREATURE = "你被輻射生物攻擊,輻射劑量§c上升§f了";
    public String YOUR_RADIATION_LEVEL = "目前§a輻射劑量§f";
//    public String YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY = "你可以透過§eRad-Away輻射抑制劑§f來降低輻射劑量";
    public String YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY = "你可以透過§e喝水§f來降低輻射劑量";
    public String YOU_DIED_BECAUSE_OF_RADIATION="你的§c輻射劑量§f超標，發出一道強烈的光芒，過一會就消失了";
    public String SOMEONE_DIED_BECAUSE_OF_RADIATION ="發出一道強烈的光芒，化做一陀小型的§e蕈§6狀§c雲§f，過一會就消失了";
    public String YOUR_RADIATION_0_200_MES ="";
    public String YOUR_RADIATION_201_400_MES ="你的§c輻射劑量§f來到 : §b微量級";
    public String YOUR_RADIATION_401_600_MES ="你的§c輻射劑量§f來到 : §a輕量級";
    public String YOUR_RADIATION_601_800_MES ="你的§c輻射劑量§f來到 : §e中量級";
    public String YOUR_RADIATION_801_999_MES ="你的§c輻射劑量§f來到 : §c過量級";
    public String YOUR_GAIN_NO_EFFECT = "你的§c輻射劑量§f目前不會造成任何效果";
    public String YOU_GAIN_EFFECT_N = "獲得效果 : §a夜視        §7哇嗚! 我的眼睛發出螢光了!§f";
    public String YOU_GAIN_EFFECT_N_F = "獲得效果 : §a夜視 / §c虛弱";
    public String YOU_GAIN_EFFECT_N_F_H_P = "獲得效果 : §a夜視 / §c飢餓  / §c虛弱 / §b中毒 ";
    public String YOU_GAIN_EFFECT_N_F_H_W = "獲得效果 : §a夜視 / §c飢餓  / §c虛弱 / §0 凋零";
    public String YOU_REST_WELL = "你充分的休息，恢復了體力";
    public String RADIATION_LEVEL_INCREASE = "輻射劑量§c上升§f了";
    public String RADIATION_LEVEL_DECREASE = "輻射劑量§b下降§f了";   
    public String RADIATION_THRIST_LEVEL = "目前§a輻射劑量§f:";

    public String FALLOUTCRART_STATUS = "§7-----------§2廢土輻射狀態§7-----------";
    public String FALLOUTCRART_R_LEVEL_STATUS = "§c輻射計量§f:";
    public String FALLOUTCRART_D_LEVEL_STATUS = "§3口渴程度§f:";
    public String FALLOUTCRART_T_LEVEL_STATUS = "§e疲倦程度§f:";
    

    

    //
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
     
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

    	server.getScheduler().scheduleSyncDelayedTask(plugin, new SyncPlayerTask_FOCraft(player,plugin), 1);
    	plugin.BukkitSchedulerSuck.addPlayer(player);

    }


    
    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent e) {
    	
    	Player player = e.getPlayer();
    	handleRadiationFoodDozen(player,e.getItem());
    	//handleRadiationEffect(player,plugin.falloutstatsRadiation.get(player.getPlayerListName()));
    	
    	handleThirstFoodDozen(player,e.getItem());
    	//handleThirstEffect(player,plugin.falloutstatsThirst.get(player.getPlayerListName()));
    	
    	server.getScheduler().scheduleSyncDelayedTask(plugin, new SyncPlayerTask_FOCraft(player,plugin), 1);
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
    		server.getScheduler().scheduleSyncDelayedTask(plugin, new SyncPlayerTask_FOCraft(player,plugin), 1);
    		return;
    	}
  
    	
    }   

    
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
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_DEATH_MES);
			String message = (player.getPlayerListName() +HAS_DIED_DUE_TO_THRIST);
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_801_999_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_601_800_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_401_600_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_201_400_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL);
			player.sendMessage("§7-----------------------------------------");
		}
		else if(nowLevel<200  && lastLevel>=200){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_0_200_MES);
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
    		player.sendMessage(FALLOUTCRAFT+YOU_HAVE_EATEN+name+" , "+THRIST_LEVEL_INCREASE+dozenNum+", "+YOUR_THRIST_LEVEL+":"+ plugin.falloutstatsThirst.get(player.getPlayerListName())+"/1000");
    		displayDehydrationDozen(player,dozenNum);
    		}
    	else if(dozenNum<0){
    		player.sendMessage(FALLOUTCRAFT+YOU_HAVE_EATEN+name+" , "+THRIST_LEVEL_DECREASE+-1*dozenNum+", "+YOUR_THRIST_LEVEL+":"+ plugin.falloutstatsThirst.get(player.getPlayerListName())+"/1000");
    		displayDehydrationDozen(player,dozenNum);
    	}
    	else {
    		//player.sendMessage(FALLOUTCRAFT+YOU_HAVE_EATEN+name+" ,"+NOTHING_HAPPENED);
    	    
    	}
		if(nowLevel>=1000){
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_DEATH_MES);
			String message = (player.getPlayerListName() +" "+HAS_DIED_DUE_TO_THRIST);
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_801_999_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_601_800_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_401_600_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_201_400_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DRINK_WATER_OR_POTION_TO_DECREASE_THRIST_LEVEL);
			player.sendMessage("§7-----------------------------------------");
		}
		else if(nowLevel<200  && lastLevel>=200){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_DEHYDRATION_0_200_MES);
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
    		player.sendMessage(FALLOUTCRAFT+YOUR_TIREDNESS_INCREASE+dozenNum+", "+YOUR_TIRERNESS_LEVEL+":"+ plugin.falloutstatsFatigue.get(player.getPlayerListName())+"/1000");
    		displayFatigueDozen(player,dozenNum);
    		}
    	else if(dozenNum<0){
    		player.sendMessage(FALLOUTCRAFT+YOUR_TIREDNESS_DECREASE+(-1)*dozenNum+", "+YOUR_TIRERNESS_LEVEL+":"+ plugin.falloutstatsFatigue.get(player.getPlayerListName())+"/1000");
    		displayFatigueDozen(player,dozenNum);  	
    	}
    	else {
    		player.sendMessage(FALLOUTCRAFT+YOUR_TIREDNESS_THE_SAME);
    	    
    	}
		if(nowLevel>=1000  && lastLevel<1000){
			player.sendMessage(FALLOUTCRAFT+YOUR_TIRERNESS_1000_MES);
			String message = (player.getPlayerListName() +" "+IS_SLEEP_WALKING);
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_TIRERNESS_801_999_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DECREASE_TIRENESS_THROUGH_SLEEPING);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_TIRERNESS_601_800_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DECREASE_TIRENESS_THROUGH_SLEEPING);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_TIRERNESS_401_600_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DECREASE_TIRENESS_THROUGH_SLEEPING);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_TIRERNESS_201_400_MES);
    		player.sendMessage(FALLOUTCRAFT+YOUR_STATUS_IS_NORMAL);
			player.sendMessage("§7-----------------------------------------");
		}
		else if(nowLevel<200  && lastLevel>=200){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_TIRERNESS_0_200_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_GAIN_RESISTANCE_BUFF_BECAUSE_OF_WELL_RESTING);
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

		    	server.getScheduler().scheduleSyncDelayedTask(plugin, new SyncPlayerTask_Sleep(player,plugin), 1);
		    	player.sendMessage(FALLOUTCRAFT+YOU_REST_WELL);
			}

		}
	}
    @EventHandler
    public void onPlayerOnBedResting(PlayerBedEnterEvent event) {
    	Player player = event.getPlayer();
    	handleFatigueDozen(player,-1000);
    	server.getScheduler().scheduleSyncDelayedTask(plugin, new SyncPlayerTask_Sleep(player,plugin), 1);
	}

    @EventHandler
    public void onEntityDamgePlayerEvent(EntityDamageByEntityEvent event) {
    	Player player;
    	if((!(event.getEntity() instanceof Player))  ||  (event.getDamager() instanceof Player)){ // not a player hit , or damage from player;
    		return;
    	}
    	else{
    		
    		player = (Player)event.getEntity();
    		if(handleRadiationHitDozen(player,event.getDamager())){
    			
    			server.getScheduler().scheduleSyncDelayedTask(plugin, new SyncPlayerTask_FOCraft(player,plugin), 1);
    			
    		}
    		return;
    	}
  
    	
    }
    private void displayDehydrationDozen(Player player,int dozenNum){
    	if (dozenNum>0){
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" subtitle {text:\"↑"+dozenNum+"\",color:\"dark_red\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" title {text:\"❆\",color:\"dark_aqua\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" times 10 10 10");
    	}
    	else if (dozenNum<0){
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" subtitle {text:\"↓"+dozenNum*(-1)+"\",color:\"aqua\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" title {text:\"❆\",color:\"dark_aqua\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" times 10 10 10");
    	}

    }
    private void displayFatigueDozen(Player player,int dozenNum){
    	
    	if (dozenNum>0){
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" subtitle {text:\"↑"+dozenNum+"\",color:\"dark_red\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" title {text:\"Zz\",color:\"yellow\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" times 10 10 10");
    	}
    	else if (dozenNum<0){
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" subtitle {text:\"↓"+dozenNum*(-1)+"\",color:\"aqua\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" title {text:\"Zz\",color:\"yellow\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" times 10 10 10");
    	}

    }
    private void displayRadiationDozen(Player player,int dozenNum){
    	
    	if (dozenNum>0){
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" subtitle {text:\"↑"+dozenNum+"\",color:\"dark_red\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" title {text:\"☢\",color:\"dark_green\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" times 10 10 10");
    	}
    	else if (dozenNum<0){
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" subtitle {text:\"↓"+dozenNum*(-1)+"\",color:\"aqua\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" title {text:\"☢\",color:\"dark_green\"}");
    		server.dispatchCommand(server.getConsoleSender(), "title "+player.getPlayerListName()+" times 10 10 10");
    	}

    }

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
    		return 1;
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
    		//player.sendMessage(FALLOUTCRAFT+YOUR_RADIATION_INCREASE_BECAUSE_ATTACK_BY_CREATURE+dozenNum+", "+YOUR_RADIATION_LEVEL+":"+ plugin.falloutstatsRadiation.get(player.getPlayerListName())+"/1000");
    		displayRadiationDozen(player,dozenNum);
    	}
    	else if(dozenNum==0){
    		return false;
    	}
		if(nowLevel>=1000){
			player.sendMessage(FALLOUTCRAFT+YOU_DIED_BECAUSE_OF_RADIATION);
			String message = (player.getPlayerListName() +" "+SOMEONE_DIED_BECAUSE_OF_RADIATION);
			Server server = Bukkit.getServer();
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_RADIATION_801_999_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_GAIN_EFFECT_N_F_H_W);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_RADIATION_601_800_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_GAIN_EFFECT_N_F_H_P);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_RADIATION_401_600_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_GAIN_EFFECT_N_F);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_RADIATION_201_400_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_GAIN_EFFECT_N);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY);
			player.sendMessage("§7-----------------------------------------");
		}
		else if(nowLevel<200){
			//player.sendMessage(FALLOUTCRAFT+" : 你的§c輻射劑量§f目前不會造成任何效果。");

		}
		return true;

    	
    }



    private int determineFoodDozen(ItemStack i){
    	int randNum = (int) (Math.random()*randomFloat);
    	int dozenNum=0;
    	//dozenNum <0 == curing item
    	if(i.getType().equals(Material.POTION)){

    		if(i.getItemMeta().hasEnchant(Enchantment.FIRE_ASPECT)  ){
        		dozenNum = -1*(int) ((Math.random()+0.5)*60)*i.getItemMeta().getEnchantLevel(Enchantment.FIRE_ASPECT);
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
    			dozenNum = (int) ((Math.random())*20);
    			//dozenNum = -1*(int) ((Math.random()+0.5)*20);
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
    		player.sendMessage(FALLOUTCRAFT+YOU_HAVE_EATEN+name+" , "+RADIATION_LEVEL_INCREASE+dozenNum+", "+RADIATION_THRIST_LEVEL+ plugin.falloutstatsRadiation.get(player.getPlayerListName())+"/1000");
    		displayRadiationDozen(player,dozenNum);
    	}
    	else if(dozenNum<0){
    		player.sendMessage(FALLOUTCRAFT+YOU_HAVE_EATEN+name+" , "+RADIATION_LEVEL_DECREASE+-1*dozenNum+", "+RADIATION_THRIST_LEVEL+ plugin.falloutstatsRadiation.get(player.getPlayerListName())+"/1000");
    		displayRadiationDozen(player,dozenNum);
    	}
    	else {
    		//player.sendMessage(FALLOUTCRAFT+YOU_HAVE_EATEN+name+" ,"+NOTHING_HAPPENED);
    	    
    	}
		if(nowLevel>=1000){
			player.sendMessage(FALLOUTCRAFT+YOU_DIED_BECAUSE_OF_RADIATION);
			String message = (player.getPlayerListName() +" "+SOMEONE_DIED_BECAUSE_OF_RADIATION);
			Server server = Bukkit.getServer();
			server.broadcastMessage(message);
		}
		else if((nowLevel>=800  && lastLevel<800) ){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_RADIATION_801_999_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_GAIN_EFFECT_N_F_H_W);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=600  && lastLevel<600)  ||  (nowLevel<800  && lastLevel>=800)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_RADIATION_601_800_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_GAIN_EFFECT_N_F_H_P);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=400  && lastLevel<400)  ||  (nowLevel<600  && lastLevel>=600)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_RADIATION_401_600_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_GAIN_EFFECT_N_F);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY);
			player.sendMessage("§7-----------------------------------------");
		}
		else if((nowLevel>=200  && lastLevel<200)  ||  (nowLevel<400  && lastLevel>=400)){
			player.sendMessage("§7-----------------------------------------");
			player.sendMessage(FALLOUTCRAFT+YOUR_RADIATION_201_400_MES);
    		player.sendMessage(FALLOUTCRAFT+YOU_GAIN_EFFECT_N);
    		player.sendMessage(FALLOUTCRAFT+YOU_CAN_DECREASE_RADIATION_LEVEL_BY_TAKING_RADAWAY);
			player.sendMessage("§7-----------------------------------------");
		}
		else if(nowLevel<200  && lastLevel>=200){
			player.sendMessage(FALLOUTCRAFT+YOUR_GAIN_NO_EFFECT);

		}

    	
    }

    protected int fatiguePerDozen = 100;
    protected int fatigueSecondsPerDozen = 600; //20mins
    protected void handleFatigueEffect(Player player , float nowLevel){
    	if(nowLevel>=1000){
    		synchronized(this){
        		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION , 400, 5),true);
        		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS , 400, 5),true);
    		}

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
    		/*
	        for (Iterator<PotionEffect> i =player.getActivePotionEffects().iterator(); i.hasNext();) {
	        	PotionEffect e = i.next();
	        	player.removePotionEffect(e.getType());
	        }*/
	        player.setFireTicks(0);
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
    		/*
	        for (Iterator<PotionEffect> i =player.getActivePotionEffects().iterator(); i.hasNext();) {
	        	PotionEffect e = i.next();
	        	player.removePotionEffect(e.getType());
	        }
	        */
    		//plugin.falloutstatsRadiation.put(player.getPlayerListName(), (float) 0);
    		player.getWorld().createExplosion(player.getLocation(), 0);
     		player.setHealth(0);
     	}
    	else if(nowLevel>=800){
        		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION , 1000, 1),true);
        		player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS , 300, 1),true);
        		player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER , 100, 1),true);

    	}
    	else if(nowLevel>=600){
        		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION , 1000, 1),true);
        		player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS , 300, 1),true);
        		player.addPotionEffect(new PotionEffect(PotionEffectType.POISON , 20, 0),true);

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