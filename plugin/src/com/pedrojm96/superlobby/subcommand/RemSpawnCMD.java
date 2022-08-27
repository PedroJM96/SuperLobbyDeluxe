package com.pedrojm96.superlobby.subcommand;

import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.command.CoreSubCommand;
import com.pedrojm96.superlobby.AllString;
import com.pedrojm96.superlobby.SuperLobby;


public class RemSpawnCMD extends CoreSubCommand{

	private SuperLobby plugin;
	
	public RemSpawnCMD(SuperLobby plugin) {
		this.plugin = plugin;
		this.plugin.log.info("Register sub-command remspawn");
	}
	
	
	@Override
	public boolean onSubCommand(CommandSender sender, String[] args) {
		
		if (!(sender instanceof Player)) {
			CoreColor.message(sender,AllString.prefix + AllString.error_no_console);
       	 	return true;
		}
		if(args.length <= 0){
			CoreColor.message(sender, AllString.prefix + AllString.use_command_remspawn);
			return true;	
		}
		
		if(args[0].equalsIgnoreCase("all")){
			Set<String> k =  plugin.configSpawn.getKeys(false);
			if (k == null || k.isEmpty()||k.size()== 0){
				CoreColor.message(sender,AllString.prefix + AllString.error_no_spawn);
				return true;
			} 
			for(String s : k){
				if(plugin.configSpawn.contains(s.toLowerCase())){
					plugin.configSpawn.setNull(s.toLowerCase());
					plugin.configSpawn.save();
					CoreColor.message(sender,AllString.prefix + AllString.rem_spawn.replaceAll("<spawn>", s.toLowerCase()));
				}
			}
			return true;
		}
		
		if(!this.plugin.configSpawn.contains(args[0])) {
			CoreColor.message(sender,AllString.prefix + AllString.error_no_spawn);
			return true;
		}
		plugin.configSpawn.setNull(args[0].toLowerCase());
		plugin.configSpawn.save();
		CoreColor.message(sender,AllString.prefix + AllString.rem_spawn.replaceAll("<spawn>", args[0].toLowerCase()));
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
		
		if(args.length == 1) {
			Set<String> k =  plugin.configSpawn.getKeys(false);
			if (k == null || k.isEmpty()||k.size()== 0){
				return list;
			} 
			
			for(String s : k){
				if(!args[0].isEmpty() || args[0]!=null) {
					if(s.startsWith(args[0]) ){
						list.add(s);
					}else {
						continue;
					}
				}else {
					list.add(s);
				}
			}
			
			if(!args[0].isEmpty() || args[0]!=null) {
				if("all".startsWith(args[0]) ){
					list.add("all");
				}
			}else {
				list.add("all");
			}
		}else {
			return list;
		}
		
		
		return (list.isEmpty() ? null: list);
	}

}
