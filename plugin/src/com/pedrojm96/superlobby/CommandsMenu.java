package com.pedrojm96.superlobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.command.CorePluginCommand;
import com.pedrojm96.core.inventory.menu.CoreMenu;



public class CommandsMenu extends CorePluginCommand{

	
	private SuperLobby plugin;
	
	private String menu = null;

	
	public CommandsMenu(SuperLobby plugin,String menuname){
		this.plugin = plugin;
		this.menu = menuname;
		plugin.log.info("Register command menu /"+plugin.menus.get(menu).getCommands());
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, String command, String[] args) {
		// TODO Auto-generated method stub
		
		if (!(sender instanceof Player)) {
			CoreColor.message(sender,AllString.prefix + AllString.error_no_console);
       	 	return true;
		}
		
		Player player = (Player)sender;
		
		CoreMenu m = plugin.menus.get(menu);
		
		if(m.getWorlds() != null ) {
			boolean isworld =false;
			for(String world : m.getWorlds()) {
				 if(world.equalsIgnoreCase(player.getWorld().getName())) {
					  isworld = true;
					  break;  
		    	  } 
			 }
			 if(!isworld) {
				 CoreColor.message(player, AllString.prefix + AllString.error_no_world);
	    		 return true; 
			 }
	     }
	     if(m.getSound()!=null){
	    	 player.playSound(player.getLocation(), m.getSound(), 1.0F, 1.0F);
	     }
	     m.open(player);
	     return true;
	}

	@Override
	public String getPerm() {
		// TODO Auto-generated method stub
		return plugin.menus.get(menu).getPerm();
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
		return plugin.menus.get(menu).getCommands();
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return "/"+plugin.menus.get(menu).getCommands();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "SuperLobby menu commands /"+plugin.menus.get(menu).getCommands()+" commands";
	}

}
