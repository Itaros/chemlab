package ru.itaros.chemlab.hoe;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.loader.recipes.AirCollectorRecipes;
import ru.itaros.chemlab.loader.recipes.WoodChainRecipes;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class AirCollectorIO extends HOEMachineIO {

	public static final int MAXPOWER = 1000;
	public static final int INCOMING_PORTS		=	1;
	public static final int OUTCOMING_PORTS	=	1;
	
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return AirCollectorRecipes.recipes;
	}	
	
	public AirCollectorIO(){
		this.setReq(INCOMING_PORTS, OUTCOMING_PORTS);
		this.allowToStart();
	}
	@Override
	protected void produce(HOEData data) {
		HOEMachineData hm = (HOEMachineData) data;
		if(hm.decrementResources()){
			hm.incrementProduction();
		}
	}
	
	@Override
	public void configureData(HOEData data) {
		HOEMachineData machine=(HOEMachineData) data;
		machine.setMaxPower(MAXPOWER);
		machine.setDepots(INCOMING_PORTS, OUTCOMING_PORTS);
		machine.setMachine(this);
		machine.setConfigured();
	}

}
