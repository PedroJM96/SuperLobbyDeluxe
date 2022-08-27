package com.pedrojm96.superlobby.runnable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.pedrojm96.core.CoreVariables;
import com.pedrojm96.core.effect.CoreBossBar;
import com.pedrojm96.superlobby.SuperLobby;


public class BossbarAnnouncerRun extends BukkitRunnable{
	private SuperLobby plugin;
	public  int bossbarc = 0;
	private List<String> nodoALL = new ArrayList<String>();

	
	public BossbarAnnouncerRun(SuperLobby plugin) {
		this.plugin = plugin;
		for(String key :  plugin.configAnnouncer.getConfigurationSection("bossbar.list").getKeys(false)){
			nodoALL.add(key);
		}
	}


	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
			 String nodo = nodoALL.get(bossbarc);
			 String mesage = plugin.configAnnouncer.getString("bossbar.list."+nodo+".message");
			 mesage = CoreVariables.replace(mesage, p);
			 String bc = plugin.configAnnouncer.getString("bossbar.list."+nodo+".color");
			 String bs = plugin.configAnnouncer.getString("bossbar.list."+nodo+".style");
			 int time = plugin.configAnnouncer.getInt("bossbar.list."+nodo+".seconds");
			
			 //new BossBar1(p,mesage,bc,bs,time);	 
			 
			 CoreBossBar.sendBossBar(p, mesage, bc, bs, time<1?1:time,plugin.getInstance());
		}
		if(bossbarc>=(nodoALL.size() - 1)){
			bossbarc = 0;
		}else{
			bossbarc +=1;
		}
	}
}
