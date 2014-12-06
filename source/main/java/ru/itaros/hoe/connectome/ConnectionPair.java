package ru.itaros.hoe.connectome;

import ru.itaros.api.hoe.internal.HOEData;

public final class ConnectionPair{
	HOEData initiator;
	HOEData recipient;
	
	public ConnectionPair(HOEData hostData, HOEData remoteData) {
		initiator=hostData;
		recipient=remoteData;
	}
	public HOEData getInitiator(){
		return initiator;
	}
	public HOEData getRecipient(){
		return recipient;
	}
	
	/*
	 * This method returns false if pair is no longer alive
	 */
	public boolean checkHealth(){
		if(initiator==null || !initiator.isRunning()){
			return false;
		}
		if(recipient==null || !recipient.isRunning()){
			return false;
		}
		return true;
	}
	/*
	 * Breaks all references
	 */
	public void erase() {
		initiator=null;
		recipient=null;
	}
	public void sendDetachRequest() {
		if(initiator!=null){
			initiator.getConnectome().detach(this);
		}
		if(recipient!=null){
			recipient.getConnectome().detach(this);
		}		
	}
	public ConnectionPair reversedCopy() {
		ConnectionPair rev = new ConnectionPair(recipient,initiator);
		return rev;
	}
}
