package com.pedrojm96.superlobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class Spawns {
	
	public List<Spawn> spawns = new ArrayList<Spawn>();
	
	private int ronda = 0;
	
	public void add(Spawn spawn) {
		spawns.add(spawn);
	}
	
	public Location nextSpawn() {
		Location retorno;
		if(ronda < spawns.size()){
			retorno = spawns.get(ronda).getLocation();
			ronda = ronda + 1;
		}else{
			ronda = 0;
			retorno = spawns.get(ronda).getLocation();
		}
		return retorno;
	}
}
