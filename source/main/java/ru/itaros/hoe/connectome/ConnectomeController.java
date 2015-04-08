package ru.itaros.hoe.connectome;

import java.util.ArrayList;

import ru.itaros.api.hoe.internal.HOEData;

/*
 * This class monitors active connections
 * and drops invalid ones to make sure GC does its job right.
 * 
 * This can be used to exchange data using common HOEData interfaces like:
 * IHeatContainer
 */
public final class ConnectomeController {

	private ArrayList<ConnectionPair> connections = new ArrayList<ConnectionPair>();
	
	private int refreshIndex=0;
	public void refresh(){
		if(connections.size()>0){
			if(refreshIndex>=connections.size()){refreshIndex=0;}
			ConnectionPair p = connections.get(refreshIndex);
			if(p==null){connections.remove(refreshIndex);}else{
				if(!p.checkHealth()){
					p.sendDetachRequest();
					p.erase();
					System.out.println("Dead connection is removed!");
				}
			}
		}
	}
	
	public void tryToConnect(HOEData hostData, HOEData remoteData){
		if(hostData==null || remoteData==null){System.out.println("Connectome glitched out: no data available");}
		
		ConnectionPair pair = new ConnectionPair(hostData,remoteData);
		
		//Now we need to get ourselves to register
		hostData.getConnectome().forceRegister(pair);
		
		//Now we need to call remote to register us to make linkage loop so they can communicate
		remoteData.getConnectome().forceRegister(pair.reversedCopy());
		
		
			
	}

	private void forceRegister(ConnectionPair pair) {
		//Checking if there is similar connection
		for(ConnectionPair pp : connections){
			boolean straight = pp.initiator==pair.initiator && pp.recipient==pair.recipient;
			boolean crossover = pp.initiator==pair.recipient && pp.recipient == pair.initiator;
			if(straight || crossover){
				System.out.println("This connection is already present");
				return;
			}
		}
		connections.add(pair);	
		System.out.println("Connection established");
	}

	public void detach(ConnectionPair connectionPair) {
		connections.remove(connectionPair);
	}

	public HOEData[] getConnected() {
		HOEData[] r = new HOEData[connections.size()];//Should be precached
		for(int i = 0 ; i < connections.size(); i++){
			ConnectionPair cp = connections.get(i);
			r[i] = cp.recipient;
		}
		return r;
	}

	
}
