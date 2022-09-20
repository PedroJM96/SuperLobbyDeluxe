package com.pedrojm96.superlobby.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.CoreViaVersion;
import com.pedrojm96.core.inventory.menu.CoreMenu;
import com.pedrojm96.core.inventory.menu.CoreMenuItem;
import com.pedrojm96.superlobby.AllString;
import com.pedrojm96.superlobby.SuperLobby;
import com.pedrojm96.superlobby.Utils;


public class OthersListener implements Listener{

	public SuperLobby plugin;
	
	public OthersListener(SuperLobby plugin) {
		this.plugin = plugin;
	}
	/**
	 * To deactivate the rain.
	 * "disable-rain" config.yml.
	 * 
	 * Desativa la lluvia en el mundo que se especifique si en la configuracion esta en verdadero DisableRain 
	 * (priorida alta para que se ejecute despues de todos los eventos parecidos.
	 * 
	 * @param e
	 */
	@EventHandler(priority=EventPriority.HIGH)
	public void onRain(WeatherChangeEvent e){
		 if (plugin.config.getBoolean("disable-rain.enable")){
			 if ((plugin.config.getStringList("disable-rain.world").contains(e.getWorld().getName())) && (!e.isCancelled())) 
			 {
				 e.setCancelled(e.toWeatherState()); 
			 }
		 }
	}
	/**
	 * To deactivate if the player can move item in the inventory.
	 * "disable-item-move" config.yml.
	 * 
	 * Desativa que los usuarios que se encuentren en el mundo especificado que no tengan el permiso sl.staff
	 * y que no se encuentren en creativo puedan mover items del inventario.
	 * 
	 * @param e
	 */
	@EventHandler (priority= EventPriority.HIGHEST)
	public void onItemMove(InventoryClickEvent e) {
		if (plugin.config.getBoolean("disable-item-move.enable")){
			if ((plugin.isWorldRadius(e.getWhoClicked().getLocation(), "disable-item-move")) && (!e.isCancelled())) 
			{
				if ((e.getWhoClicked().isOp()) || (e.getWhoClicked().hasPermission("superlobby.staff")))
				{
					if (e.getWhoClicked().getGameMode() == GameMode.CREATIVE) 
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
	 * To protect the frames.
	 * "frame-protect.enable" config.yml
	 * 
	 * Para evitar que los jugadores remuevan los frames.
	 * 
	 * @param e
	 */
	 @EventHandler
	 public void onHangingBreakByEntity(HangingBreakByEntityEvent e)
	 {
		 if (e.isCancelled()) {
			 return;
		 }
		 if (plugin.config.getBoolean("frame-protect.enable")){
			 if (e.getRemover() instanceof Player) {
				 if (plugin.isWorldRadius(e.getRemover().getLocation(), "frame-protect")) {
					 if ((e.getRemover().isOp()) || (e.getRemover().hasPermission("superlobby.staff")))
					 {
						 if (((HumanEntity) e.getRemover()).getGameMode() == GameMode.CREATIVE) 
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
	 }
	 /**
	  * To protect the frames.
	  * "frame-protect.enable" config.yml
	  * 
	  * Para evitar que los jugadores coloquen los frames.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onHangingPlace(HangingPlaceEvent e)
	 {
		 if (e.isCancelled()) {
			 return;
		 }
		 if (plugin.config.getBoolean("frame-protect.enable")){
			 if (plugin.isWorldRadius(e.getPlayer().getLocation(), "frame-protect")) {
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
	  * To activate the menus.
	  * "menu" config.yml
	  * "list" menus.yml
	  * 
	  * Para ejecutar los comandos internos del menu.
	  * 
	  * @param e
	  */
	 @SuppressWarnings("deprecation")
	 @EventHandler
	 public void onMenuClick(InventoryClickEvent e)
	 {
		 if(e.isCancelled()){
			 return;
		 }
		  
		 for(String menu : plugin.menus.keySet()){
			 CoreMenu m = plugin.menus.get(menu);
			 Player p = (Player) e.getWhoClicked();
			 if (e.getView().getTitle().equals(CoreColor.colorCodes(m.getName()))){
				 ItemStack itemInHand = e.getCurrentItem();
				 if(itemInHand == null){
					 break;
				 }
				 e.setCancelled(true);
				
				 for (CoreMenuItem item : m.items){
					 if (item.like(itemInHand,e.getSlot(),p))
					 {
						 if(item.isCommand()){
		    					
							 if(item.getVersionCheck() && (CoreViaVersion.getApi()!= null)) {
								 int playerversion = CoreViaVersion.getPlayerClientVersion(p);
								 boolean vali=false;
								 for(String realversion : item.getVersionList()) {
									 int protocolversion = CoreViaVersion.getProtocolVersion(realversion);
									 if(protocolversion==playerversion) {
										 vali = true;
										 break;
									 }
								 }
								 if(vali) {
									 if(item.hasPerm(p)){
										 if(plugin.economy != null){
											 double money = plugin.economy.getBalance(p.getName()); 
											 if(money >= item.getPrice() || item.getPrice() == 0){
												 double t = money - item.getPrice();
												 Utils.setMoney(t,p,plugin);	
												 item.executeCommands(p,plugin.getInstance(),AllString.prefix);
											 }else{
												 p.sendMessage(CoreColor.colorCodes(AllString.prefix+AllString.no_money));
											 }
										 }else{
											 item.executeCommands(p,plugin.getInstance(),AllString.prefix);
										 }
									 }else{
										 p.sendMessage(CoreColor.colorCodes(AllString.prefix+AllString.error_no_permission));
									 }
								 }else {
									 String versionformat = "";
									 for(String local : item.getVersionList()) {
										 versionformat = versionformat + local+" ";
									 }
									 p.sendMessage(CoreColor.colorCodes(item.getNoVersionMessage().replaceAll("<version>", versionformat)));
								 }
							 }else {
								 if(item.hasPerm(p)){
									 if(plugin.economy != null){
										 double money = plugin.economy.getBalance(p.getName()); 
										 if(money >= item.getPrice() || item.getPrice() == 0){
											 double t = money - item.getPrice();
											 Utils.setMoney(t,p,plugin);	
											 item.executeCommands(p,plugin.getInstance(),AllString.prefix);
										 }else{
											 p.sendMessage(CoreColor.colorCodes(AllString.prefix+AllString.no_money));
										 }
									 }else{
										 item.executeCommands(p,plugin.getInstance(),AllString.prefix);
									 }
								 }else{
									 p.sendMessage(CoreColor.colorCodes(AllString.prefix+AllString.error_no_permission));
								 }
							 }
						 }
						 if(!item.kOpen()){
							 p.closeInventory();;
						 }
						 break;
					 }
				 }
				 break;
			 }
		 }
		  
	 }
}
