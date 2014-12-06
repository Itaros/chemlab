package ru.itaros.hoe.io;

import net.minecraft.block.Block;
import ru.itaros.api.hoe.heat.IHeatContainer;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.hoe.data.machines.HOEMachineData;

public abstract class HOEMachineIO extends HOEIO {

	public HOEMachineIO() {
		super();
	}

	public abstract void configureData(HOEData data);
	protected abstract boolean isMachineActive(HOEData data);
	
	
	@Override
	public void tick(HOEData data, boolean doReal) {
		super.tick(data, doReal);
		HOEMachineData machine = (HOEMachineData) data;
		if(!machine.isConfigured()){return;}
		
		if(machine instanceof IHeatContainer){
			((IHeatContainer)machine).updateDistribution();
		}
		
		if(!isMachineActive(data)){return;}
		machine.incrementTick();
		if(machine.getTicks()>machine.ticksRequared){
			machine.voidTicks();
			produce(data, doReal);
		}
	}


	private Block hostBlock;
	public HOEMachineIO setHostBlock(Block block){
		hostBlock=block;
		return this;
	}
	public Block getHostBlock(){
		return hostBlock;
	}
	
	
	protected abstract void produce(HOEData data, boolean doReal);

}