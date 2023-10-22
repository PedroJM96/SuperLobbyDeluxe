package com.pedrojm96.superlobby.subcommand;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.command.CoreSubCommand;
import com.pedrojm96.core.inventory.menu.CoreMenu;
import com.pedrojm96.superlobby.AllString;
import com.pedrojm96.superlobby.SuperLobby;


public class OpenMenuCMD extends CoreSubCommand{

	private SuperLobby plugin;
	
	public OpenMenuCMD(SuperLobby plugin) {
		this.plugin = plugin;
		this.plugin.log.info("Register sub-command openmenu");
	}
	
	
	@Override
	public boolean onSubCommand(CommandSender sender, String[] args) {
		
		
		if(args.length <= 1){
			CoreColor.message(sender, AllString.prefix + AllString.use_command_openmenu);
			return true;	
		}
		String playername = args[0].toLowerCase();
		
		if((Bukkit.getPlayerExact(playername) == null) || (!Bukkit.getPlayerExact(playername).isOnline())) {
			CoreColor.message(sender,AllString.prefix + AllString.error_no_online);
			return true;
		}
		
		String menuname = args[1].toLowerCase();
		
		if(!plugin.menus.containsKey(menuname)) {
			CoreColor.message(sender,AllString.prefix + AllString.error_no_menu);
			return true;
		}
		
		CoreMenu menu = plugin.menus.get(menuname);

		Player player = Bukkit.getPlayerExact(playername);
		if(menu.getSound()!=null){
	    	 player.playSound(player.getLocation(), menu.getSound(), 1.0F, 1.0F);
	    }
		
		menu.open(player);
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
