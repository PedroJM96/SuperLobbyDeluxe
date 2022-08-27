package com.pedrojm96.superlobby;

import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.google.common.base.Splitter;
import com.pedrojm96.core.CoreColor;
import com.pedrojm96.core.CoreVariables;

 
public class BoardLine {
	private Team team;
	private List<String> lis;
	private int index = 0;
	private int interval;
	private int intervalfijo;
	private Player player;
	
	public BoardLine(Player p,Team team , List<String> lis,int interval){
		this.team = team;
		this.lis = lis;
		this.interval = interval;
		this.intervalfijo = interval;
		this.player = p;
		String s = lis.get(0);
		
		s = CoreVariables.replace(s, player);
		
		Iterator<String> iterator = Splitter.fixedLength(16).split(s).iterator();
		String iterador = iterator.next();
		String prefix = iterador;
		
		
		//team.setPrefix(iterador);
		if (s.length() > 16){
			String suffix = iterator.next();
			
			
			//p.sendMessage("Sin formato: "+"["+prefix.replaceAll("§", "&")+":"+prefix.length()+"]"+"["+suffix.replaceAll("§", "&")+":"+suffix.length()+"]");
		
			//String prefix = team.getPrefix();
			
			char c1 = prefix.charAt(prefix.length()-1);
			char c2 = suffix.charAt(0);
			boolean ultimo = false;
		    if (((c1 == '&') || (c1 == '§')) && (ChatColor.getByChar(c2) != null))
		    {
		    	
		    	prefix =prefix.substring(0, prefix.length()-1);
		        //team.setPrefix(team.getPrefix().substring(0, prefix.length()-1));
		    	suffix= c1 + suffix;
		    	ultimo = true;
		    	//p.sendMessage("Remover ultimo §: "+"["+prefix.replaceAll("§", "&")+":"+prefix.length()+"]"+"["+suffix.replaceAll("§", "&")+":"+suffix.length()+"]");
		    }

		    String finalcolor = "";
		   
		    boolean formate = false;
		    
		    for(int i = (prefix.length()-1) ; i>=0;i-=2) {
		    	for(String color : CoreColor.getNativeColorList()) {
			    	if(prefix.endsWith(color)) {
			    		finalcolor = color+finalcolor;
			    		formate = true;
			    		prefix =prefix.substring(0, prefix.length()-2);
			    	}
			    }
		    }
		    

		    if(formate) {
		    	
		    	suffix = finalcolor.trim() + suffix;
		    	
		    	if(suffix.length()>16) {
		    		suffix = suffix.substring(0, 15);
		    	}
		    	
		    	//p.sendMessage("Formato limpio: "+"["+prefix.replaceAll("§", "&")+":"+prefix.length()+"]"+"["+suffix.replaceAll("§", "&")+":"+suffix.length()+"]");
		    
		    }else if(!ultimo){
		    	String str1 = "";
			    if(!ChatColor.getLastColors(prefix).equals("")) {
			    	str1 = ChatColor.getLastColors(prefix);
			    }
			     
			    if ((suffix = str1 + suffix).length() > 16) {
			    	suffix = suffix.substring(0, 15);
			    }
			    
			   // p.sendMessage("Ultimo color: "+"["+prefix.replaceAll("§", "&")+":"+prefix.length()+"]"+"["+suffix.replaceAll("§", "&")+":"+suffix.length()+"]");
		    }
		    
		    
		    
		    
			/**String last = "";
			for (int i = 0; i < s.substring(0, 16).length(); i++)
	        {
	            char c = s.charAt(i);
	            if (c == '§') {
	                last = last + "§" + s.charAt(i + 1);
	            }
	         }
	        String sufi = iterator.next();
	        if((last.length() + sufi.length())>16){
	        	sufi = sufi.substring(0, sufi.length()-last.length());
	        }**/
		    team.setPrefix(prefix);
	        team.setSuffix(suffix);
	        
		}else {
			team.setPrefix(prefix);
			team.setSuffix(CoreColor.coloriseTextComponentString("&f"));
		}
		
	}
	
	
	
	
	
	
	public void udate(){
		if(this.interval <= 0){
			net();
			this.interval = this.intervalfijo;
		}
		this.interval--;
	}
	
	
	
	private void net(){
		if(this.index>= this.lis.size()){
			this.index = 0;
		}
		String s = lis.get(this.index);
		s = CoreVariables.replace(s, player);
		Iterator<String> iterator = Splitter.fixedLength(16).split(s).iterator();
		String iterador = iterator.next();
		String prefix = iterador;
		//team.setPrefix(iterador);
		
		
		if (s.length() > 16){
			String suffix = iterator.next();
			
			
			//Bukkit.getPlayer("PedroJM96").sendMessage("Sin formato: "+"["+prefix.replaceAll("§", "&")+":"+prefix.length()+"]"+"["+suffix.replaceAll("§", "&")+":"+suffix.length()+"]");
		
			//String prefix = team.getPrefix();
			
			char c1 = prefix.charAt(prefix.length()-1);
			char c2 = suffix.charAt(0);
			boolean ultimo = false;
		    if (((c1 == '&') || (c1 == '§')) && (ChatColor.getByChar(c2) != null))
		    {
		    	
		    	prefix =prefix.substring(0, prefix.length()-1);
		    	
		        //team.setPrefix(team.getPrefix().substring(0, prefix.length()-1));
		    	suffix= c1 + suffix;
		    	ultimo = true;
		    }
		    
		    
		    //Bukkit.getPlayer("PedroJM96").sendMessage("Remover ultimo §: "+"["+prefix.replaceAll("§", "&")+":"+prefix.length()+"]"+"["+suffix.replaceAll("§", "&")+":"+suffix.length()+"]");
		    
		    String finalcolor = "";
		
		    boolean formate = false;
		    
		    
		    for(int i = (prefix.length()-1) ; i>=0;i-=2) {
		    	for(String color : CoreColor.getNativeColorList()) {
			    	if(prefix.endsWith(color)) {
			    		finalcolor = color+finalcolor;
			    		formate = true;
			    		prefix =prefix.substring(0, prefix.length()-2);
			    	}
			    }
		    }
		    
		    
		    
		    if(formate) {
		    	
		    	
		    	suffix = finalcolor.trim() + suffix;
		    	
		    	if(suffix.length()>16) {
		    		suffix = suffix.substring(0, 15);
		    	}
		    	
		    	//Bukkit.getPlayer("PedroJM96").sendMessage("Formato limpio: "+"["+prefix.replaceAll("§", "&")+":"+prefix.length()+"]"+"["+suffix.replaceAll("§", "&")+":"+suffix.length()+"]");
		    
		    }else if(!ultimo){
		    	String str1 = "";
			    if(!ChatColor.getLastColors(prefix).equals("")) {
			    	str1 = ChatColor.getLastColors(prefix);
			    }
			     
			    if ((suffix = str1 + suffix).length() > 16) {
			    	suffix = suffix.substring(0, 15);
			    }
			   
			    //Bukkit.getPlayer("PedroJM96").sendMessage("Ultimo color: "+"["+prefix.replaceAll("§", "&")+":"+prefix.length()+"]"+"["+suffix.replaceAll("§", "&")+":"+suffix.length()+"]");
		    }
		    
		    
		    
		    
			/**String last = "";
			for (int i = 0; i < s.substring(0, 16).length(); i++)
	        {
	            char c = s.charAt(i);
	            if (c == '§') {
	                last = last + "§" + s.charAt(i + 1);
	            }
	         }
	        String sufi = iterator.next();
	        if((last.length() + sufi.length())>16){
	        	sufi = sufi.substring(0, sufi.length()-last.length());
	        }**/
		    team.setPrefix(prefix);
	        team.setSuffix(suffix);
		}else {
			team.setPrefix(prefix);
			team.setSuffix(CoreColor.coloriseTextComponentString("&f"));
		}
		this.index++;
		
	}
}
