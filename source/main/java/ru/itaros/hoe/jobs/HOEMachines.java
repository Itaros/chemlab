package ru.itaros.hoe.jobs;

import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import ru.itaros.api.hoe.IHOEJob;
import ru.itaros.api.hoe.exceptions.HOENoSuchDataExistsException;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;

public class HOEMachines implements IHOEJob {	
	
	Vector<HOEMachineData> machines = new Vector<HOEMachineData>();
	LinkedList<HOEMachineData> injectorQueue = new LinkedList<HOEMachineData>();
	private volatile boolean tryToInject=false;
	
	//Used in error reporting
	private HOEMachineData currentlyProcessed;
	public HOEMachineData getCurrentlyProccessedData(){
		return currentlyProcessed;
	}
	
	
	@Override
	public void run() {
		manageQueue();
		HOEData skipped=null;
		for(HOEMachineData d : machines){
			if(!d.isRunning()){skipped=d;}
			currentlyProcessed=d;
			if(d.isConfigured()){
				HOEMachineIO io = d.getIO();
				io.tick(d, true);
			}
		}
		if(skipped!=null){
			System.out.println("Skipped machine removed: "+skipped.getClass().getSimpleName()+"@"+skipped.hashCode());
			machines.remove(skipped);
			skipped.notifySkipEvent();
		}
	}

	
	ReentrantLock queueLock = new ReentrantLock();
	private void manageQueue(){
		if(!tryToInject){return;}
		if(queueLock.tryLock()){
			try{
				if(!injectorQueue.isEmpty()){
					HOEMachineData data = injectorQueue.pop();
					machines.add(data);
				}else{
					tryToInject=false;
				}
			}finally{
				queueLock.unlock();
			}
		}else{
			System.out.println("Queue manager skips");
		}
	}
	
	/*
	 * Injects precreated custom data. Usefull for 'special' machines
	 */
	public void injectCustomData(HOEMachineData data){
		queueLock.lock();
		injectorQueue.push(data);
		System.out.println("Injecting machine: "+data.getClass().getSimpleName()+"@"+data.hashCode());
		tryToInject=true;
		queueLock.unlock();
		//machines.add(data);
	}
	/*
	 * Creates and injects basic HOEMachineCrafterData
	 */
	public HOEMachineCrafterData generateMachineCrafterData() {
		HOEMachineCrafterData data = new HOEMachineCrafterData();
		injectCustomData(data);
		return data;
	}

	public void removeMachineData(HOEMachineCrafterData data) throws HOENoSuchDataExistsException {
		if(!machines.remove(data)){
			throw new HOENoSuchDataExistsException();
		}
	}


	public HOEMachineData checkForFingerprint(HOEDataFingerprint fingerprint) {
		HOEMachineData candidate=null;
		queueLock.lock();
		for(HOEMachineData hmda:machines){
			HOEDataFingerprint comparable = hmda.getOwnerFingerprint();
			if(comparable==null){System.out.println("Wrong fingerprint: "+hmda.getClass().getSimpleName());hmda.invalidate();continue;}
			if(comparable.equals(fingerprint)){
				candidate=hmda;
				validateProcessingRegistry(candidate);
				break;
			}
		}
		queueLock.unlock();
		return candidate;
	}

	/*
	 * This method insures this data will not be ejected(via being pardoned)
	 * And returns it back if it is already has been evicted.
	 */
	private void validateProcessingRegistry(HOEMachineData candidate) {
		//Pardoning
		if(!candidate.isRunning()){
			candidate.revalidate();
		}
		//Oh god. It is now evicted?
		if(candidate.isSkipEventNotified()){
			injectCustomData(candidate);
		}
	}

}
