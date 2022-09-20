package com.pedrojm96.superlobby.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.CoreSpigotUpdater;
import com.pedrojm96.core.CoreUtils;
import com.pedrojm96.core.CoreVariables;
import com.pedrojm96.core.CoreViaVersion;
import com.pedrojm96.core.command.CoreExecuteComands;
import com.pedrojm96.core.effect.CoreActionBar;
import com.pedrojm96.core.effect.CoreBossBar;
import com.pedrojm96.core.effect.CorePlayerListHeaderFooter;
import com.pedrojm96.core.effect.CoreTitles;
import com.pedrojm96.core.effect.CoreWrittenBooks;
import com.pedrojm96.core.inventory.CoreItem;
import com.pedrojm96.core.inventory.menu.CoreMenu;
import com.pedrojm96.superlobby.AllString;
import com.pedrojm96.superlobby.CustomEvents;
import com.pedrojm96.superlobby.PlayerBoard;
import com.pedrojm96.superlobby.SuperLobby;


@SuppressWarnings("deprecation")
public class PlayerListener implements Listener{
	
	public SuperLobby plugin;
	
	public PlayerListener(SuperLobby plugin) {
		this.plugin = plugin;
		
	}
	
	/**
	 * To deactivate if the player can throw item.
	 * "disable-drop-item" config.yml.
	 * 
	 * Desativa que los usuarios que se encuentren en el mundo especificado que no tengan el permiso sl.staff
	 * y que no se encuentren en creativo puedan soltar items del inventario.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e){
		if (plugin.config.getBoolean("disable-drop-item.enable")){
			if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-drop-item")) && (!e.isCancelled())) 
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
	 * To deactivate if the player can collect item.
	 * "disable-pick-up-items" config.yml.
	 * 
	 * Desativa que los usuarios que se encuentren en el mundo especificado que no tengan 
	 * el permiso sl.staff y que no se encuentren en creativo puedan recoger items.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onPickUpItems(PlayerPickupItemEvent e){
		if (plugin.config.getBoolean("disable-pick-up-items.enable")){
			if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-pick-up-items")) && (!e.isCancelled())) 
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
	 * To clean the inventory to enter the server and To clean the chat when entering the server.
	 * "join-clear-inventory" config.yml.
	 * "join-clear-chat" config.yml.
	 * 
	 * Limpiar el chat y inventario cuando el jugador entra al servidor.
	 * 
	 * @param e
	 */
	@EventHandler(priority=EventPriority.LOWEST)
	public void onJoinClear(PlayerJoinEvent e){
		if (plugin.config.getBoolean("join-clear-inventory")){
			e.getPlayer().getInventory().clear();
		}
		if (plugin.config.getBoolean("join-clear-chat")){
			for (int i = 0; i < 120; i++) {
				Player p = e.getPlayer();  
				p.sendMessage("");
			}
		}	
	}
	/**
	 * To teleport the player to the spawn when entering the server.
	 * "join-spawn" config.yml.
	 * 
	 * Transportar al jugador al spawn si existe cuando entra al servidor.
	 * 
	 * @param e
	 */
	 @EventHandler(priority=EventPriority.LOWEST)
	 public void onJoinSpawn(PlayerJoinEvent e){
		 if (plugin.config.getBoolean("join-spawn")){
			  plugin.teleportToSpawn(e.getPlayer());	  
		 }
	 }
	 /**
	  * To transport the player to spawn when they place the command /spawn.
	  * "command-spawn" config.yml.
	  * 
	  * Para abilitar el comando spawn propio del plugin.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onCommandSpawn(PlayerCommandPreprocessEvent e){
		 if(e.isCancelled()){
				return;
		 }
		 Player p = e.getPlayer();
		 
		String message = e.getMessage();
		 
		 if(message.startsWith("/spawn")) {
			 
			 String cmd = message.split(" ")[0];
			 
			 if(cmd.equalsIgnoreCase("/spawn")) {
				 if(plugin.config.getBoolean("command-spawn")){
					 e.setCancelled(true);
					 if(p.hasPermission("superlobby.use")){
						 plugin.teleportToSpawn(p);
					 }else {
						 CoreColor.message(p, AllString.prefix + AllString.error_no_permission);
					 }
				 }
			 }
			 
			 
		 }
	 }
	 /**
	  * To give and activate the custom items when entering the server.
	  * "lobby-items.join-server" config.yml
	  * 
	  * Permitir entregar los �tem de opciones del inventario en el mundo especificado al entrar al seridor.
	  * 
	  * @param e
	  */
	 @EventHandler (priority=EventPriority.HIGH)
	 public void onJoinLobbyItem(PlayerJoinEvent e){
		 if (plugin.config.getBoolean("lobby-items.enable") && plugin.config.getBoolean("lobby-items.join-server")){
			 if (plugin.isWorldRadius(e.getPlayer().getLocation(), "lobby-items")) 
			 {
				for(CoreItem item : plugin.items) {
					if(item.hasPerm(e.getPlayer())) {
						item.give(e.getPlayer(), plugin.loader);
					}else {
						plugin.log.debug("The player "+e.getPlayer().getName() + " do not have permission to receive the item "+item.getName());
					}
				}
			 }
	
		 } 
	 }
	 /**
	  * To give and activate the custom items when respawn on the server.
	  * "lobby-items.respawn" config.yml
	  * 
	  * Entrega los item cuando el jugador vuelve a aparecer.
	  * 
	  * @param e
	  */
	 public void onRespawnLobbyItem(final PlayerRespawnEvent e)
	 {
		 if (plugin.config.getBoolean("lobby-items.respawn")){
			 Bukkit.getScheduler().scheduleSyncDelayedTask(plugin.getInstance(), new Runnable()
			 {
				 public void run()
				 {
					 if (!e.getPlayer().isOnline()) {
				            return;
				     }
					 if (plugin.isWorldRadius(e.getPlayer().getLocation(), "lobby-items")) {
						 for(CoreItem item : plugin.items) {
							 if(item.hasPerm(e.getPlayer())) {
								 item.give(e.getPlayer(), plugin.loader);
							 }else {
								 plugin.log.debug("The player "+e.getPlayer().getName() + " do not have permission to receive the item "+item.getName());
							 }
						 }
					 }
			    }
			 }, 2L);
			
		 }
	 }
	 /**
	  * To give and activate the custom items when join on the world.
	  * "lobby-items.join-world" config.yml
	  * 
	  * Entrega los item del lobby cuando el jugador cambia de mundo
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void changeWorldLobbyItem(final PlayerChangedWorldEvent e)
	 {
		 if (plugin.config.getBoolean("lobby-items.join-world")){
			 Bukkit.getScheduler().scheduleSyncDelayedTask(plugin.getInstance(), new Runnable()
			 {
				 public void run()
				 {
					 if (!e.getPlayer().isOnline()) {
				            return;
				     }
					 if (plugin.isWorldRadius(e.getPlayer().getLocation(), "lobby-items")) {
						 for(CoreItem item : plugin.items) {
							 if(item.hasPerm(e.getPlayer())) {
								 item.give(e.getPlayer(), plugin.loader);
							 }else {
								 plugin.log.debug("The player "+e.getPlayer().getName() + " do not have permission to receive the item "+item.getName());
							 }
						 }
					 }
				 }
			 }, 2L);
		 }
	 }
	 /**
	  * To give and activate the custom items when join on the world.
	  * "lobby-items.enable" config.yml
	  * 
	  * Cuando el jugador interatual con los item del lobby.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onLobbyItemClick(PlayerInteractEvent e) {
		 if (plugin.config.getBoolean("lobby-items.enable")) {
			 if (plugin.isWorldRadius(e.getPlayer().getLocation(), "lobby-items")) {
				 if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
					 ItemStack itemInHand = e.getItem();
					 for(CoreItem item : plugin.items) {
						 if(item.like(itemInHand, e.getPlayer())) {
							 e.setCancelled(true);
							 if(item.getVersionCheck() && (CoreViaVersion.getApi()!= null)) {
								 int playerversion = CoreViaVersion.getPlayerClientVersion(e.getPlayer());
								 boolean vali=false;
								 for(String realversion : item.getVersionList()) {
									 int protocolversion = CoreViaVersion.getProtocolVersion(realversion);
									 if(protocolversion==playerversion) {
										 vali = true;
										 break;
									 }
								 }
								 if(vali) {
									 if(item.hasPerm(e.getPlayer())) {
										 item.executeCommands(e.getPlayer(), plugin.loader, AllString.prefix+AllString.click_wait,AllString.prefix);
										 e.getPlayer().updateInventory();
									 }else {
										 e.getPlayer().sendMessage(CoreColor.colorCodes(AllString.prefix+AllString.error_no_permission));
									 }
									
								 }else {
									 String versionformat = "";
									 for(String local : item.getVersionList()) {
										 versionformat = versionformat + local+" ";
									 }
									 CoreColor.message(e.getPlayer(), AllString.prefix + item.getNoVersionMessage());
								 }
							 }else {
								 if(item.hasPerm(e.getPlayer())) {
									 item.executeCommands(e.getPlayer(), plugin.loader, AllString.prefix+AllString.click_wait,AllString.prefix);
									 e.getPlayer().updateInventory();
								 }else {
									 e.getPlayer().sendMessage(CoreColor.colorCodes(AllString.prefix+AllString.error_no_permission));
								 }
								 
							 }
							 break;
						 }
					 }
				 }
			 }
		 }
	 }
	 /**
	  * To prevent players from falling into the void.
	  * "void-tp.enable" config.yml
	  * 
	  * Para teletransportar a los jugadores al spawn cuando caen al vacio.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onVoidTP(PlayerMoveEvent e) {
		 if (plugin.config.getBoolean("void-tp.enable")) {
			 if (plugin.isWorldRadius(e.getPlayer().getLocation(), "void-tp")) {
				 if(e.getPlayer().getLocation().getY() < plugin.config.getInt("void-tp.level")) {
					 Set<String> k =  plugin.configSpawn.getKeys(false);  
					 if (k == null || k.isEmpty()||k.size()== 0){
						 if(e.getPlayer().getWorld().getSpawnLocation() == null){
							 return;
						 }
						 e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
						 plugin.log.debug("Void-TP: The spawn has not been established, trying to send to the spawn by defeto of the world.");
					 }else{
						 plugin.teleportToSpawn(e.getPlayer());
					 }
				 }
			 }
		 }
	 }	
	 /**
	  * To protect the frames.
	  * "frame-protect.enable" config.yml
	  * 
	  * Para evitar que los jugadores interatuen con los frames.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onPlayerInteractEntity(PlayerInteractEntityEvent e)
	 {
		 if (e.isCancelled()) {
			 return;
		 }
		 if (plugin.config.getBoolean("frame-protect.enable")){
			 if (e.getRightClicked() instanceof ItemFrame) {
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
	 }
	 /**
	  * To protect the farm.
	  * "farm-protect.enable" config.yml
	  * 
	  * Para proteger los cultivos de os jugadores.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onSoilChangePlayer(PlayerInteractEvent e)
	 {
		 if (plugin.config.getBoolean("farm-protect.enable")) {
			 if (plugin.isWorldRadius(e.getPlayer().getLocation(), "farm-protect")) {
				 
				 if ((e.getAction() == Action.PHYSICAL) && (CoreUtils.isMaterial(e.getClickedBlock().getType(),"SOIL","SOUL_SOIL","LEGACY_SOIL"))) {
					 e.setCancelled(true);
				 }	 
				 
			 } 
		 }
	 } 
	 /**
	  * To have the launch pad.
	  * "jump-pads.enable" config.yml
	  * 
	  * Para colocar placas de presion impulsadoras de grandes saltos.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onJumpPlate(PlayerMoveEvent e) {
		 if (plugin.config.getBoolean("jump-pads.enable")) {
			 
			 
			 if (plugin.isWorldRadius(e.getPlayer().getLocation(), "jump-pads")) {
				 Material mat1 = Material.getMaterial(plugin.config.getString("jump-pads.plate-block").toUpperCase());
				 if(mat1==null) {
					 mat1 = Material.getMaterial(plugin.config.getString("jump-pads.plate-block-old").toUpperCase());
				 }
				 
				 if(e.getPlayer().getLocation().getBlock().getType() == mat1) {
					
					 Vector vector = e.getPlayer().getLocation().getDirection().multiply(1.5D).setY(1.0D);
					 e.getPlayer().setVelocity(vector);

					 e.getPlayer().setFallDistance(-9999.0F);
					 Sound sound;
					 try {
						 sound = Sound.valueOf(plugin.config.getString("jump-pads.sound").toUpperCase());
			         } catch (IllegalArgumentException ignore2) {
			                // try next
			        	 sound = null;
			         }
					 
					 if(sound==null) {
						sound = Sound.valueOf(plugin.config.getString("jump-pads.sound-old").toUpperCase());
					 }
					 e.getPlayer().playSound(e.getPlayer().getLocation(), sound, 1.0F, 1.0F);
				 }
			 } 	 
		 }
	 }
	/**
	 * To send messages when you enter using the BossBar,Tab,Titles,Motd,ActionBar.
	 * "join-boss-bar.enable" config.yml
	 * "join-tab.enable" config.yml
	 * "join-titles.enable" config.yml
	 * "join-motd.enable" config.yml
	 * "join-action-bar.enable" config.yml
	 * 
	 * Muestra efetos como bossbar,tab,titulos,subtitulos,motd al jugador al entrar al servidor.
	 * 
	 * @param e
	 */
	 @EventHandler (priority=EventPriority.HIGH)
	 public void onJoinEffets(PlayerJoinEvent e){
		 if (plugin.config.getBoolean("join-boss-bar.enable")){
			 String mesage = CoreVariables.replace(plugin.config.getString("join-boss-bar.message"), e.getPlayer());
			 String bc = plugin.config.getString("join-boss-bar.color");
			 String bs = plugin.config.getString("join-boss-bar.style");
			 int time = plugin.config.getInt("join-boss-bar.seconds");
			 CoreBossBar.sendBossBar(e.getPlayer(), mesage, bc, bs, time<1?1:time,plugin.getInstance());
		 } 
		 
		 if (plugin.config.getBoolean("join-tab.enable")){
			 String h = CoreVariables.replace(plugin.config.getString("join-tab.header"), e.getPlayer());
			 String f = CoreVariables.replace(plugin.config.getString("join-tab.footer"), e.getPlayer());
			 CorePlayerListHeaderFooter.sendHeaderFooter(e.getPlayer(), h, f);	
		 }
		 
		 if (plugin.config.getBoolean("join-titles.enable")){
			 int fadeIn = plugin.config.getInt("join-titles.fade-in");
			 int stay = plugin.config.getInt("join-titles.stay");
			 int fadeOut = plugin.config.getInt("join-titles.fade-out");
			 String t = CoreVariables.replace(plugin.config.getString("join-titles.title"), e.getPlayer());
			 String st = CoreVariables.replace(plugin.config.getString("join-titles.sub-title"), e.getPlayer());
			 CoreTitles.sendTitles(e.getPlayer(), fadeIn, stay, fadeOut, t,st);
		 }
		 if (plugin.config.getBoolean("join-motd.enable")){
			 List<String> motd = CoreVariables.replaceList(plugin.config.getStringList("join-motd.message"), e.getPlayer());
			 for (int i = 0; i < motd.size(); i++) {
				 String line = motd.get(i);
				 e.getPlayer().sendMessage(line);	 
			 }
		 }
	
		 
		 if (plugin.config.getBoolean("join-action-bar.enable")){
			 String msg = CoreVariables.replace(plugin.config.getString("join-action-bar.message"), e.getPlayer());
			 int time = Integer.valueOf(plugin.config.getInt("join-action-bar.seconds"));
			 CoreActionBar.sendActionBar(e.getPlayer(), msg,time*20,plugin.getInstance());
		 }
		 
	 }
	 /**
	  * To deactivate commands.
	  * "commands-blacklist.enable" config.yml
	  * 
	  * Para desatifvar comandos.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onCcommandBloker(PlayerCommandPreprocessEvent e){
		 if(e.isCancelled()){
			 return;
		 }
		 Player p = e.getPlayer();
		
		 if(plugin.config.getBoolean("commands-blacklist.enable")){
			 if(!p.hasPermission(plugin.config.getString("commands-blacklist.bypass"))){
				 if(plugin.config.getStringList("commands-blacklist.list") != null){
					 for(String commands : plugin.config.getStringList("commands-blacklist.list")){
						 if(e.getMessage().contains(" ")) {
							 if(e.getMessage().split(" ")[0].equalsIgnoreCase(commands)){
								 e.setCancelled(true);
								 CoreColor.message(e.getPlayer(),AllString.prefix + AllString.error_no_command);
								 break;
							 }
						 }else {
							 if(e.getMessage().equalsIgnoreCase(commands)){
								 e.setCancelled(true);
								 CoreColor.message(e.getPlayer(),AllString.prefix + AllString.error_no_command);
								 break;
							 }
						 }
						 
						 
					 }
				 }
			 }
		 }
	 }
	 /**
	  * To format the chat.
	  * "chat-format.enable" chat.yml
	  * 
	  * Para dar formato al chat.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onChat(AsyncPlayerChatEvent e){
		 if(plugin.configChat.getBoolean("chat-format.enable")){
			 Player player = e.getPlayer();
			 if(!player.hasPermission("superLobby.chat.magic")){
				 if(e.getMessage().contains("&k") || e.getMessage().contains("§k")){
					 e.getMessage().replaceAll("&k", "").replaceAll("§k", "");
				 }
			  }
			  if (player.hasPermission("superLobby.chat.color")) {
			      e.setMessage(CoreColor.colorCodes(e.getMessage()));
			  }
			  String format = "";
			  
			  if(plugin.permission != null){
				 
				  if(!plugin.permission.getName().equalsIgnoreCase("SuperPerms")) {
					  String g = plugin.permission.getPrimaryGroup(player);
					  
					  
					  if(g==null) {
						  throw new NullPointerException("PrimaryGroup = null.");
					  }
					  String nonColoredg = CoreColor.clearColor(g);
					
					  
					  if (plugin.configChat.isSet("chat-format.format." + nonColoredg.toLowerCase())) {
					      format = plugin.configChat.getString("chat-format.format." + nonColoredg.toLowerCase() );
					  } else {
						  plugin.log.alert("The "+nonColoredg.toLowerCase()+" group was not found in the configuration.");
						  for(String dd : plugin.configChat.getConfigurationSection("chat-format.format").getKeys(false)) {
							 
							  if(!nonColoredg.equals(dd)) {
								  plugin.log.debug("El grupo '"+nonColoredg.toLowerCase()+"' obtenido del permiso del usuario no es igual al grupo de la configuracion '"+dd+"'");
								  
							  }else {
								  plugin.log.debug("El grupo '"+nonColoredg.toLowerCase()+"' obtenido del permiso del usuario es igual al grupo de la configuracion '"+dd+"'");
							  }

						  }
						  
						  
					      format = plugin.configChat.getString("chat-format.format.default");
					  }
					  format = format.replace("{RANK}", g);
				  }else {
					  plugin.log.alert("Permissions plugin not found, using default chat.");
					  format = plugin.configChat.getString("chat-format.format.default");
					  
				  }
  
			  }else{
				  plugin.log.alert("Plugin vault not found, using chat by default.");
				  format = plugin.configChat.getString("chat-format.format.default");
			  }
			  
			  format = format.replace("{PLAYER_NAME}", player.getName());
			  format = format.replace("{DISPLAY_NAME}", "%s");
			  format = format.replace("{WORLD}", player.getWorld().getName());
			  if(plugin.chat != null){
				  format = format.replace("{PREFIX}", plugin.chat.getPlayerPrefix(player));
				  format = format.replace("{SUFFIX}", plugin.chat.getPlayerSuffix(player));
			  }
			  format = CoreVariables.replace(format, player);
			  format = format.replace("%", "%%");
			  format = format.replace("{MESSAGE}", "%2$s");
			  e.setFormat(format);
		 }
	 }
	
	 /**
	  * To execute custom events.
	  * "custom-events" config.yml
	  * "list" customevents.yml
	  * 
	  * Para ejecutar los eventos personalizados cuando el jugador entra al servidor.
	  * 
	  * @param e
	  */
	 @EventHandler(priority=EventPriority.HIGH)
	 public void onCustomJoin(PlayerJoinEvent e){
		 if (plugin.config.getBoolean("custom-events")){
			 for(CustomEvents cj : plugin.customEvents ){
				 if(e.getPlayer().hasPermission(cj.getPermission())){
					 if(cj.hasJoinMessage()){
						 if(cj.getJoinMessage().equalsIgnoreCase("none")){
							 e.setJoinMessage(null);
						 }else{
							 e.setJoinMessage(CoreVariables.replace(cj.getJoinMessage(),e.getPlayer()));
						 }
					 }
					 if(cj.hasJoinFirework()){
						 cj.sendJoinFirework(e.getPlayer());
					 }
					 if(cj.hasJoinSound()){
						 cj.sendJoinSound(e.getPlayer());
					 }
					 if(cj.hasJoinGamemode()){
						 cj.sendJoinGamemode(e.getPlayer());
					 }
					 if(cj.hasJoinComands()){
						 cj.sendJoinComands(e.getPlayer());
					 }
					 break;
				 }
			 }
		 }
	 }
	 /**
	  * To execute custom events.
	  * "custom-events" config.yml
	  * "list" customevents.yml
	  * 
	  * Para ejecutar los eventos personalizados cuando el jugador sale del servidor.
	  * 
	  * @param e
	  */
	 @EventHandler(priority=EventPriority.HIGH)
	 public void onCustomQuit(PlayerQuitEvent e){
		 if (plugin.config.getBoolean("custom-events")){
			 for(CustomEvents cj : plugin.customEvents ){
				 if(e.getPlayer().hasPermission(cj.getPermission())){
					 if(cj.hasQuitMessage()){
						 if(cj.getQuitMessage().equalsIgnoreCase("none")){
							 e.setQuitMessage(null);
						 }else{
							 e.setQuitMessage(CoreVariables.replace(cj.getQuitMessage(),e.getPlayer()));
						 }
					 } 
					 break;
				 }
			 }
		 }
	 }
	 /**
	  * Para limpiar la cache del plugin cuando sale el jugador.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onLeaveCache(PlayerQuitEvent e){
		 for (CoreItem item : plugin.items){
			 if(item.getCooldown().containsKey(e.getPlayer().getName())){
				 item.getCooldown().remove(e.getPlayer().getName());
			 }
		 }
	 }
	 /**
	  * To activate the menus.
	  * "menu" config.yml
	  * "list" menus.yml
	  * 
	  * Cuando envia un mensage en chat vemos si es igual al nombre de un menu.
	  * 
	  * @param e
	  */
	 @EventHandler(priority=EventPriority.HIGH)
	 public void onOpenMenu(PlayerCommandPreprocessEvent e){
		 if(e.isCancelled()){
				return;
		 }
		 if(!plugin.config.getBoolean("menu")) {
			 return;
		 }
		 
		 Player p = e.getPlayer();

		 if(!e.getMessage().startsWith("/slmenuopen#")) {
			 return;
		 }

		 String menuname = e.getMessage().split("#")[1].trim();
		 if(plugin.menus.containsKey(menuname)) {
			 e.setCancelled(true);
			 CoreMenu m = plugin.menus.get(menuname);
			 if(m.getWorlds() != null ) {
		    	 boolean isworld =false;
				 for(String world : m.getWorlds()) {
					 if(world.equalsIgnoreCase(p.getWorld().getName())) {
						  isworld = true;
						  break;  
			    	  } 
				 }
				 if(!isworld) {
					 CoreColor.message(e.getPlayer(), AllString.prefix + AllString.error_no_world);
		    		 return; 
				 } 
		       }
		      
		      if(m.getPerm() == null || m.getPerm() == ""){
		    	  if(m.getSound()!=null){
		    		 p.playSound(p.getLocation(), m.getSound(), 1.0F, 1.0F);
		    	  }
		    	  m.open(p);
		    	  return;
		      }
		      
		      if(!p.hasPermission(m.getPerm())){
		    	  CoreColor.message(e.getPlayer(), AllString.prefix + AllString.error_no_permission);
		    	  return;
		      }
		      if(m.getSound()!=null){
		    	  p.playSound(p.getLocation(), m.getSound(), 1.0F, 1.0F);
		      }
		      m.open(p);
		 }else {
			  CoreColor.message(e.getPlayer(), AllString.prefix + "Menu not found. Please inform the staff.");
		 }
	 }
	 
	 /**
	  * To activate the Scoreboard.
	  * "settings-enable" board.yml
	  * 
	  * Para enviar el scoreboar al jugador cuando entra al servidor.
	  * 
	  * @param e
	  */
	 @EventHandler(priority=EventPriority.HIGHEST)
	 public void onJoinBoard(final PlayerJoinEvent e){
		 if (plugin.configBoard.getBoolean("settings-enable")){
			 if(plugin.configBoard.getStringList("settings-world").contains(e.getPlayer().getWorld().getName())) {
				 PlayerBoard board = new PlayerBoard(e.getPlayer(),plugin.configBoard.getStringList("settings-title"),plugin.configBoard.getInt("settings-update-title"));
				 board.setupLines( plugin.configBoard.getConfigurationSection("board"));
				 board.send();
				 plugin.playerBoards.put(e.getPlayer(), board);
				 
				 new BukkitRunnable() {
		                public void run() {
		                		if(e.getPlayer() == null) {
		   						 	return;
		                		}
		   					 
		   					 	if (!e.getPlayer().isOnline()) {
		   				            return;
		   					 	}
		   					    PlayerBoard board = new PlayerBoard(e.getPlayer(),plugin.configBoard.getStringList("settings-title"),plugin.configBoard.getInt("settings-update-title"));
		   					    board.setupLines( plugin.configBoard.getConfigurationSection("board"));
		   					    board.send();
		   					    plugin.playerBoards.put(e.getPlayer(), board);	
		                	
		                }
		            }.runTaskLater(plugin.getInstance(), 20L);	
				 
			 }
		 }
	 }
	 /**
	  * To activate the Scoreboard.
	  * "settings-enable" board.yml
	  * 
	  * Para enviar quitar el scoreaboar del jugador cuando sale del servidor.
	  * 
	  * @param e
	  */
	 @EventHandler(priority=EventPriority.LOWEST)
	 public void onQuitBoard(PlayerQuitEvent e){
		 e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		 if(plugin.playerBoards.containsKey(e.getPlayer())){
			 plugin.playerBoards.remove(e.getPlayer());
		 }
	 }
	 /**
	  * To activate the Scoreboard.
	  * "settings-enable" board.yml
	  * 
	  * Para enviar el scoreboar al jugador cuando cambia de mundo.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onChangeWorldBoard(PlayerChangedWorldEvent e) {
		 if (plugin.configBoard.getBoolean("settings-enable")){
			 final Player p = e.getPlayer();
			 Bukkit.getScheduler().scheduleSyncDelayedTask(plugin.getInstance(), new Runnable()
			 {
				 public void run()
				 {
					 if(plugin.playerBoards.containsKey(p)) {
						 //if(!plugin.playerBoards.get(p).get().equals(p.getScoreboard())) {
							 if(plugin.configBoard.getStringList("settings-world").contains(p.getWorld().getName())) {
								 PlayerBoard board = new PlayerBoard(p,plugin.configBoard.getStringList("settings-title"),plugin.configBoard.getInt("settings-update-title"));
								
								 board.setupLines( plugin.configBoard.getConfigurationSection("board"));
								 board.send();
								 plugin.playerBoards.replace(p, board);
							 }
						 //}
					 }else if(plugin.configBoard.getStringList("settings-world").contains(p.getWorld().getName())) {
						 PlayerBoard board = new PlayerBoard(p,plugin.configBoard.getStringList("settings-title"),plugin.configBoard.getInt("settings-update-title"));
						
						 board.setupLines( plugin.configBoard.getConfigurationSection("board"));
						 board.send();
						 plugin.playerBoards.put(p, board);
					 }
				 }
			 }, 2L);		
		 }
	 }
	 /**
	  * Check for updates.
	  * "update-check" config.yml
	  * 
	  * Busca para ver si ay actualizaciones para el plugin.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void PlayerJoinUpdateCheck(PlayerJoinEvent e)
	 {
		 if(plugin.config.getBoolean("update-check")){
			 final Player p = e.getPlayer();
			 if(p.isOp()) {
				 new BukkitRunnable() {
					 public void run() {
						 CoreSpigotUpdater updater = new CoreSpigotUpdater(plugin.getInstance(), 20400);
						 try {
							 if (updater.checkForUpdates()) {
								 CoreColor.message(p, AllString.prefix+"&7Update avaliable for SuperLobby. Please update to recieve latest version.");
								 CoreColor.message(p, AllString.prefix+"&7"+updater.getResourceURL());
							 }
						} catch (Exception e) {}	 
					 }
				 }.runTaskAsynchronously(plugin.getInstance());
			 }	
		 }
	 }
	 /**
	  * Deactivate the player's interaction with the dispenser.
	  * "disable-dispenser-interaction.enable" config.yml
	  * 
	  * Desativa la interacion con el dispensador.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onDispenserClick(PlayerInteractEvent e) {
		 
		 if (plugin.config.getBoolean("disable-dispenser-interaction.enable")){
			 if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-dispenser-interaction")) && (!e.isCancelled())) 
			 {
				 if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					 if(CoreUtils.isMaterial(e.getClickedBlock().getType(), "DISPENSER")) {
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
		 }
	 }
	 /**
	  * Disable interaction with the musical blocks.
	  * "disable-noteblock-interaction.enable" config.yml
	  * 
	  * Desativa la interacion con el bloque de notas musicales.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onNoteBlockClick(PlayerInteractEvent e) {
		 if (plugin.config.getBoolean("disable-noteblock-interaction.enable")){
			 if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-noteblock-interaction")) && (!e.isCancelled())) {
				 if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
					 if(e.getClickedBlock().getType() == Material.NOTE_BLOCK) {
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
		 }
	 }
	 /**
	  * Deactivate the player's interaction with the button.
	  * "disable-button-interaction.enable" config.yml
	  * 
	  * Desativa la interacion con los botones.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onButtonClick(PlayerInteractEvent e) {
		 if (plugin.config.getBoolean("disable-button-interaction.enable")){
			 if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-button-interaction")) && (!e.isCancelled())) {
				 if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					 
					 
					 if(CoreUtils.isMaterial(e.getClickedBlock().getType(), "ACACIA_BUTTON","BIRCH_BUTTON","DARK_OAK_BUTTON","JUNGLE_BUTTON","OAK_BUTTON","SPRUCE_BUTTON","STONE_BUTTON","WOOD_BUTTON","STONE_BUTTON")  ) {
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
		 }
	 }
	 
	 /**
	  * Deactivate the player's interaction with the trapdoor.
	  * "disable-trapdoor-interaction.enable" config.yml
	  * 
	  * Desactiva la interacion con las puertas de trampa.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onTrapdoorClick(PlayerInteractEvent e) {
		 if (plugin.config.getBoolean("disable-trapdoor-interaction.enable")){
			 if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-trapdoor-interaction")) && (!e.isCancelled())) {
				 if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						
					 if(CoreUtils.isMaterial(e.getClickedBlock().getType(), "ACACIA_TRAPDOOR","BIRCH_TRAPDOOR","DARK_OAK_TRAPDOOR","IRON_TRAPDOOR","JUNGLE_TRAPDOOR","OAK_TRAPDOOR","SPRUCE_TRAPDOOR","TRAP_DOOR")) {
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
		 }
	 }
	 
	 /**
	  * Deactivate the player's interaction with the fence gate.
	  * "disable-fence-gate-interaction.enable" config.yml
	  * 
	  * Desativa la interacion con las puerta de vallas.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onFenceGateClick(PlayerInteractEvent e) {
		 if (plugin.config.getBoolean("disable-fence-gate-interaction.enable")){
			 if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-fence-gate-interaction")) && (!e.isCancelled())) {
				 if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					 if(CoreUtils.isMaterial(e.getClickedBlock().getType(), "ACACIA_FENCE_GATE","BIRCH_FENCE_GATE","DARK_OAK_FENCE_GATE","JUNGLE_FENCE_GATE","FENCE_GATE","OAK_FENCE_GATE","SPRUCE_FENCE_GATE") ) {
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
		 }
	 }
	 /**
	  * Deactivate the player's interaction with the hopper.
	  * "disable-hopper-interaction.enable" config.yml
	  * 
	  * Desativa la interacion con las tolvas.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onHopperClick(PlayerInteractEvent e) {
		 if (plugin.config.getBoolean("disable-hopper-interaction.enable")){
			 if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-hopper-interaction")) && (!e.isCancelled())) {
				 if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					 if(CoreUtils.isMaterial(e.getClickedBlock().getType(), "HOPPER","HOPPER_MINECART") ) {
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
			 
		 }
	 }
	 
	 /**
	  * Deactivate the player's interaction with the dropper.
	  * "disable-dropper-interaction.enable" config.yml
	  * 
	  * Desativa la interacion con el soltador.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onDropperClick(PlayerInteractEvent e) {
		 
		 if (plugin.config.getBoolean("disable-dropper-interaction.enable")){
			 if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-dropper-interaction")) && (!e.isCancelled())) 
			 {
				 if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					 if(CoreUtils.isMaterial(e.getClickedBlock().getType(), "DROPPER")) {
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
		 }
	 }
	 
	 /**
	  * Deactivate the player's interaction with the daylight sensor.
	  * "disable-daylight-sensor-interaction.enable" config.yml
	  * 
	  * Desativa la interacion con el sensor de luz diurna o luz solar.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onDaylightSensorClick(PlayerInteractEvent e) {
		 
		 if (plugin.config.getBoolean("disable-daylight-sensor-interaction.enable")){
			 if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-daylight-sensor-interaction")) && (!e.isCancelled())) 
			 {
				 if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					 if(CoreUtils.isMaterial(e.getClickedBlock().getType(), "DAYLIGHT_DETECTOR","DAYLIGHT_DETECTOR_INVERTED","LEGACY_DAYLIGHT_DETECTOR_INVERTED")) {
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
		 }
	 }
	  
	 /**
	  * Deactivate the player's interaction with the dropper.
	  * "disable-armorstands-interaction.enable" config.yml
	  * 
	  * Desativa la interacion con el Armor Stands.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onArmorStandsClick(PlayerArmorStandManipulateEvent e) {
		 
		 if (plugin.config.getBoolean("disable-armorstands-interaction.enable")){
			 if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-armorstands-interaction")) && (!e.isCancelled())) 
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
	  * Deactivate the player's interaction with the door.
	  * "disable-door-interaction.enable" config.yml
	  * 
	  * Desativa la interacion con el soltador.
	  * 
	  * @param e
	  */
	 @EventHandler
	 public void onDoor(PlayerInteractEvent e) {
		 
		 if (plugin.config.getBoolean("disable-door-interaction.enable")){
			 if ((plugin.isWorldRadius(e.getPlayer().getLocation(), "disable-door-interaction")) && (!e.isCancelled())) 
			 {
				 if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					 
					 if(CoreUtils.isMaterial(e.getClickedBlock().getType(), "ACACIA_DOOR","BIRCH_DOOR","DARK_OAK_DOOR","IRON_DOOR","JUNGLE_DOOR","OAK_DOOR","SPRUCE_DOOR","WOOD_DOOR","WOODEN_DOOR","CRIMSON_DOOR","WARPED_DOOR")) {
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
		 }
	 }
	 
	@EventHandler(priority = EventPriority.HIGHEST)
    public void firstJoinDetection(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        boolean existingPlayer = player.hasPlayedBefore();
        if (!existingPlayer) {
           
        	if(plugin.configFirstJoin.getBoolean("broadcast.enabled")) {
        		e.setJoinMessage(null);
        		for(Player onlinePlayer: plugin.getInstance().getServer().getOnlinePlayers()) {
        			for(String line : plugin.configFirstJoin.getStringList("broadcast.messages")) {
        				CoreColor.message(onlinePlayer, CoreVariables.replace(line, player));
        			}
        		}
        	}
        	
        	if(plugin.configFirstJoin.getBoolean("tell.enabled")) {
    			for(String line : plugin.configFirstJoin.getStringList("tell.messages")) {
    				CoreColor.message(player, CoreVariables.replace(line, player));
    			}	
        	}
        	
        	if(plugin.configFirstJoin.getBoolean("play-sound.enabled")) {
        		for(Player onlinePlayer: plugin.getInstance().getServer().getOnlinePlayers()) {
        			Sound sound = Sound.valueOf(plugin.configFirstJoin.getString("play-sound.sound").toUpperCase());
        			onlinePlayer.playSound(onlinePlayer.getLocation(), sound, 1.0F, 1.0F);
        		}
        	}
        	
        	
        	if(plugin.configFirstJoin.getBoolean("smoke-effect.enabled")) {
        		for (int i = 0; i <= 25; i++) {
        			e.getPlayer().getLocation().getWorld().playEffect(e.getPlayer().getLocation(), Effect.SMOKE, i);
                }
        	}
        	
        	if(plugin.configFirstJoin.getBoolean("firework.enabled")) {
        		for(int i=0;i<5;i++){
        			int m = i*10;
        			this.plugin.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(this.plugin.getInstance(), new Runnable() {
        				  public void run() {
        					  fireword(player);
        				  }
        				}, m);
        		}
        	}
        	
        	if(plugin.configFirstJoin.getBoolean("kit.enabled")) {
        		for(ItemStack item: plugin.firsjoinkit) {
        			 player.getInventory().addItem(item);
        		}      		
        	}
        	if(plugin.configFirstJoin.getBoolean("written-books.enabled")) {	  
        		CoreWrittenBooks.sendBook(player, plugin.configFirstJoin.getStringList("written-books.pages"), plugin.configFirstJoin.getString("written-books.title"), plugin.configFirstJoin.getString("written-books.name"),plugin.configFirstJoin.getBoolean("written-books.give-item"));    
        	}
        	
        	if(plugin.configFirstJoin.getBoolean("potion-effects.enabled")) {
        		List<PotionEffect> effects = new ArrayList<PotionEffect>();
                for (String s : plugin.configFirstJoin.getStringList("potion-effects.effects")) {
                    String[] effect = s.split(":");
                    effects.add(new PotionEffect(PotionEffectType.getByName(effect[0].toUpperCase()), Integer.parseInt(effect[2]) * 20, (Integer.parseInt(effect[1])) - 1));
                }
                player.addPotionEffects(effects);     		
        	}
        	if(plugin.configFirstJoin.getBoolean("commands.enabled")) {
                for (String comando : plugin.configFirstJoin.getStringList("commands.commands")) {
                	CoreExecuteComands c = new CoreExecuteComands(player,CoreVariables.replace(comando, player),plugin.getInstance(),AllString.prefix);
        			c.execute();
                }
        	}
        }
	}
	 
	
	public void fireword(Player p){
		//Spawn the Firework
        Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        //random
        Random r = new Random();
        Type type = Type.BALL;
       
        //sets type
	    int rt = r.nextInt(5) + 1;
	    if (rt == 1) type = Type.BALL;
        if (rt == 2) type = Type.BALL_LARGE;
        if (rt == 3) type = Type.BURST;
        if (rt == 4) type = Type.CREEPER;
        if (rt == 5) type = Type.STAR;
        
        //colors
        //To be Added
        int a = r.nextInt(256);
        int b = r.nextInt(256);
        int g = r.nextInt(256);
        Color c1 = Color.fromRGB(a, g, b);

        a = r.nextInt(256);
        b = r.nextInt(256);
        g = r.nextInt(256);
        Color c2 = Color.fromRGB(a, g, b);

        //effect
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

        //applied effects
        fwm.addEffect(effect);

        //random power! moar sulphur!
        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);

        //aaaaaand set it
        fw.setFireworkMeta(fwm);
        
	}
	
	 
	 
}
