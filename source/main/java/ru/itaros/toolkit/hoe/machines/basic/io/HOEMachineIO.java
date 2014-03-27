package ru.itaros.toolkit.hoe.machines.basic.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.api.hoe.internal.HOEIO;


public abstract class HOEMachineIO extends HOEIO{

	private int ticksRequared;
	
	private boolean isRecipeSet=false;
	protected void setRecipeFlag(boolean flag){
		isRecipeSet=flag;
	}	
	@Override
	public void tick(HOEData data) {
		if(!isRecipeSet){return;}
		incrementTick();
		if(getTicks()>ticksRequared){
			voidTicks();
			produce(data);
		}
	}
	
	protected abstract void produce(HOEData data);

	
}
