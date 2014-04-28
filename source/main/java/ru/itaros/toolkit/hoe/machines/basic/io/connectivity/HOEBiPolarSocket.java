package ru.itaros.toolkit.hoe.machines.basic.io.connectivity;

import java.util.ArrayList;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.toolkit.hoe.machines.basic.io.connectivity.internal.HOEDataPipe;

public class HOEBiPolarSocket {

	public static final int SLIPSTEP=20;
	
	private int timer;
	
	private HOEDataPipe inbound = new HOEDataPipe();
	
	private ArrayList<HOEPeerToPeerConnector> outbound = new ArrayList<HOEPeerToPeerConnector>();
	
	
	public void operate(HOEData me){
		timer++;
		if(timer>SLIPSTEP){
			timer=0;
			executeInbounds(me);
			executeOutbounds();
		}
	}
	
	private void executeInbounds(HOEData me){
		for(HOEDataMessage m:inbound.flipAndExtract()){
			m.run(me);
		}
	}
	private void executeOutbounds(){
		for(HOEPeerToPeerConnector connector:outbound){
			connector.send();
		}
	}
	public void scheduleBroadcast(HOEDataMessage message){
		for(HOEPeerToPeerConnector connector:outbound){
			connector.put(message);
		}
	}
	public HOEDataPipe getReceiver(){
		return inbound;
	}
	
	
	
	public void dropOutbound(){
		outbound.clear();
	}
	public void insertOutbound(HOEPeerToPeerConnector...connectors){
		for(HOEPeerToPeerConnector c:connectors){
			outbound.add(c);
		}
	}
	
}
