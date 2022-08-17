package com.pedrojm96.superlobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.CoreVariables;
import com.pedrojm96.core.command.CorePluginCommand;



public class CommandsInfo extends CorePluginCommand{

	

	private InfoCommand infocommand;
	
	public CommandsInfo(SuperLobby plugin,InfoCommand infocommand){
		this.infocommand = infocommand;
		plugin.log.info("Register info command /"+infocommand.getCommand());
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, String command, String[] args) {
		// TODO Auto-generated method stub
		
		if (!(sender instanceof Player)) {
			CoreColor.message(sender,AllString.prefix + AllString.error_no_console);
       	 	return true;
		}
		
		Player player = (Player)sender;
		
		for(String m : infocommand.getInfo()){
			 player.sendMessage(CoreVariables.replace(m, player));
		}
	    return true;
	}

	@Override
	public String getPerm() {
		// TODO Auto-generated method stub
		return infocommand.getPermission();
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
		return infocommand.getCommand();
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return "/"+infocommand.getCommand();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "SuperLobby info commands /"+infocommand.getCommand()+" commands";
	}

}
