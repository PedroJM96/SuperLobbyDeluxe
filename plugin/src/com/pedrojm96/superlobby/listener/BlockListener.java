package com.pedrojm96.superlobby.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.pedrojm96.superlobby.SuperLobby;

public class BlockListener implements Listener{
	
	public SuperLobby plugin;
	
	public BlockListener(SuperLobby plugin) {
		this.plugin = plugin;
	}
	/** 
	 * To deactivate the blocks break.
	 * "disable-block-break" config.yml.
	 * 
	 * Desativa que los usuarios que se encuentren en el mundo especificado, que no 
	 * tengan el permiso sl.staff y que no se encuentren en creativo puedan ronper bloques.
	 * 
	 * @param e
	 */
	@EventHandler 
	public void onBlockBreak(BlockBreakEvent e){
		if (plugin.config.getBoolean("disable-block-break.enable")){
			if ((plugin.isWorldRadius(e.getBlock().getLocation(), "disable-block-break")) &&(!e.isCancelled())) 
			{
				if ((e.getPlayer().isOp()) || (e.getPlayer().hasPermission("superlobby.staff")))
				{
					if (e.getPlayer().getGameMode() == GameMode.CREATIVE) 
					{
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
					}
				} else {
					e.setCancelled(true);
				}
			}
		}
	}
	/**
	 * To deactivate that they place blocks.
	 * "disable-block-place" config.yml.
	 * 
	 * Desativa que los usuarios que se encuentren en el mundo especificado, que no 
	 * tengan el permiso sl.staff y que no se encuentren en creativo puedan colocar bloques.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (plugin.config.getBoolean("disable-block-place.enable")){
			if ((plugin.isWorldRadius(e.getBlock().getLocation(), "disable-block-place")) && (!e.isCancelled())) 
			{
				if ((e.getPlayer().isOp()) || (e.getPlayer().hasPermission("superlobby.staff")))
				{
					if (e.getPlayer().getGameMode() == GameMode.CREATIVE) 
					{
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
					}
				} else {
					e.setCancelled(true);
				}
			}
		}
	}
	/**
	 * To deactivate that the blocks burn.
	 * "disable-block-burn" config.yml.
	 * 
	 * Desativa que los bloques se sufran da�o por el fuego.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onBlockFire(BlockBurnEvent e){
		if (plugin.config.getBoolean("disable-block-burn.enable")){
			if ((plugin.isWorldRadius(e.getBlock().getLocation(), "disable-block-burn")) && (!e.isCancelled())) 
			{
				
				e.setCancelled(true); 
			}
		} 
	}
	/**
	 * To deactivate that the blocks burn.
	 * "disable-block-burn" config.yml.
	 * 
	 * Desativa que los bloques se prendan fuego.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent e){
		if (plugin.config.getBoolean("disable-block-burn.enable")){
			if ((plugin.isWorldRadius(e.getBlock().getLocation(), "disable-block-burn")) && (!e.isCancelled())) 
			{
				e.setCancelled(true); 
			}
		} 
	}
}
