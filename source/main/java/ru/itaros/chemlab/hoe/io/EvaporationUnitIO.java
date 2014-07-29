package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.loader.recipes.EvaporationUnitRecipes;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.recipes.RecipesCollection;

public class EvaporationUnitIO extends HOEMachineCrafterIO {

	public static final int MAXPOWER = 100;
	public static final int INCOMING_PORTS		=	2;
	public static final int OUTCOMING_PORTS	=	3;
	
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return EvaporationUnitRecipes.recipes;
	}	
	
	public EvaporationUnitIO(){
		this.setReq(INCOMING_PORTS, OUTCOMING_PORTS);
		this.allowToStart();
	}

	
	@Override
	public void configureData(HOEData data) {
		HOEMachineCrafterData machine=(HOEMachineCrafterData) data;
		machine.setMaxPower(MAXPOWER);
		machine.setDepots(INCOMING_PORTS, OUTCOMING_PORTS);
		machine.setMachine(this);
		machine.setConfigured();
	}


}
