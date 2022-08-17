package com.pedrojm96.superlobby.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import com.pedrojm96.superlobby.SuperLobby;

public class TapCompleteListener implements Listener{

	
	public SuperLobby plugin;
	
	public TapCompleteListener(SuperLobby plugin) {
		this.plugin = plugin;
		
	}
	
	
	public boolean containListTab(String tab) {
		List<String> listablanca = plugin.config.getStringList("disable-tab-complete.whitelist");
		
		for(String m : listablanca) {
			if(m.equalsIgnoreCase("/"+tab)) {
				return true;
			}
		}
		return false;
		
	}
	
	
	 @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	  public void onTab(PlayerCommandSendEvent e) {
		 
		
     	
		List<String> filter = new ArrayList<String>();			        	
     	for(String m : e.getCommands()) {
     		if(containListTab(m)) {
     			filter.add(m);
     			plugin.log.debug("lista(): "+m);
     			
     		}
     	}
     	e.getCommands().clear();
     	e.getCommands().addAll(filter); 
	 }
	
}
