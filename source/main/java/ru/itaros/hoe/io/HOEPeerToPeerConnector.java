package ru.itaros.hoe.io;

import java.util.ArrayList;

/*
 * This class connects 
 */
public class HOEPeerToPeerConnector {

	private HOEBiPolarSocket source, target;
	
	private ArrayList<HOEDataMessage> buffer = new ArrayList<HOEDataMessage>();
	
	public HOEPeerToPeerConnector(HOEBiPolarSocket source, HOEBiPolarSocket target){
		this.source=source;
		this.target=target;
	}
	
	public void put(HOEDataMessage message){
		buffer.add(message);
	}

	public void send() {
		for(HOEDataMessage message:buffer){
			target.getReceiver().put(message);
		}
		buffer.clear();
	}
	
	
}
