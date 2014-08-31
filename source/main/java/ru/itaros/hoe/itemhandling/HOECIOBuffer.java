package ru.itaros.hoe.itemhandling;

import ru.itaros.hoe.tiles.ioconfig.PortInfo;

//TODO: Delete if no use will be found

/*
 * This class is used in HOEData to transfer items into it from CIO ports 
 * if item is in demand. The main usage is to move heavy operations into HOEContext
 * Like, MixtureStack operations.
 * 
 * Additional feature is the ability to gateway special HOE-aware CIO IPAI(MixtureStack)
 */
public class HOECIOBuffer {
	
	private IUniversalStack candidate;
	
	private int cyclesPassed=0;
	private volatile boolean newCandidateRequired=true;
	private volatile boolean awaitingTakeower=false;
	
	private IUniversalStack transfer;
	
	
	public void tryToPush(PortInfo[] ports){
		if(newCandidateRequired || awaitingTakeower){return;}//Cant transfer if candidate is not elected
		if(cyclesPassed<6){
			PortInfo cur = ports[cyclesPassed];
			cyclesPassed++;
			//Checking if master type is equal
			if(candidate.getProxy().getClass()==cur.getStack().getClass()){
				
			}
		}else{
			cyclesPassed=0;
			newCandidateRequired=true;
		}
	}
}
