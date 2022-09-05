package com.pedrojm96.superlobby.runnable;



import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.pedrojm96.superlobby.PlayerBoard;
import com.pedrojm96.superlobby.SuperLobby;



public class BoardAnimationRun extends BukkitRunnable{
	
	public SuperLobby plugin;
	
	public BoardAnimationRun(SuperLobby plugin) {
		this.plugin = plugin;
	}
	
	
	public void run() {
		for(Player onlinePlayer : plugin.playerBoards.keySet()){
			if(onlinePlayer.isOnline()) {
				PlayerBoard board = plugin.playerBoards.get(onlinePlayer);
				board.udate();
			}
		}
	}
} 
