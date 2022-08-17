package com.pedrojm96.superlobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

public class TapComplete {
	
	
	public List<String> blocktab = new ArrayList<String>();
	public SuperLobby superlobby;
	
	public TapComplete(SuperLobby plugin) {
		protocolManager = ProtocolLibrary.getProtocolManager();
		this.superlobby = plugin;
	}
	
	 
	private ProtocolManager protocolManager = null;
	
	

	public void onTabCcompletePre1_13() {
		
		
	
		protocolManager.addPacketListener(
				new PacketAdapter(superlobby, ListenerPriority.NORMAL,PacketType.Play.Server.TAB_COMPLETE) {
					
					@Override
					@EventHandler(priority = EventPriority.HIGHEST)
				    public void onPacketSending(PacketEvent event) {
				        // Item packets (id: 0x29)
							superlobby.log.debug(event.getPacket().toString());
				            
				        	if(blocktab.contains(event.getPlayer().getName()) &&  !event.getPlayer().hasPermission(superlobby.config.getString("disable-tab-complete.bypass"))) {
				        		superlobby.log.debug(event.getPacket().getClass().toString());
					        	PacketContainer packet = event.getPacket();
					        	String[] oldmessage = packet.getStringArrays().read(0);
					        	List<String> filter = new ArrayList<String>();			        	
					        	for(String m : oldmessage) {
					        		if(containListTab(m)) {
					        			filter.add(m);
					        			
					        		}
					        	}
					        	
					        	String[] newmessge = new String[filter.size()];
					        	superlobby.log.debug("size: "+filter.size());
					        	int coun = 0;
					        	for(String m : filter) {
					        		newmessge[coun] = m;
					        		superlobby.log.debug("lista("+coun+"): "+m);
					        		coun = coun + 1;
					        	}
					        	packet.getStringArrays().write(0, newmessge);
					        	blocktab.remove(event.getPlayer().getName());
				        	}
	
				        }
				    
		});
		
		protocolManager.addPacketListener(
				new PacketAdapter(superlobby, ListenerPriority.NORMAL,PacketType.Play.Client.TAB_COMPLETE) {
					
					@Override
					@EventHandler(priority = EventPriority.HIGHEST)
				    public void onPacketReceiving(PacketEvent event) {
				        // Item packets (id: 0x29)
				      
				            
							superlobby.log.debug(event.getPacket().toString());
				        	
				        	PacketContainer packet = event.getPacket();
				        	
				        	String mensaje = packet.getStrings().read(0);
				        	
				        	
				        	if(mensaje.contains(" ")) {
				        		String[] mensajes = mensaje.split(" ");
				        		if(!containListTab(mensajes[0])) {
				        			blocktab.add(event.getPlayer().getName());
				        			superlobby.log.debug("bloqueo");
				        		}
				        	}else {
				        		blocktab.add(event.getPlayer().getName());
				        		superlobby.log.debug("bloqueo");
				        	}
				        
				    }
		});
	}
	
	public boolean containListTab(String tab) {
		List<String> listablanca = superlobby.config.getStringList("disable-tab-complete.whitelist");
		
		for(String m : listablanca) {
			if(m.equalsIgnoreCase(tab)) {
				return true;
			}
		}
		return false;
		
	}
}
