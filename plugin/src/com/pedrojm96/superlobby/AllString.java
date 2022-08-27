package com.pedrojm96.superlobby;

import com.pedrojm96.core.CoreConfig;

public class AllString {
	
	public static String prefix;
	public static String error_no_console;
	public static String error_no_permission;
	public static String use_command;
	public static String use_command_setspawn;
	public static String set_spawn;
	public static String error_spawn_not_set;
	public static String click_wait;
	public static String error_no_command;
	public static String error_no_world;
	public static String no_money;
	public static String use_command_remspawn;
	public static String error_no_spawn;
	public static String rem_spawn;
	public static String config_loaded;
	public static String use_command_setspawn_permission;
	public static String use_command_openmenu;
	public static String error_no_online;
	public static String error_no_menu;
	public static String help_command_reload;
	public static String help_description_reload;
	public static String help_command_setSpawn;
	public static String help_description_setSpawn;
	public static String help_command_remSpawn;
	public static String help_description_remSpawn;
	public static String help_command_openmenu;
	public static String help_description_openmenu;
	
	
	public static void load(CoreConfig config,CoreConfig messages) {
		prefix = config.getString("prefix");
		error_no_console = messages.getString("error-no-console");
		error_no_permission = messages.getString("error-no-permission");
		use_command = messages.getString("use-command");
		use_command_setspawn = messages.getString("use-command-setspawn");
		set_spawn = messages.getString("set-spawn");
		error_spawn_not_set = messages.getString("error-spawn-not-set");
		click_wait = messages.getString("click-wait");
		error_no_command = messages.getString("error-no-command"); 
		error_no_world = messages.getString("error-no-world");
		no_money = messages.getString("no-money");
		use_command_remspawn = messages.getString("use-command-remspawn");
		error_no_spawn = messages.getString("error-no-spawn");
		rem_spawn = messages.getString("rem-spawn");
		config_loaded = messages.getString("config-loaded");
		use_command_setspawn_permission = messages.getString("use-command-setspawn-permission");
		use_command_openmenu = messages.getString("use-command-openmenu");
		error_no_online = messages.getString("error-no-online");
		error_no_menu = messages.getString("error-no-menu");
		help_command_reload = messages.getString("help-command-reload");
		help_description_reload = messages.getString("help-description-reload");
		help_command_setSpawn = messages.getString("help-command-setSpawn");
		help_description_setSpawn = messages.getString("help-description-setSpawn");
		help_command_remSpawn = messages.getString("help-command-remSpawn");
		help_description_remSpawn = messages.getString("help-description-remSpawn");
		help_command_openmenu = messages.getString("help-command-openmenu");
		help_description_openmenu = messages.getString("help-description-openmenu");
	}
}
