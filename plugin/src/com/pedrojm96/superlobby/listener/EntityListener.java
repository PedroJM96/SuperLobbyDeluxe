package com.pedrojm96.superlobby.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.pedrojm96.core.CoreUtils;
import com.pedrojm96.superlobby.SuperLobby;


public class EntityListener implements Listener{
	
	public SuperLobby plugin;
	
	public EntityListener(SuperLobby plugin) {
		this.plugin = plugin;
	}
	/**
	 * To deactivate that the items do damage.
	 * "disable-item-damage" config.yml.
	 * 
	 * Desactivar el da�o causado por los items en el mundo especificado.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onItemDamage(EntityDamageEvent  e){
		if (e.getEntity() instanceof org.bukkit.entity.Item) {
			if (plugin.config.getBoolean("disable-item-damage.enable")){
				if ((plugin.config.getStringList("disable-item-damage.world").contains(e.getEntity().getWorld().getName())) && (!e.isCancelled())) 
				{
					e.setCancelled(true); 
				}
			}
		}
	}
	/**
	 * To deactivate entity explosions.
	 * "disable-entity-explode" config.yml.
	 * 
	 * Desactiva la explosi�n de entidades, ejemplo el crepeer.
	 * 
	 * @param e
	 */
	@EventHandler(priority=EventPriority.HIGH)
	public void onEntityExplode(EntityExplodeEvent e){
		if (plugin.config.getBoolean("disable-entity-explode.enable")){
			if ((plugin.config.getStringList("disable-entity-explode.world").contains(e.getEntity().getWorld().getName())) && (!e.isCancelled())) 
			{
				e.setCancelled(true); 
			}
		} 
	}
	/**
	 * To deactivate the damage.
	 * "disable-damage" config.yml.
	 * 
	 * Desactiva el da�o a los jugadores.
	 * 
	 * @param e
	 */
	@EventHandler (priority=EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent e)
	{
	    if (!(e.getEntity() instanceof Player)) {
	      return;
	    }
	    if (plugin.config.getBoolean("disable-damage.enable")){
			if ((plugin.config.getStringList("disable-damage.world").contains(e.getEntity().getWorld().getName())) && (!e.isCancelled())) 
			{
				e.setCancelled(true); 
				((Player) e.getEntity()).setHealth(20);
			}
		}  
	}
	/**
	 * To deactivate hunger in the players.
	 * "disable-hunger" config.yml.
	 * 
	 * Desactiva el hambre en los jugadores en el mundo espesificado.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e)
	{
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (plugin.config.getBoolean("disable-hunger.enable")){
			if ((plugin.config.getStringList("disable-hunger.world").contains(e.getEntity().getWorld().getName())) && (!e.isCancelled())) 
			{
				e.setCancelled(true); 
				((Player) e.getEntity()).setFoodLevel(20);
			}
		} 
	}
	/**
	 * To deactivate the players' death messages.
	 * "disable-death-message" config.yml.
	 * 
	 * Desactiva los mensajes de muertes de los jugadores en el mundo especificado.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onPlayerDeathMessage(PlayerDeathEvent e)
	{
		if (plugin.config.getBoolean("disable-death-message.enable")){
			if (plugin.config.getStringList("disable-death-message.world").contains(e.getEntity().getWorld().getName())) 
			{
				e.setDeathMessage(null);
			}
		}
	}
	/**
	 * To deactivate monsters and animals appear.
	 * "disable-creature-spawn" config.yml.
	 * 
	 * Desactiva que los animales y mostruos se aparescan en el mundo.
	 * 
	 * @param e
	 */
	@EventHandler(priority=EventPriority.NORMAL)
	public void onCreatureSpawn(CreatureSpawnEvent e)
	{
		if(e.isCancelled()){
			return;
		}
		if(e.getEntity() == null){
			return;
		}
		if(e.getSpawnReason()!=SpawnReason.NATURAL) {
			return;
		}
		if (plugin.config.getBoolean("disable-creature-spawn.enable")){
			if (plugin.config.getStringList("disable-creature-spawn.world").contains(e.getEntity().getWorld().getName())) 
			{
				 e.setCancelled(true);
			}
		}
	}
	/**
	 * To clean the items left by the player when he dies.
	 * "clear-drops-on-death" config.yml.
	 * 
	 * Limpiar los �tem cuando muera el jugador en el mundo espesificado.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onPlayerDeathDrops(PlayerDeathEvent e)
	{
		if (plugin.config.getBoolean("clear-drops-on-death.enable")){
			if (plugin.config.getStringList("clear-drops-on-death.world").contains(e.getEntity().getWorld().getName())) 
			{
				e.getDrops().clear();
			}
		}
	}
	/**
	  * To protect the farm.
	  * "farm-protect.enable" config.yml
	  * 
	  * Para proteger los cultivos de las entidades.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void soilChangeEntity(EntityInteractEvent e)
	 {
		 if (plugin.config.getBoolean("farm-protect.enable")) {
			 if (plugin.config.getStringList("farm-protect.world").contains(e.getEntity().getWorld().getName())) {
				 
				 if ((e.getEntityType() != EntityType.PLAYER) && (CoreUtils.isMaterial(e.getBlock().getType(), "SOUL_SOIL","SOIL"))) {
					 e.setCancelled(true);
				 }  
			 } 
		 }
	 }
	
}
