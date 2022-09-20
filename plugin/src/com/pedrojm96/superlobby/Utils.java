package com.pedrojm96.superlobby;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class Utils {
	
	public static Map<Player, Spawn> playerSpawn =  new HashMap<Player, Spawn>();
	
	
	@SuppressWarnings("deprecation")
	public static void setMoney(double money,Player p,SuperLobby plugin){
		double i = money - plugin.economy.getBalance(p.getName());
		if (i > 0) {
			plugin.economy.depositPlayer(p, i);
		} else {
			plugin.economy.withdrawPlayer(p, -i);
		}
	}
}
