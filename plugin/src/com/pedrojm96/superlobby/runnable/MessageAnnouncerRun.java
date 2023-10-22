package com.pedrojm96.superlobby.runnable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.pedrojm96.core.CoreVariables;
import com.pedrojm96.superlobby.SuperLobby;


public class MessageAnnouncerRun extends BukkitRunnable{
	private SuperLobby plugin;
	public  int messagec = 0;
	private List<String> nodoALL = new ArrayList<String>();

	
	public MessageAnnouncerRun(SuperLobby plugin) {
		this.plugin = plugin;
		for(String key :  plugin.configAnnouncer.getConfigurationSection("message.list").getKeys(false)){
			nodoALL.add(key);
		}
	}


	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
			String nodo = nodoALL.get(messagec);
			List<String> messageLines = plugin.configAnnouncer.getStringList("message.list."+nodo);
			for(String s : messageLines){
				s = CoreVariables.replace(s, p);
				p.sendMessage(s); 
			}
			
		}
		if(messagec>=(nodoALL.size() - 1)){
			messagec = 0;
		}else{
			messagec +=1;
		}
	}
}
