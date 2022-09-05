package com.pedrojm96.superlobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.pedrojm96.core.CoreVariables;






public class PlayerBoard {
	private Scoreboard board;
	private Objective score;
	private List<BoardLine> lines ;
	private List<String> anititle;
	private int indexanititle = 0;
	private int titleinterval;
	private int titleintervalfijo;
	private Player player;
	
	
	private int linePosition = 15;
	
   private List<String> listaColores = new ArrayList<String>();
	
	@SuppressWarnings("deprecation")
	public PlayerBoard(Player player,List<String>  anititle,int titleinterval){
		this.anititle = anititle;
		this.board = Bukkit.getScoreboardManager().getNewScoreboard();
		this.lines = new ArrayList<BoardLine>();
		this.score = this.board.registerNewObjective("main", "dummy");
		this.score.setDisplayName(CoreVariables.replace(anititle.get(0),player));
		this.score.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.titleinterval = titleinterval;
		this.titleintervalfijo = titleinterval;
		this.player = player;
		
		for (ChatColor color : ChatColor.values()) {
			listaColores.add(color.toString() + ChatColor.RESET.toString()); 
		}
	}
	
	public Scoreboard get(){
		return this.board;
	}
	
	
	public void setupLines(ConfigurationSection config) {
		int precolor=0;
		
		 for(String nodo : config.getKeys(false)){
			 Team team = this.get().registerNewTeam("slt-" + linePosition);
			 team.addEntry(listaColores.get(precolor));
			 this.getObjective().getScore(listaColores.get(precolor)).setScore(linePosition); 
			 BoardLine line = new BoardLine(player,team,config.getStringList(nodo+".content"),config.getInt(nodo+".update"));
			 this.add(line);
			 linePosition--;
			 precolor++;
		 }
	}
	
	public Objective getObjective(){
		return this.score;
	}
	
	public void udate(){
		
		for(BoardLine line : this.lines){
			line.udate();
		}
		if(titleinterval <= 0){
			udateTitle();
			titleinterval = this.titleintervalfijo;
		}
		titleinterval--;
	}
	
	
	public void udateTitle(){
		if(indexanititle >= anititle.size()){
			indexanititle = 0;
		}
		this.score.setDisplayName(CoreVariables.replace(anititle.get(indexanititle),player));
		indexanititle++;
	}
	
	private void add(BoardLine param){
		this.lines.add(param);
	}
	
	 public void send(){
		this.player.setScoreboard(this.board);
	}

	
}
