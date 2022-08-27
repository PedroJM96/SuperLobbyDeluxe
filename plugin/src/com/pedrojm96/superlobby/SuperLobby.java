package com.pedrojm96.superlobby;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.CoreConfig;
import com.pedrojm96.core.CoreLog;
import com.pedrojm96.core.CoreMaterial;
import com.pedrojm96.core.CorePlugin;
import com.pedrojm96.core.CoreSpigotUpdater;
import com.pedrojm96.core.CoreUtils;
import com.pedrojm96.core.CoreVariables;
import com.pedrojm96.core.CoreViaVersion;
import com.pedrojm96.core.command.CoreCommands;
import com.pedrojm96.core.command.CoreExecuteComands;
import com.pedrojm96.core.effect.CoreBossBar;
import com.pedrojm96.core.effect.CorePlayerListHeaderFooter;
import com.pedrojm96.core.inventory.CoreItem;
import com.pedrojm96.core.inventory.menu.CoreMenu;
import com.pedrojm96.core.libraryloader.CoreLoader;
import com.pedrojm96.core.libraryloader.CoreURLClassLoader;
import com.pedrojm96.core.libraryloader.CoreURLClassLoaderHelper;
import com.pedrojm96.core.libraryloader.LibraryLoader;
import com.pedrojm96.superlobby.listener.BlockListener;
import com.pedrojm96.superlobby.listener.EntityListener;
import com.pedrojm96.superlobby.listener.OthersListener;
import com.pedrojm96.superlobby.listener.PlayerListener;
import com.pedrojm96.superlobby.listener.TapCompleteListener;
import com.pedrojm96.superlobby.runnable.AlwaysDayNightRun;
import com.pedrojm96.superlobby.runnable.BoardAnimationRun;
import com.pedrojm96.superlobby.runnable.TabUpdateRun;
import com.pedrojm96.superlobby.subcommand.HelpCMD;
import com.pedrojm96.superlobby.subcommand.OpenMenuCMD;
import com.pedrojm96.superlobby.subcommand.ReloadCMD;
import com.pedrojm96.superlobby.subcommand.RemSpawnCMD;
import com.pedrojm96.superlobby.subcommand.SetSpawnCMD;
import com.pedrojm96.superlobby.subcommand.SetSpawnPermissionCMD;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class SuperLobby implements CoreLoader{

	public CoreConfig config;
	public CoreConfig configSpawn;
	public CoreConfig configItem;
	public CoreConfig configChat;
	public CoreConfig configInfoCommands;
	public CoreConfig configCustomEvents;
	public CoreConfig configMenus;
	public CoreConfig configBoard;
	public CoreConfig configAnnouncer;
	public CoreConfig configMessages;
	public CoreConfig configFirstJoin;
	public CoreLog log;
	public Permission permission = null;
	public Economy economy = null;
	public PlayerPoints playerPoints = null;
	
	public List<ItemStack> firsjoinkit = new ArrayList<ItemStack>();;
	
	public Chat chat = null;
	public List<InfoCommand> infoCommands = new ArrayList<InfoCommand>();
	public List<CustomEvents> customEvents = new ArrayList<CustomEvents>();
	
	public List<CoreItem> items = new ArrayList<CoreItem>();
	public Map<String, CoreMenu> menus = new HashMap<String, CoreMenu>();
	public Map<Player, PlayerBoard> playerBoards =  new HashMap<Player, PlayerBoard>();
	
	public Map<String, Spawns> spawnss =  new HashMap<String, Spawns>();
	
	public Announcer announcer;
	
	public final JavaPlugin instance;
	public final CorePlugin loader;
	public  static SuperLobby plugin;
	private final CoreURLClassLoaderHelper classPathAppender;
	
	public int spawns = 0;
	
	
	public SuperLobby(CorePlugin loader) {
		this.loader = loader;
        this.instance = loader.getInstance();
        this.log = loader.getLog();
        this.classPathAppender = new CoreURLClassLoader(getClass().getClassLoader());
        //this.classPathAppender = new JarInJarClassPathAppender(getClass().getClassLoader());
    }
	
	
	
	public void banner() {
		this.log.line();
		this.log.println("   _____                       _           _     _           ");
		this.log.println("  / ____|                     | |         | |   | |          ");
		this.log.println(" | (___  _   _ _ __   ___ _ __| |     ___ | |__ | |__  _   _ ");
		this.log.println("  \\___ \\| | | | '_ \\ / _ \\ '__| |    / _ \\| '_ \\| '_ \\| | | |");
		this.log.println("  ____) | |_| | |_) |  __/ |  | |___| (_) | |_) | |_) | |_| |");
		this.log.println(" |_____/ \\__,_| .__/ \\___|_|  |______\\___/|_.__/|_.__/ \\__, |");
		this.log.println("              | |                                       __/ |");
		this.log.println("              |_|                                      |___/ ");
		this.log.println("    ");
		this.log.line();
	}

	
	 public void loadDependencies(){
		 this.log.info("Loading Libraries...");
		 LibraryLoader lib = new LibraryLoader(this.classPathAppender, this.log,this.loader);
		 try {
			  lib.loadLib( "commons-lang", "commons-lang", "2.6");
			  lib.loadLib( "commons-codec", "commons-codec", "1.15");
			  lib.loadLib( "com.google.code.gson", "gson", "2.9.0");
		     
		 } catch (IOException e) {
		      e.printStackTrace();
		 } 
	 }
	 
	 
	 
	 
	public void onEnable() {
		plugin = this;
		this.log = this.loader.getLog();
		banner();
		this.log.info("Deluxe Version: V" + this.instance.getDescription().getVersion());
		this.log.info("Plugin Create by PedroJM96.");
		loadDependencies();
		try {
		   //Ponemos a "Dormir" el programa durante los ms que queremos
		   Thread.sleep(1*1000);
		}
		catch (Exception e) {
		   System.out.println(e);
		}
		
		/******************************************************************************************************************
		 *                                      Carga de configuracion                                                    *
		 ******************************************************************************************************************/
		this.log.info("Loading configuration...");
		FileConfiguration oldconfig = new YamlConfiguration();
		File oldfile = new File(this.instance.getDataFolder(),"config.yml");
		
		File backfile = new File(this.instance.getDataFolder(),"old-config.yml");
		
		if(oldfile.exists()) {
			try {
				oldconfig.load(oldfile);
			} catch (InvalidConfigurationException e) {
				log.error("&cError on loaded old config.yml.",e);
			}catch (IOException e) {
				log.error("&cError on loaded old config.yml.",e);
			}
			if(!oldconfig.getBoolean("newconfig")) {
				try {
					oldconfig.save(backfile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error("Error on save old-config.yml.",e);
				}
				oldfile.delete();
			}	
		}
		this.config = new CoreConfig(this.instance,"config",this.log,this.instance.getResource("config.yml"),true);
		log.seDebug(this.config.getBoolean("debug"));
		loadMessages();
		AllString.load(this.config, this.configMessages);
		this.configSpawn = new CoreConfig(this.instance,"spawn",this.log,this.instance.getResource("spawn.yml"),false);
		loadSpawn();
		this.configChat = new CoreConfig(this.instance,"chat",this.log,this.instance.getResource("chat.yml"),false);
		this.configInfoCommands = new CoreConfig(this.instance,"infocommands",this.log,this.instance.getResource("infocommands.yml"),false);
		loadInfoCommands();
		this.configCustomEvents = new CoreConfig(this.instance,"customevents",this.log,this.instance.getResource("customevents.yml"),false);
		loadCustomEvents();
		this.configItem = new CoreConfig(this.instance,"items",this.log,this.instance.getResource("items.yml"),false);
		loadItems();
		this.configMenus = new CoreConfig(this.instance,"menus",this.log,this.instance.getResource("menus.yml"),false);
		loadMenus();
		this.configBoard = new CoreConfig(this.instance,"board",this.log,this.instance.getResource("board.yml"),false);
		this.configAnnouncer = new CoreConfig(this.instance,"announcer",this.log,this.instance.getResource("announcer.yml"),false);
		
		this.configFirstJoin = new CoreConfig(this.instance,"firstjoin",this.log,this.instance.getResource("firstjoin.yml"),true);
		loadFirsJoinKit();
		/******************************************************************************************************************
		 *                                  Regsitro de canal de mensaje para bungeecord                                  *
		 ******************************************************************************************************************/
		Bukkit.getMessenger().registerOutgoingPluginChannel(this.instance, "BungeeCord");
		/******************************************************************************************************************
		 *                                          Registro de comandos                                                  *
		 ******************************************************************************************************************/
		this.log.info("Register commands...");
		SuperLobbyCMD mainCommand = new SuperLobbyCMD(this);
		mainCommand.addSubCommand(Arrays.asList("setspawn"), new SetSpawnCMD(this));
		mainCommand.addSubCommand(Arrays.asList("setspawnpermission"), new SetSpawnPermissionCMD(this));
		mainCommand.addSubCommand(Arrays.asList("remspawn"), new RemSpawnCMD(this));
		mainCommand.addSubCommand(Arrays.asList("reload"), new ReloadCMD(this));
		mainCommand.addSubCommand(Arrays.asList("help","?"), new HelpCMD(this));
		mainCommand.addSubCommand(Arrays.asList("openmenu"), new OpenMenuCMD(this));

	
		
		CoreCommands.registerCommand(mainCommand, this.loader);
		//this.getCommand("superlobby").setExecutor(mainCommand);
		
		/******************************************************************************************************************
		 *                                          Registro de eventos                                                   *
		 ******************************************************************************************************************/
		PluginManager pm = this.instance.getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this.instance);
		pm.registerEvents(new BlockListener(this), this.instance);
		pm.registerEvents(new EntityListener(this), this.instance);
		pm.registerEvents(new OthersListener(this), this.instance);
		
		new AlwaysDayNightRun(this).runTaskTimer(this.instance, 20L, 200L);
		CoreVariables.iniUcode();
		this.permission = setupVaultPermissions();
		if(this.permission != null) {
			log.alert("Hooked Vault Permissions");
			CoreVariables.permission(permission);
		}
		this.chat = setupChat();
		if(this.chat != null) {
			log.alert("Hooked Vault Chat");
			CoreVariables.chat(chat);
		}
		this.economy = setupEconomy();
		if(this.economy != null) {
			log.alert("Hooked Vault economy");
			CoreExecuteComands.economy(economy);
		}
		this.playerPoints = setupPlayerPoints();
		if(this.playerPoints != null) {
			log.alert("Hooked PlayerPoints");
			CoreExecuteComands.playerPoints(playerPoints);
		}
		
		if(config.getBoolean("disable-tab-complete.enable")) {
			
			if(CoreUtils.Version.getVersion().esMenor(CoreUtils.Version.v1_13)) {
				if(setupProtocolLib()) {
					log.alert("Hooked ProtocolLib");
					TapComplete tab = new TapComplete(this);
					tab.onTabCcompletePre1_13();				

				}
			}else {
				pm.registerEvents(new TapCompleteListener(this), this.instance);
			}
		}
		
		if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
			log.alert("Hooked PlaceholderAPI");
			CoreVariables.placeholderAPI(true);
		}
		if(CoreViaVersion.Setup()) {
			log.alert("Hooked ViaVersion");
			
		}
		if(config.getInt("join-tab.update") > 0){
			long pe = config.getInt("join-tab.update");
			new TabUpdateRun(this).runTaskTimer(this.instance, 0L, pe);
		}
		if(this.configBoard.getBoolean("settings-enable")){
			new BoardAnimationRun(this).runTaskTimer(this.instance, 0L, 1L);
		}
		this.announcer = new Announcer(this);
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this.instance,457);
		checkForUpdates();
		log.info("&7A total of &b"+ menus.size() +" &7menus were loaded.");
		this.log.line();
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void loadFirsJoinKit() {
		if(this.configFirstJoin.getBoolean("kit.enabled")) {
			firsjoinkit = new ArrayList<ItemStack>();
    		for(String mate_data_amount: this.configFirstJoin.getStringList("kit.items")) {
    			String mate_data;
    			int amount = 1;
    			if(mate_data_amount.contains(",")) {
    				mate_data = mate_data_amount.split(",")[0];
    				amount = CoreUtils.toint(mate_data_amount.split(",")[1]);
    			}else {
    				mate_data = mate_data_amount;
    			}
    			
    			if(Material.getMaterial(mate_data.contains(":") ? mate_data.split(":")[0].trim() : mate_data.trim().toUpperCase()) == null) {
					this.getLog().error("FirstJoin: The item " + mate_data.trim().toUpperCase() + " has an invalid item Material." );
				}else {
					String mate;
					Short data = 0;
					if(mate_data.contains(":")) {
						mate = mate_data.split(":")[0].trim();
						data = Short.valueOf(mate_data.split(":")[1].trim());
					}else {
						mate = mate_data;
						data = 0;
					}
					ItemStack item = new ItemStack(Material.getMaterial(mate.toUpperCase()), amount);
					if (data != null) {
						 item.setDurability(data.shortValue());
					}	
					firsjoinkit.add(item);
				}
    		}      		
    	}
	}
	
	
	
	public ItemStack getBook(Player player) {
	    ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
	    BookMeta meta = (BookMeta)book.getItemMeta();
	    if (meta == null) {
	    	return null; 
	    }
	      
	    List<String> coloredPages = new ArrayList<String>();
	    
	    for (String page : this.configFirstJoin.getStringList("written-books.pages")) {
	    	page = CoreVariables.replace(page, player);
	    	coloredPages.add(page);
	    } 
	    meta.setPages(coloredPages);
	    meta.setTitle(CoreColor.colorCodes(this.configFirstJoin.getString("written-books.title")));
	    meta.setDisplayName(CoreColor.colorCodes(this.configFirstJoin.getString("written-books.name")));
	    meta.setAuthor(player.getName());
	    book.setItemMeta(meta);
	    return book;
	  }
	
	
	@Override
    public void onDisable() {
		CoreBossBar.canselAll();
    }
	
	
	public void checkForUpdates() {
		if(config.getBoolean("update-check")){
			new BukkitRunnable() {
				public void run() {
					CoreSpigotUpdater updater = new CoreSpigotUpdater(instance, 20400);
		        	try {
		                if (updater.checkForUpdates()) {
		                	log.alert("An update was found! for SuperLobby. Please update to recieve latest version. download: " + updater.getResourceURL());
		                }	
		            } catch (Exception e) {
		            	
		            	log.error("Failed to check for a update on spigot.");
		            }
				}
        		
        	}.runTask(this.instance);
        	
        	
        } 
    }
	
	
	
	public  boolean setupProtocolLib()
	{
		if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
	    	return false;
	    }
	    return true;
	}
	
	
	
	public void loadSpawn() {
		spawnss =  new HashMap<String, Spawns>();
		Set<String> k =  configSpawn.getKeys(false);
		if (k != null && !k.isEmpty() && k.size()> 0) {
			for(String s : k) {
				if(configSpawn.contains(s+".permission")) {
					 String perm = configSpawn.getString(s+".permission");
					 World w = this.instance.getServer().getWorld(configSpawn.getString(s+".world"));
				     double x = configSpawn.getDouble(s+".x");
					 double y = configSpawn.getDouble(s+".y");
					 double z = configSpawn.getDouble(s+".z");
					 int yaw = configSpawn.getInt(s+".yaw");
					 int pi = configSpawn.getInt(s+".pi");
					 
					 if(spawnss.containsKey(perm)) {
						 spawnss.get(perm).add(new Location(w,x,y,z,yaw,pi));
					 }else {
						 Spawns spawn = new Spawns();
						 spawn.add(new Location(w,x,y,z,yaw,pi));
						 spawnss.put(perm, spawn);
					 }
				 }else {
					 World w = this.instance.getServer().getWorld(configSpawn.getString(s+".world"));
				     double x = configSpawn.getDouble(s+".x");
					 double y = configSpawn.getDouble(s+".y");
					 double z = configSpawn.getDouble(s+".z");
					 int yaw = configSpawn.getInt(s+".yaw");
					 int pi = configSpawn.getInt(s+".pi");
					 if(spawnss.containsKey("none")) {
						 spawnss.get("none").add(new Location(w,x,y,z,yaw,pi));
					 }else {
						 Spawns spawn = new Spawns();
						 spawn.add(new Location(w,x,y,z,yaw,pi));
						 spawnss.put("none", spawn);
					 }
				 }
			}
			 
		}
	}
	
	public void teleportToSpawn(final Player p) {
		
		 if (this.spawnss.isEmpty()) {
			 Bukkit.getScheduler().scheduleSyncDelayedTask(this.instance, new Runnable() {
					public void run() {
						CoreColor.message(p, AllString.prefix + AllString.error_spawn_not_set);
					}
				}, 10);
		 }else {
			 boolean vipSpawn = false;
			 for(String perm : this.spawnss.keySet()) {
				 if(p.hasPermission(perm) && perm!= "none") {
					 p.teleport(spawnss.get(perm).nextSpawn());
					 vipSpawn = true;
					 break;
				 }
			 }
			 if(!vipSpawn) {
				 if(spawnss.containsKey("none")) {
					 p.teleport(spawnss.get("none").nextSpawn());
				 }else {
					 CoreColor.message(p, AllString.prefix + AllString.error_spawn_not_set);
				 }
			 }
		 }
	}
	
	
	
	public void loadMenus(){
		menus = new HashMap<String, CoreMenu>();
		Set<String> keymenu =  configMenus.getKeys(false);
		for (String nodomenu : keymenu){
			ConfigurationSection sectionmenu = configMenus.getConfigurationSection(nodomenu);
			CoreMenu menu = new CoreMenu(sectionmenu.getString("settings-name"),sectionmenu.getConfigurationSection("items"),this.loader);
			menu.setPerm(sectionmenu.getString("settings-permission"));
			menu.setRows(sectionmenu.getInt("settings-rows"));
			menu.setCommands(sectionmenu.getString("settings-open-commands"));
			menu.setSound(sectionmenu.getString("settings-open-sound"));
			menu.setGlassEnable(sectionmenu.getBoolean("settings-glass-enable"));
			menu.setGlasscolor(sectionmenu.getInt("settings-glass-color"));
			if ((sectionmenu.isSet("settings-worlds")) && (sectionmenu.isList("settings-worlds"))) {
				menu.setWorlds(sectionmenu.getStringList("settings-worlds"));
	        }
			if ((sectionmenu.isSet("settings-world")) && (sectionmenu.getString("settings-world")!="" )) {
				menu.setWorld(sectionmenu.getString("settings-world"));
	        }
			
			menu.load("https://pedrojm96.com/minecraft-plugin/superlobbydeluxe/sl-menus-yml/");
			menus.put(nodomenu, menu);
			
			if(this.config.getBoolean("menu") && menu.getCommands() != null) {
				CommandsMenu cm = new CommandsMenu(this,nodomenu);
	    		CoreCommands.registerCommand(cm, this.loader);	
			 }
	
		}	
	}
	
	
	
	public void loadItems(){
		items = new ArrayList<CoreItem>();
		Set<String> keys = configItem.getKeys(false);
		for (String key : keys)
		{
			ConfigurationSection nodo = configItem.getConfigurationSection(key);
			if (!nodo.isSet("name"))
			{
				log.error("Lobby-Item: The item " + key + " has no name!");
			}
			else if ((!nodo.isSet("material")) && (!nodo.isSet("material-old")))
			{
				log.debug("Lobby-Item: The item " + key + " does not have a defined material value, using id instead.!");
				if(!nodo.isSet("id")) {
					log.error("Lobby-Item: The item " + key + " has no Material or ID!");
				}
				else if ((nodo.getInt("id") == 0) || (CoreMaterial.getMaterial(nodo.getInt("id")) == null))
				{
			    	  log.error("Lobby-Item: The item " + key + " has an invalid item ID: " + nodo.getInt("id") + ".");
				}else {

					CoreItem item = new CoreItem(CoreMaterial.getMaterial(nodo.getInt("id")));
					item.setDelay(Integer.valueOf(nodo.getInt("delay")));
					item.setPerm(nodo.getString("permission"));
					item.setName(nodo.getString("name"));
					item.setSlot(Integer.valueOf(nodo.getInt("slot")));
					item.setEnchantGlow(nodo.getBoolean("enchant-glow"));
					item.setData(Short.valueOf((short)nodo.getInt("data")));
					item.setVersionCheck(nodo.getBoolean("version-check"));
					item.setVersionList(nodo.getStringList("version-list"));
					item.setNoVersionMessage(nodo.getString("no-version-message"));
					if ((nodo.isSet("lore")) && (nodo.isList("lore"))) {
			        	item.setLore(nodo.getStringList("lore"));
			        }
					if((nodo.contains("skull")) && (nodo.isSet("skull"))){
			        	 item.setSkull(nodo.getString("skull"));
			        }
			        if ((nodo.isSet("commands")) && (nodo.isList("commands"))) {
			        	item.setCommands(nodo.getStringList("commands"));
			        }
			        items.add(item);
					
				}
			}
			else 
			{
				String mate_data;
				if(CoreUtils.isPre1_13()) {
					if(nodo.isSet("material-old")) {
						mate_data = nodo.getString("material-old");
					}else {
						mate_data = nodo.getString("material");
					}
				}else {
					if(nodo.isSet("material")) {
						mate_data = nodo.getString("material");
					}else {
						mate_data = nodo.getString("material-old");
					}
				}
				
				if(Material.getMaterial(mate_data.contains(":") ? mate_data.split(":")[0].trim() : mate_data) == null) {
					log.error("Lobby-Item: The item " + key + " has an invalid item Material: " + (mate_data.contains(":") ? mate_data.split(":")[0].trim() : mate_data) + ".");
				}else {
					String mate;
					short data;
					if(mate_data.contains(":")) {
						mate = mate_data.split(":")[0].trim();
						data = Short.valueOf(mate_data.split(":")[1].trim());
					}else {
						mate = mate_data;
						data = Short.valueOf((short)nodo.getInt("data"));
					}
					
					
					
					CoreItem item = new CoreItem(Material.getMaterial(mate));
					item.setDelay(Integer.valueOf(nodo.getInt("delay")));
					item.setPerm(nodo.getString("permission"));
					item.setName(nodo.getString("name"));
					item.setSlot(Integer.valueOf(nodo.getInt("slot")));
					item.setEnchantGlow(nodo.getBoolean("enchant-glow"));
					item.setData(data);
					if ((nodo.isSet("lore")) && (nodo.isList("lore"))) {
			        	item.setLore(nodo.getStringList("lore"));
			        }
					if((nodo.contains("skull")) && (nodo.isSet("skull"))){
			        	 item.setSkull(nodo.getString("skull"));
			        }
			        if ((nodo.isSet("commands")) && (nodo.isList("commands"))) {
			        	item.setCommands(nodo.getStringList("commands"));
			        }
			        items.add(item);
				}
			}
		}
	}
	public void loadInfoCommands(){
		this.infoCommands = new ArrayList<InfoCommand>();
		Set<String> key = configInfoCommands.getKeys(false);
		for(String nodo: key){
			ConfigurationSection a = configInfoCommands.getConfigurationSection(nodo);
			if (!a.isSet("command"))
			{
				log.alert("InfoCommands: The nodo " + nodo + " has no command!");
				continue;
			}
			
			if ((a.isSet("info")) && (a.isList("info"))) {
				InfoCommand infoc = new InfoCommand(a.getString("command"));
				infoc.setPermission(a.getString("permission"));
				List<String> lista = a.getStringList("info");
				infoc.setInfo(lista);
				infoCommands.add(infoc);
				if(config.getBoolean("info-commands")) {
					 CommandsInfo cm = new CommandsInfo(this,infoc);
			    	 CoreCommands.registerCommand(cm, this.loader);	
				}
			}else {
				log.alert("InfoCommands: The nodo " + nodo + " has no info!");
			}
			
			
		}
	}
	public void loadCustomEvents(){
		customEvents = new ArrayList<CustomEvents>();
		Set<String> key = configCustomEvents.getKeys(false);
		for(String nodo: key){
			ConfigurationSection a = configCustomEvents.getConfigurationSection(nodo);
			if (!a.isSet("permission"))
			{
				log.alert("CustomEvents: The customjoin " + nodo + " has no permission!");
			}
			CustomEvents cj = new CustomEvents(a.getString("permission"),this.instance);
			if (a.isSet("join-message")){
				cj.setJoinMessage(a.getString("join-message"));
			}
			if (a.isSet("quit-message")){
				cj.setQuitMessage(a.getString("quit-message"));
			}
			if (a.isSet("join-firework")){
				cj.setJoinFirework(a.getString("join-firework"));
			}
			
			if (a.isSet("join-sound-old")){
				if(!Utils.isEnum(Sound.class, a.getString("join-sound-old").toUpperCase()) && !a.getString("join-sound-old").equalsIgnoreCase("random")) {
					if (a.isSet("join-sound")) {
						cj.setJoinSound(a.getString("join-sound"));
					}
				}else{
					cj.setJoinSound(a.getString("join-sound-old"));
				}
			}else if (a.isSet("join-sound")){
				cj.setJoinSound(a.getString("join-sound"));
			}
			
			if(a.isSet("join-sound-global")) {
				cj.setJoinSoundGlobal(true);
			}
			if (a.isSet("join-gamemode")){
				cj.setJoinGamemode(a.getString("join-gamemode"));
			}
			if (a.isSet("join-comands")){
				cj.setJoinComands(a.getStringList("join-comands"));
			}
			customEvents.add(cj);      
		}
	}
	
	
	public Permission setupVaultPermissions()
	{
		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return null;
	    }	
		Permission retorno = null;
	    RegisteredServiceProvider<Permission> rsp = this.instance.getServer().getServicesManager().getRegistration(Permission.class);
	    try{
	    	retorno = ((Permission)rsp.getProvider());
	    }catch(NullPointerException e){
	    	log.debug("Failed to get the vault permissions provider.", e);
	    	retorno = null;
	    }
	    return retorno;
	}
	public Chat setupChat()
	{
		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return null;
	    }
		Chat retorno = null;
	    RegisteredServiceProvider<Chat> rsp = this.instance.getServer().getServicesManager().getRegistration(Chat.class);
	    try{
		   retorno = ((Chat)rsp.getProvider());
	    }catch(NullPointerException e){
	    	log.debug("Failed to get the vault chat provider.", e);
	    }
	    return retorno;
	}
	

	public Economy setupEconomy()
	{
		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
	    	return null;
	    }
		Economy retorno = null;
	    RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
	    try{
	    	retorno = ((Economy)rsp.getProvider());
	    }catch(NullPointerException e){
	    	log.debug("Failed to get the vault economy provider.", e);
	    }
	    return retorno;
	}
	
	public PlayerPoints setupPlayerPoints()
	{
		if (Bukkit.getPluginManager().getPlugin("PlayerPoints") == null) {
	    	return null;
	    }
		PlayerPoints pointsPlugin = (PlayerPoints)Bukkit.getPluginManager().getPlugin("PlayerPoints");
	    return pointsPlugin;
	}
	
	
	
	
	public void loadMessages(){
		String m = config.getString("messages").toUpperCase();
		if(m=="EN") {
			this.configMessages = new CoreConfig(this.instance,"messages_EN",this.log,this.instance.getResource("messages_EN.yml"),true);
		}else if(m=="ES") {
			this.configMessages = new CoreConfig(this.instance,"messages_ES",this.log,this.instance.getResource("messages_ES.yml"),true);
		}else if(m=="NL") {
			this.configMessages = new CoreConfig(this.instance,"messages_NL",this.log,this.instance.getResource("messages_NL.yml"),true);
		}else if(m=="ZH") {
			this.configMessages = new CoreConfig(this.instance,"messages_ZH",this.log,this.instance.getResource("messages_ZH.yml"),true);
		}else if(m=="RU") {
			this.configMessages = new CoreConfig(this.instance,"messages_RU",this.log,this.instance.getResource("messages_RU.yml"),true);
		}else {
			this.configMessages = new CoreConfig(this.instance,"messages_EN",this.log,this.instance.getResource("messages_EN.yml"),true);
		}
	}
	
	public void reloadTab(){
		if (this.config.getBoolean("join-tab.enable")){
			for(Player p : Bukkit.getOnlinePlayers()){
				String h = this.config.getString("join-tab.header");
				h = CoreVariables.replace(h, p);
				String f = this.config.getString("JoinTab.Footer");
				f = CoreVariables.replace(f, p);
				CorePlayerListHeaderFooter.sendHeaderFooter(p, h, f);
			}
		}
	}
	
	public void reloadItems(){
		if (this.config.getBoolean("lobby-items.enable")){
			for(Player p : Bukkit.getOnlinePlayers()){
				if (this.config.getStringList("lobby-items.world").contains(p.getPlayer().getWorld().getName())) {
					for(CoreItem item : this.items) {
						
							if(item.hasPerm(p)) {
								item.give(p, this.loader);
							}else {
								this.log.debug("The player "+p.getName() + " do not have permission to receive the item "+item.getName());
							}
						
					}
				}
			}
		}
	}
	

	public CoreLog getLog() {
		// TODO Auto-generated method stub
		return this.log;
	}

	public JavaPlugin getInstance() {
		// TODO Auto-generated method stub
		return this.loader.getInstance();
	}



	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		
	}
	
}
