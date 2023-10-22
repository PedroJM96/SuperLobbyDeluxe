package com.pedrojm96.superlobby.runnable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.pedrojm96.core.CoreVariables;
import com.pedrojm96.core.effect.CoreTitles;
import com.pedrojm96.superlobby.SuperLobby;


public class TitlesAnnouncerRun extends BukkitRunnable{
	private SuperLobby plugin;
	public  int titlec = 0;
	private List<String> nodoALL = new ArrayList<String>();

	
	public TitlesAnnouncerRun(SuperLobby plugin) {
		this.plugin = plugin;
		for(String key :  plugin.configAnnouncer.getConfigurationSection("titles.list").getKeys(false)){
			nodoALL.add(key);
		}
	}


	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
			String nodo = nodoALL.get(titlec);
			
			int fadeIn = plugin.configAnnouncer.getInt("titles.list."+nodo+".fadein");
			int stay = plugin.configAnnouncer.getInt("titles.list."+nodo+".stay");
			int fadeOut = plugin.configAnnouncer.getInt("titles.list."+nodo+".fadeout");
			String title = plugin.configAnnouncer.getString("titles.list."+nodo+".title");
			title = CoreVariables.replace(title, p);
			String subtitle = plugin.configAnnouncer.getString("titles.list."+nodo+".subtitle");
			subtitle = CoreVariables.replace(subtitle, p);
			CoreTitles.sendTitles(p, fadeIn, stay, fadeOut, title,subtitle);
		}
		if(titlec>=(nodoALL.size() - 1)){
			titlec = 0;
		}else{
			titlec +=1;
		}
	}
}
