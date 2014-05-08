package ru.itaros.toolkit.hoe.machines.basic;

import java.util.Vector;

import ru.itaros.api.hoe.IHOEJob;
import ru.itaros.api.hoe.exceptions.HOENoSuchDataExistsException;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;

public class HOEMachines implements IHOEJob {
	
	private HOEMachinesDataLink datalink=new HOEMachinesDataLink(this);
	
	Vector<HOEMachineData> machines = new Vector<HOEMachineData>();
	
	//Used in error reporting
	private HOEMachineData currentlyProcessed;
	public HOEMachineData getCurrentlyProccessedData(){
		return currentlyProcessed;
	}
	
	
	@Override
	public void run() {
		for(HOEMachineData d : machines){
			currentlyProcessed=d;
			if(d.isConfigured()){
				HOEMachineIO io = d.getIO();
				io.tick(d);
			}
		}
	}

	/*
	 * Injects precreated custom data. Usefull for 'special' machines
	 */
	public void injectCustomData(HOEMachineData data){
		machines.add(data);
	}
	/*
	 * Creates and injects basic HOEMachineCrafterData
	 */
	public HOEMachineCrafterData generateMachineCrafterData() {
		HOEMachineCrafterData data = new HOEMachineCrafterData();
		machines.add(data);
		return data;
	}

	public void removeMachineData(HOEMachineCrafterData data) throws HOENoSuchDataExistsException {
		if(!machines.remove(data)){
			throw new HOENoSuchDataExistsException();
		}
	}

}
