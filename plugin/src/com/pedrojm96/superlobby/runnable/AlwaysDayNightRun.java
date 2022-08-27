package com.pedrojm96.superlobby.runnable;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import com.pedrojm96.superlobby.SuperLobby;

public class AlwaysDayNightRun extends BukkitRunnable{

	public SuperLobby plugin;
	
	public AlwaysDayNightRun(SuperLobby plugin) {
		this.plugin = plugin;
	}
	
	public void run() {
		if (plugin.config.getBoolean("always-day.enable")){
			for (World w : Bukkit.getServer().getWorlds()) {
				if (plugin.config.getStringList("always-day.world").contains(w.getName())) 
				{
					
					/* w.setTime(1000L); */
					w.setTime(6000L);
				}	
			}    
		}
		
		if (plugin.config.getBoolean("always-night.enable")){
			for (World w : Bukkit.getServer().getWorlds()) {
				if (plugin.config.getStringList("always-night.world").contains(w.getName())) 
				{
					/* w.setTime(13000L); */
					w.setTime(18000L);
				}	
			}    
		}
	}
}
