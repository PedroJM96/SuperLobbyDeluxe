package com.pedrojm96.superlobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import com.pedrojm96.superlobby.runnable.ActionbarAnnouncerRun;
import com.pedrojm96.superlobby.runnable.BossbarAnnouncerRun;
import com.pedrojm96.superlobby.runnable.MessageAnnouncerRun;
import com.pedrojm96.superlobby.runnable.TitlesAnnouncerRun;

public class Announcer {

	private SuperLobby plugin;
	
	private List<Integer> timers = new ArrayList<Integer>();
	
	public Announcer(SuperLobby plugin) {
		this.plugin = plugin;
		//Messages
		if(plugin.configAnnouncer.getBoolean("message.enable")) {
			int interval = plugin.configAnnouncer.getInt("message.interval");
			timers.add(new MessageAnnouncerRun(plugin).runTaskTimer(plugin, 0L, interval).getTaskId());
		}
		//BossBar Anunciador mensajes automáticos del bossbar.
		if(plugin.configAnnouncer.getBoolean("bossbar.enable")) {
			int interval = plugin.configAnnouncer.getInt("bossbar.interval");
			timers.add(new BossbarAnnouncerRun(plugin).runTaskTimer(plugin, 0L, interval).getTaskId());
		}
		//Titles
		if(plugin.configAnnouncer.getBoolean("titles.enable")) {
			int interval = plugin.configAnnouncer.getInt("titles.interval");
			timers.add(new TitlesAnnouncerRun(plugin).runTaskTimer(plugin, 0L, interval).getTaskId());
		}
		//Actionbar
		if(plugin.configAnnouncer.getBoolean("actionbar.enable")) {
			int interval = plugin.configAnnouncer.getInt("actionbar.interval");
			timers.add(new ActionbarAnnouncerRun(plugin).runTaskTimer(plugin, 0L, interval).getTaskId());
		}
		
	}
	
	
	public void reload() {
		cancelTimer();
		//Messages
		if(plugin.configAnnouncer.getBoolean("message.enable")) {
			int interval = plugin.configAnnouncer.getInt("message.interval");
			timers.add(new MessageAnnouncerRun(plugin).runTaskTimer(plugin, 0L, interval).getTaskId());
		}
		//BossBar Anunciador mensajes automáticos del bossbar.
		if(plugin.configAnnouncer.getBoolean("bossbar.enable")) {
			int interval = plugin.configAnnouncer.getInt("bossbar.interval");
			timers.add(new BossbarAnnouncerRun(plugin).runTaskTimer(plugin, 0L, interval).getTaskId());
		}
		//Titles
		if(plugin.configAnnouncer.getBoolean("titles.enable")) {
			int interval = plugin.configAnnouncer.getInt("titles.interval");
			timers.add(new TitlesAnnouncerRun(plugin).runTaskTimer(plugin, 0L, interval).getTaskId());
		}
		//Actionbar
		if(plugin.configAnnouncer.getBoolean("actionbar.enable")) {
			int interval = plugin.configAnnouncer.getInt("actionbar.interval");
			timers.add(new ActionbarAnnouncerRun(plugin).runTaskTimer(plugin, 0L, interval).getTaskId());
		}	
	}
	
	private void cancelTimer()
	{
		for(Integer timerID : timers) {
			if (timerID != null) {
				Bukkit.getScheduler().cancelTask(timerID.intValue());
			}
		}
		timers = new ArrayList<Integer>();
	}
	
	
}
