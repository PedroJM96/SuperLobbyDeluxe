package com.pedrojm96.superlobby.subCommand;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.command.CoreSubCommand;
import com.pedrojm96.superlobby.AllString;
import com.pedrojm96.superlobby.SuperLobby;


public class HelpCMD extends CoreSubCommand{

	private SuperLobby plugin;
	
	public HelpCMD(SuperLobby plugin) {
		this.plugin = plugin;
		this.plugin.log.info("Register sub-command help");
	}
	
	
	@Override
	public boolean onSubCommand(CommandSender sender, String[] args) {
		CoreColor.message(sender, "&e&m---------------------------------------------------");
		CoreColor.message(sender, "");
		CoreColor.message(sender,AllString.prefix + AllString.help_command_setSpawn);
		CoreColor.message(sender,AllString.prefix + AllString.help_description_setSpawn);
		CoreColor.message(sender,AllString.prefix + AllString.help_command_remSpawn);
		CoreColor.message(sender,AllString.prefix + AllString.help_description_remSpawn);
		
		CoreColor.message(sender,AllString.prefix + AllString.help_command_openmenu);
		CoreColor.message(sender,AllString.prefix + AllString.help_description_openmenu);
		
		CoreColor.message(sender,AllString.prefix + AllString.help_command_reload);
		CoreColor.message(sender,AllString.prefix + AllString.help_description_reload);
		CoreColor.message(sender, "");
		CoreColor.message(sender, "&e&m---------------------------------------------------");
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
		return null;
	}


	@Override
	public List<String> onCustomTabComplete(CommandSender sender, List<String> list, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
