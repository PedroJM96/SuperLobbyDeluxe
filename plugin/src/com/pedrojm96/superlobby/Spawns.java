package com.pedrojm96.superlobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class Spawns {
	
	public List<Location> spawns = new ArrayList<Location>();
	
	private int ronda = 0;
	
	
	public void add(Location spawn) {
		spawns.add(spawn);
	}
	
	public Location nextSpawn() {
		Location retorno;
		if(ronda < spawns.size()){
			retorno = spawns.get(ronda);
			ronda = ronda + 1;
		}else{
			ronda = 0;
			retorno = spawns.get(ronda);
		}
		return retorno;
	}	
}
