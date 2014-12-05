package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.ServiceBayData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.io.HOEMachineIO;

public class ServiceBayIO extends HOEMachineIO {

	public ServiceBayIO(){
		super();
		this.allowToStart();
	}
	
	@Override
	public void configureData(HOEData data) {
		HOEMachineData machinedata = (HOEMachineData)data;
		
		machinedata.setMaxPower(1000);
		machinedata.ticksRequared=20;
		machinedata.setMachine(this);
		machinedata.setConfigured();
	}

	
	@Override
	protected void produce(HOEData data, boolean doReal) {
		
		
	}

	@Override
	protected boolean isMachineActive(HOEData data) {
		if(data instanceof ServiceBayData){
			ServiceBayData sbd = (ServiceBayData)data;
			//return sbd.hasSupplies();
			return true;
		}else{
			return false;
		}
	}





	

	
	
}
