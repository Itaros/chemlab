package ru.itaros.toolkit.hoe.machines.basic.io.connectivity.internal;

import java.util.ArrayList;

import ru.itaros.toolkit.hoe.machines.basic.io.connectivity.HOEDataMessage;

public class HOEDataPipe {

	volatile boolean isLocked=false;
	
	private ArrayList<HOEDataMessage> inbound 			= new ArrayList<HOEDataMessage>();
	private ArrayList<HOEDataMessage> inbound_shadowed 	= new ArrayList<HOEDataMessage>();
	
	
	//DATA MANIPULATION
	public void put(HOEDataMessage message){
		lock();
		inbound.add(message);
		unlock();
	}
	public HOEDataMessage[] flipAndExtract(){
		lock();
		flip();
		HOEDataMessage[] rslt = extractTrusted();
		unlock();
		return rslt;
	}
	
	private HOEDataMessage[] extractTrusted(){
		HOEDataMessage[] msgs = new HOEDataMessage[inbound_shadowed.size()];
		msgs=inbound_shadowed.toArray(msgs);
		inbound_shadowed.clear();
		return msgs;
	}
	
	
	//THREADING
	
	private synchronized void lock(){
		if(isLocked){
			while(isLocked){
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					//WUT?
					e.printStackTrace();
				}
			}
		}
		isLocked=true;
	}
	private void unlock(){
		isLocked=false;
	}	
	
	private void flip(){
		ArrayList<HOEDataMessage> intermid = inbound;
		inbound=inbound_shadowed;
		inbound_shadowed=intermid;
	}
	
	
	
}
