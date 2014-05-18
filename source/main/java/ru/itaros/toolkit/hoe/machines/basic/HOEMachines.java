package ru.itaros.toolkit.hoe.machines.basic;

import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import ru.itaros.api.hoe.IHOEJob;
import ru.itaros.api.hoe.exceptions.HOENoSuchDataExistsException;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;

public class HOEMachines implements IHOEJob {
	
	private HOEMachinesDataLink datalink=new HOEMachinesDataLink(this);
	
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
			System.out.println("Skipped machine removed: "+skipped.getClass().getSimpleName());
			machines.remove(skipped);
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

}
