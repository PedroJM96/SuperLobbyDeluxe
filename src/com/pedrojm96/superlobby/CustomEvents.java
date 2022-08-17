package com.pedrojm96.superlobby;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.pedrojm96.core.CoreVariables;
import com.pedrojm96.core.command.CoreExecuteComands;







public class CustomEvents {
	private String permission;
	private String join_message;
	private String quit_message;
	private String join_gamemode;
	private List<String> join_comands;
	private String join_firework;
	private String join_sound;
	
	private boolean join_sound_global = false;
	
	private JavaPlugin plugin;
	private int num;
	
	public CustomEvents(String permission,JavaPlugin plugin){
		this.permission = permission;
		this.plugin = plugin;
	}
	
	public String getPermission(){
		return permission;
	}
	
	public boolean hasJoinMessage(){
		return (join_message!=null);
	}
	public boolean hasQuitMessage(){
		return (quit_message!=null);
	}
	public boolean hasJoinFirework(){
		return (join_firework!=null);
	}
	public boolean hasJoinSound(){
		return (join_sound!=null);
	}
	public boolean hasJoinGamemode(){
		return (join_gamemode!=null);
	}
	public boolean hasJoinComands(){
		return (join_comands!=null);
	}
	
	public void setJoinMessage(String join_message){
		this.join_message = join_message;
	}
	
	public void setQuitMessage(String quit_message){
		this.quit_message = quit_message;
	}
	public String getJoinMessage(){
		return this.join_message;
	}
	public String getQuitMessage(){
		return this.quit_message;
	}
	public void sendJoinFirework(final Player p){
		for(int i=0;i<num;i++){
			int m = i*10;
			this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
				  public void run() {
					  fireword(p);
				  }
				}, m);
		}
	}
	
	public void sendJoinSound(Player p){
		if(join_sound.equalsIgnoreCase("random")){
		    Random r = new Random();
		    
		   
			Sound[] so = Sound.values();
			int v = so.length;
			int rt = r.nextInt(v) + 1;
			
			if(this.join_sound_global) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.playSound(player.getLocation(), so[rt], 1.0F, 1.0F);
				}
			}else {
				p.getWorld().playSound(p.getLocation(), so[rt], 1.0F, 1.0F);
			}
			
		}else{
			if(this.join_sound_global) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.playSound(player.getLocation(), Sound.valueOf(join_sound.toUpperCase()), 1.0F, 1.0F);
				}
			}else {
				p.getWorld().playSound(p.getLocation(), Sound.valueOf(join_sound.toUpperCase()), 1.0F, 1.0F);
			}
		}
		 
	}
	
	public void sendJoinGamemode(Player p){
		p.setGameMode(GameMode.valueOf(join_gamemode.toUpperCase()));
	}
	
	public void sendJoinComands(Player p){
		for(String comando : this.join_comands) {
			CoreExecuteComands c = new CoreExecuteComands(p,CoreVariables.replace(comando, p),plugin,AllString.prefix);
			c.execute();
		}
	}
	
	
	
	public void fireword(Player p){
		//Spawn the Firework
        Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        //random
        Random r = new Random();
        Type type = Type.BALL;
        if(join_firework.equalsIgnoreCase("random")){
        	//sets type
            int rt = r.nextInt(5) + 1;
            if (rt == 1) type = Type.BALL;
            if (rt == 2) type = Type.BALL_LARGE;
            if (rt == 3) type = Type.BURST;
            if (rt == 4) type = Type.CREEPER;
            if (rt == 5) type = Type.STAR;
        }else{
        	type = Type.valueOf(join_firework.toUpperCase());
        }
        
        //colors
        //To be Added
        int a = r.nextInt(256);
        int b = r.nextInt(256);
        int g = r.nextInt(256);
        Color c1 = Color.fromRGB(a, g, b);

        a = r.nextInt(256);
        b = r.nextInt(256);
        g = r.nextInt(256);
        Color c2 = Color.fromRGB(a, g, b);


        //effect
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

        //applied effects
        fwm.addEffect(effect);

        //random power! moar sulphur!
        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);

        //aaaaaand set it
        fw.setFireworkMeta(fwm);
        
	}
	
	public void setJoinFirework(String firework){
		if(firework.trim().contains(",")){
			String[] datos = firework.trim().split(",");
			this.join_firework = datos[0].trim();
			this.num = Integer.valueOf(datos[1].trim());
		}else{
			this.join_firework = firework;
			this.num = 2;
		}
	}
	public void setJoinSound(String sound){
		this.join_sound = sound;
	}
	public void setJoinGamemode(String gamemode){
		this.join_gamemode = gamemode;
	}
	public void setJoinComands(List<String> comands){
		this.join_comands = comands;
	}
	
	public void setJoinSoundGlobal(boolean global){
		this.join_sound_global = global;
	}
	
}
