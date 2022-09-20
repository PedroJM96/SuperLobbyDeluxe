package com.pedrojm96.superlobby;

import org.bukkit.Location;

public class Spawn {
	private int protection_radius = 0;
	private Location spawn;
	
	public Spawn(Location spawn) {
		this.spawn = spawn;
	}
	
	public Spawn(Location spawn, int radius) {
		this.spawn = spawn;
		protection_radius = radius;
	}

	/**
	 * @return the protection_radius
	 */
	public int getProtection_radius() {
		return protection_radius;
	}

	/**
	 * @param protection_radius the protection_radius to set
	 */
	public void setProtection_radius(int protection_radius) {
		this.protection_radius = protection_radius;
	}

	/**
	 * @return the spawn
	 */
	public Location getLocation() {
		return spawn;
	}
}
