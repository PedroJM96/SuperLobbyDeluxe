package com.pedrojm96.superlobby.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.pedrojm96.core.CoreVariables;
import com.pedrojm96.core.effect.CorePlayerListHeaderFooter;
import com.pedrojm96.superlobby.SuperLobby;




public class TabUpdateRun extends BukkitRunnable{
	
	public SuperLobby plugin;
	
	public TabUpdateRun(SuperLobby plugin) {
		this.plugin = plugin;
	}
	
	
	public void run() {
		
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
			if (plugin.config.getBoolean("join-tab.enable")){
				String h = plugin.config.getString("join-tab.header");
				h = CoreVariables.replace(h, onlinePlayer);
				String f = plugin.config.getString("join-tab.footer");
				f = CoreVariables.replace(f, onlinePlayer);
				CorePlayerListHeaderFooter.sendHeaderFooter(onlinePlayer, h, f);	
			}
		}
		
	}
}
