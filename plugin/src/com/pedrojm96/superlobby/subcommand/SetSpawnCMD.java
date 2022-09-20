package com.pedrojm96.superlobby.subcommand;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.command.CoreSubCommand;
import com.pedrojm96.superlobby.AllString;
import com.pedrojm96.superlobby.SuperLobby;


public class SetSpawnCMD extends CoreSubCommand{

	private SuperLobby plugin;
	
	public SetSpawnCMD(SuperLobby plugin) {
		this.plugin = plugin;
		this.plugin.log.info("Register sub-command setspawn");
	}
	
	
	@Override
	public boolean onSubCommand(CommandSender sender, String[] args) {
		
		if (!(sender instanceof Player)) {
			CoreColor.message(sender,AllString.prefix + AllString.error_no_console);
       	 	return true;
		}
		if(args.length <= 0){
			CoreColor.message(sender, AllString.prefix + AllString.use_command_setspawn);
			return true;	
		}
		String name = args[0];
		Player p = (Player) sender;
		this.plugin.configSpawn.set(name+".world", p.getLocation().getWorld().getName());
		this.plugin.configSpawn.set(name+".x", p.getLocation().getX());
		this.plugin.configSpawn.set(name+".y", p.getLocation().getY());
		this.plugin.configSpawn.set(name+".z", p.getLocation().getZ());
		this.plugin.configSpawn.set(name+".yaw", p.getLocation().getYaw());
		this.plugin.configSpawn.set(name+".pi", p.getLocation().getPitch());
		
		this.plugin.configSpawn.save();
		CoreColor.message(sender,AllString.prefix + AllString.set_spawn.replaceAll("<spawn>", name.toLowerCase()));
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
