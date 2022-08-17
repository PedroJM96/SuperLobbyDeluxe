package com.pedrojm96.superlobby.subCommand;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.CoreConfig;
import com.pedrojm96.core.command.CoreSubCommand;
import com.pedrojm96.core.effect.CoreBossBar;
import com.pedrojm96.superlobby.AllString;
import com.pedrojm96.superlobby.PlayerBoard;
import com.pedrojm96.superlobby.SuperLobby;


public class ReloadCMD extends CoreSubCommand{

	private SuperLobby plugin;
	
	public ReloadCMD(SuperLobby plugin) {
		this.plugin = plugin;
		this.plugin.log.info("Register sub-command reload");
	}
	
	
	@Override
	public boolean onSubCommand(CommandSender sender, String[] args) {
		CoreBossBar.canselAll();
		plugin.config = new CoreConfig(plugin,"config",plugin.log,plugin.getResource("config.yml"),true);
		plugin.reloadTab();
		plugin.loadMessages();
		AllString.load(plugin.config, plugin.configMessages);
		plugin.configItem = new CoreConfig(plugin,"items",plugin.log,plugin.getResource("items.yml"),false);
		plugin.loadItems();
		plugin.reloadItems();
		plugin.configMenus = new CoreConfig(plugin,"menus",plugin.log,plugin.getResource("menus.yml"),false);
		plugin.loadMenus();
		plugin.configSpawn = new CoreConfig(plugin,"spawn",plugin.log,plugin.getResource("spawn.yml"),false);
		plugin.configInfoCommands = new CoreConfig(plugin,"infocommands",plugin.log,plugin.getResource("infocommands.yml"),false);
		plugin.loadInfoCommands();
		plugin.configCustomEvents = new CoreConfig(plugin,"customevents",plugin.log,plugin.getResource("customevents.yml"),false);
		plugin.loadCustomEvents();
		plugin.configAnnouncer = new CoreConfig(plugin,"announcer",plugin.log,plugin.getResource("announcer.yml"),false);
		plugin.announcer.reload();
		
		plugin.configFirstJoin = new CoreConfig(plugin,"firstjoin",plugin.log,plugin.getResource("firstjoin.yml"),true);
		plugin.loadFirsJoinKit();
		plugin.configBoard = new CoreConfig(plugin,"board",plugin.log,plugin.getResource("board.yml"),false);
		plugin.playerBoards = new HashMap<Player, PlayerBoard>();
		if(plugin.configBoard.getBoolean("settings-enable")){
			for(Player p : Bukkit.getOnlinePlayers()){
				if(plugin.configBoard.getStringList("settings-world").contains(p.getWorld().getName())) {
					PlayerBoard board = new PlayerBoard(p,plugin.configBoard.getStringList("settings-title"),plugin.configBoard.getInt("settings-update-title"));
					board.setupLines( plugin.configBoard.getConfigurationSection("board"));
					board.send();
					plugin.playerBoards.put(p, board);
				}
			}
		}
		CoreColor.message(sender,AllString.prefix + AllString.config_loaded);
		return true;
	}

	@Override
	public String getErrorNoPermission() {
		// TODO Auto-generated method stub
		return AllString.prefix + AllString.error_no_permission;
	}

	@Override
	public String getPerm() {
		// TODO Auto-generated method stub
		return "superlobby.admin";
	}





	@Override
	public List<String> onCustomTabComplete(CommandSender sender, List<String> list, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
