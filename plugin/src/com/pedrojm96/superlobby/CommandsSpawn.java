package com.pedrojm96.superlobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.command.CorePluginCommand;



public class CommandsSpawn extends CorePluginCommand{

	
	private SuperLobby plugin;
	

	public CommandsSpawn(SuperLobby plugin){
		this.plugin = plugin;
		plugin.log.info("Register command  /spawn");
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, String command, String[] args) {
		// TODO Auto-generated method stub
		
		if (!(sender instanceof Player)) {
			CoreColor.message(sender,AllString.prefix + AllString.error_no_console);
       	 	return true;
		}
		
		Player player = (Player)sender;
		if(plugin.config.getBoolean("command-spawn")){
			 
			
			if(!player.hasPermission("superlobby.use")){
				CoreColor.message(player, AllString.prefix + AllString.error_no_permission);
				return true; 
			 }
			
			if(args.length <= 0){
				 plugin.teleportToSpawn(player);
				 return true;
			  }
			 if(args.length <= 1){
				 String name = args[0];
				 plugin.teleportToSpawn(player,name);
				 return true;
			  }
			 if(!player.hasPermission("superlobby.admin")) {
				 CoreColor.message(player, AllString.prefix + AllString.error_no_permission);
				 return true;
			 }
			 
			  String name = args[0];
			  String playername = args[1];
			  if((Bukkit.getPlayerExact(playername) == null) || (!Bukkit.getPlayerExact(playername).isOnline())) {
					CoreColor.message(sender,AllString.prefix + AllString.error_no_online);
					return true;
			   }
			   Player player2 = Bukkit.getPlayerExact(playername);
			   plugin.teleportToSpawn(player2,name); 
			   return true;
			   
		 }
	     return true;
	}

	@Override
	public String getPerm() {
		// TODO Auto-generated method stub
		return "superlobby.use";
	}


	@Override
	public String getErrorNoPermission() {
		// TODO Auto-generated method stub
		return AllString.prefix + AllString.error_no_permission;
	}

	@Override
	public List<String> onCustomTabComplete(CommandSender sender, List<String> list, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "spawn";
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return "/spawn";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "SuperLobby spawn commands.";
	}

}
