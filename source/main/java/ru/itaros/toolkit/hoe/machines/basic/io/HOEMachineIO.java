package ru.itaros.toolkit.hoe.machines.basic.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;

public abstract class HOEMachineIO extends HOEIO {

	public HOEMachineIO() {
		super();
	}

	public abstract void configureData(HOEData data);
	protected abstract boolean isMachineActive(HOEData data);
	
	
	@Override
	public void tick(HOEData data) {
		super.tick(data);
		HOEMachineData machine = (HOEMachineData) data;
		if(!isMachineActive(data)){return;}
		machine.incrementTick();
		if(machine.getTicks()>machine.ticksRequared){
			machine.voidTicks();
			produce(data);
		}
	}



	protected abstract void produce(HOEData data);

}