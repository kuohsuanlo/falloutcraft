package io.github.kuohsuanlo.falloutcraft;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.external.EZPlaceholderHook;

public class AddPlaceholder extends EZPlaceholderHook{

	  private FalloutcraftPlugin MainPlugin;
	  private final FalloutcraftPlayerListener listener;
	
	public AddPlaceholder(FalloutcraftPlugin instance) {
		// TODO Auto-generated constructor stub
		this.MainPlugin = instance;
		this.listener = MainPlugin.playerListener;
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
	            return String.valueOf(MainPlugin.falloutstatsRadiation.get((p).getPlayerListName())+"/1000");
	        }
			 return String.valueOf(MainPlugin.falloutstatsThirst.get((p).getPlayerListName())+"/1000");
		 }
			 return String.valueOf(MainPlugin.falloutstatsFatigue.get((p).getPlayerListName())+"/1000");
		 }
	        // always check if the player is null for placeholders related to the player!
	        if (p == null) {
	            return "";
	        }
		return null;
	}

}
