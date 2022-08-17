package com.pedrojm96.superlobby;

import org.bukkit.entity.Player;

public class Utils {
	
	@SuppressWarnings("deprecation")
	public static void setMoney(double money,Player p,SuperLobby plugin){
		double i = money - plugin.economy.getBalance(p.getName());
		if (i > 0) {
			plugin.economy.depositPlayer(p, i);
		} else {
			plugin.economy.withdrawPlayer(p, -i);
		}
	}
	
	public static boolean isEnum(Class<?> class1, String value ) {
		try {
			@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
			Object obj = Enum.valueOf((Class<Enum>)class1, value);
			return true;
		}catch(IllegalArgumentException ex){
			return false;
		}
		
	}

}
