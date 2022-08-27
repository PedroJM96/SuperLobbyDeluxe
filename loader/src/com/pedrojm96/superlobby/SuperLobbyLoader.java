package com.pedrojm96.superlobby;

import org.bukkit.plugin.java.JavaPlugin;

import com.pedrojm96.core.CoreLog;
import com.pedrojm96.core.CorePlugin;
import com.pedrojm96.core.libraryloader.CoreClassLoader;
import com.pedrojm96.core.libraryloader.CoreLoader;



public class SuperLobbyLoader extends JavaPlugin implements CorePlugin{


    private final CoreLoader plugin;
    public CoreLog log;
    
    public SuperLobbyLoader() {
    	this.log = new CoreLog(this,CoreLog.Color.YELLOW);
    	CoreClassLoader loader = new CoreClassLoader(getClass().getClassLoader(), "superlobby-plugin.jarinjar" ,this.log);
		this.plugin = loader.instantiatePlugin("com.pedrojm96.superlobby.SuperLobby", CorePlugin.class, this);
		if(this.plugin==null) {
    		this.getServer().getPluginManager().disablePlugin(this);
    	}
    }
  
    @Override
    public void onLoad() {
        this.plugin.onLoad();
    }

    @Override
    public void onEnable() {
        this.plugin.onEnable();
    }

    @Override
    public void onDisable() {
        this.plugin.onDisable();
    }

	@Override
	public CoreLog getLog() {
		// TODO Auto-generated method stub
		return this.log;
	}

	@Override
	public JavaPlugin getInstance() {
		// TODO Auto-generated method stub
		return this;
	}
}
