package com.pedrojm96.superlobby.subcommand;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.command.CoreSubCommand;
import com.pedrojm96.superlobby.AllString;
import com.pedrojm96.superlobby.SuperLobby;


public class SetSpawnPermissionCMD extends CoreSubCommand{

	private SuperLobby plugin;
	
	public SetSpawnPermissionCMD(SuperLobby plugin) {
		this.plugin = plugin;
		this.plugin.log.info("Register sub-command setspawnpermission");
	}
	
	
	@Override
	public boolean onSubCommand(CommandSender sender, String[] args) {
		
		if (!(sender instanceof Player)) {
			CoreColor.message(sender,AllString.prefix + AllString.error_no_console);
       	 	return true;
		}
		if(args.length <= 1){
			CoreColor.message(sender, AllString.prefix + AllString.use_command_setspawn_permission);
			return true;	
		}
		String name = args[0];
		String perm = args[1];
		if(!this.plugin.configSpawn.contains(name)) {
			CoreColor.message(sender,AllString.prefix + AllString.error_no_spawn);
			return true;
		}
		this.plugin.configSpawn.set(name+".permission", perm);
		this.plugin.configSpawn.save();
		CoreColor.message(sender,AllString.prefix + AllString.set_spawn.replaceAll("<spawn>", name.toLowerCase()));
		this.plugin.loadSpawn();
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
