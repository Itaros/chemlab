package ru.itaros.toolkit.hoe.machines.basic;

import java.util.Vector;

import ru.itaros.api.hoe.IHOEJob;
import ru.itaros.api.hoe.exceptions.HOENoSuchDataExistsException;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;

public class HOEMachines implements IHOEJob {
	
	private HOEMachinesDataLink datalink=new HOEMachinesDataLink(this);
	
	Vector<HOEMachineData> machines = new Vector<HOEMachineData>();
	
	@Override
	public void run() {
		for(HOEMachineData d : machines){
			if(d.isConfigured()){
				HOEMachineIO io = d.getIO();
				io.tick(d);
			}
		}
	}

	public HOEMachineData generateMachineData() {
		HOEMachineData data = new HOEMachineData();
		machines.add(data);
		return data;
	}

	public void removeMachineData(HOEMachineData data) throws HOENoSuchDataExistsException {
		if(!machines.remove(data)){
			throw new HOENoSuchDataExistsException();
		}
	}

}
