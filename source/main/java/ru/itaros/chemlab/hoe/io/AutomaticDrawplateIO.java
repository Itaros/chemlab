package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.loader.recipes.AutomaticDrawplateRecipes;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.recipes.RecipesCollection;

public class AutomaticDrawplateIO extends HOEMachineCrafterIO {

	public static final int MAXPOWER = 2000;
	public static final int INCOMING_PORTS		=	1;
	public static final int OUTCOMING_PORTS	=	2;
	
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return AutomaticDrawplateRecipes.recipes;
	}	
	
	public AutomaticDrawplateIO(){
		this.setReq(INCOMING_PORTS, OUTCOMING_PORTS);
		this.allowToStart();
	}

	
	@Override
	public void configureData(HOEData data) {
		HOEMachineCrafterData machine=(HOEMachineCrafterData) data;
		
		machine.setIgnoreInboundMetadata();
		
		machine.setMaxPower(MAXPOWER);
		machine.setDepots(INCOMING_PORTS, OUTCOMING_PORTS);
		machine.setMachine(this);
		machine.setConfigured();
	}

}
