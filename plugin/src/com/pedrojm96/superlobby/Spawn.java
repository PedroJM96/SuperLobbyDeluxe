package com.pedrojm96.superlobby;

import org.bukkit.Location;

public class Spawn {
	private int protection_radius = 0;
	private Location spawn;
	private String name;
	
	public Spawn(Location spawn, String name) {
		this.spawn = spawn;
		this.name = name;
	}
	
	public Spawn(Location spawn, int radius, String name) {
		this.spawn = spawn;
		protection_radius = radius;
		this.name = name;
	}
	
	public String getName() {
		return this.name;
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
