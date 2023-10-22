package com.pedrojm96.superlobby;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.command.CorePluginCommand;



public class SuperLobbyCMD extends CorePluginCommand{

	public SuperLobbyCMD(SuperLobby plugin) {
		plugin.log.info("Register main command superlobby");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String command, String[] args) {
		CoreColor.message(sender, AllString.prefix + AllString.use_command);
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
		return "superlobby.use";
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return  Arrays.asList("sl" );
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return "/<command>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "SuperLobby.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "superlobby";
	}


	@Override
	public List<String> onCustomTabComplete(CommandSender sender, List<String> list, String[] args) {
		// TODO Auto-generated method stub
		return list;
	}

	

}
