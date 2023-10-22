package com.pedrojm96.superlobby.runnable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.pedrojm96.core.CoreVariables;
import com.pedrojm96.core.effect.CoreActionBar;
import com.pedrojm96.superlobby.SuperLobby;


public class ActionbarAnnouncerRun extends BukkitRunnable{
	private SuperLobby plugin;
	public  int ationc = 0;
	private List<String> nodoALL = new ArrayList<String>();

	
	public ActionbarAnnouncerRun(SuperLobby plugin) {
		this.plugin = plugin;
		for(String key :  plugin.configAnnouncer.getConfigurationSection("actionbar.list").getKeys(false)){
			nodoALL.add(key);
		}
	}


	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
			String nodo = nodoALL.get(ationc);
			int stay = plugin.configAnnouncer.getInt("actionbar.list."+nodo+".stay");
			String mesage = plugin.configAnnouncer.getString("actionbar.list."+nodo+".message");
			mesage = CoreVariables.replace(mesage, p);
			CoreActionBar.sendActionBar(p, mesage, stay,plugin.getInstance());
			
		}
		if(ationc>=(nodoALL.size() - 1)){
			ationc = 0;
		}else{
			ationc +=1;
		}
	}
}
