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
		
		
			
		/*for(Player player : Bukkit.getOnlinePlayers()) {
					Location start = player.getLocation();
					Location end = npc.getLoc();
					Vector dir = end.toVector().subtract(start.toVector());
					
					for (double i = 0; i < npc.getDistance(player); i += 1.0  ) {
						Vector div = dir.clone().multiply(i);
						start.add(div);
						
						ParticleEffect.CLOUD.play(player, start, 0, 0, 0, 0, 1);
						
						
						start.subtract(div); // subtract
					    dir.normalize(); // normalize
					
					}
					
					
				}
		 */
		for(Player onlinePlayer : plugin.playerBoards.keySet()){
			if(onlinePlayer.isOnline()) {
				PlayerBoard board = plugin.playerBoards.get(onlinePlayer);
				board.udate();
			}
		}
	}
} 
