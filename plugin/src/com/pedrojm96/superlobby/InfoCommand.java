package com.pedrojm96.superlobby;

import java.util.List;

public class InfoCommand {
	private String command;
	private List<String> info;
	private String permission;
	
	
	public InfoCommand(String command){
		this.command = command;
	}
	public String getCommand(){
		return this.command;
	}
	
	public void setCommand(String command){
		this.command = command;
	}
	
	public List<String> getInfo(){
		return this.info;
	}
	public void setInfo(List<String> info){
		this.info = info;
	}
	
	
	public String getPermission(){
		return this.permission;
	}
	
	public void setPermission(String permission){
		this.permission = permission;
	}
}
