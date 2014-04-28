package ru.itaros.toolkit.hoe.machines.basic;

import java.util.Vector;

import ru.itaros.api.hoe.IHOEJob;
import ru.itaros.api.hoe.exceptions.HOENoSuchDataExistsException;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineCrafterIO;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;

public class HOELow implements IHOEJob {

	//Who uses datalink anyway? If you need it check HOEMachines implementation
	//private HOEMachinesDataLink datalink=new HOEMachinesDataLink(this);
	
	Vector<HOEMachineCrafterData> machines = new Vector<HOEMachineCrafterData>();

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
	public void injectCustomData(HOEMachineCrafterData data){
		machines.add(data);
	}
	/*
	 * Creates and injects basic HOEMachineData
	 */
	public HOEData generateMachineData() {
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
