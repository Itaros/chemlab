package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.DiaphragmalElectrolyzerData;
import ru.itaros.chemlab.loader.recipes.DiaphragmalElectrolyzerRecipes;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.recipes.RecipesCollection;

public class DiaphragmalElectrolyzerIO extends HOEMachineCrafterIO {

	public static final int MAXPOWER = 10000;
	public static final int INCOMING_PORTS		=	3;
	public static final int OUTCOMING_PORTS	=	4;
	
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return DiaphragmalElectrolyzerRecipes.recipes;
	}	
	
	public DiaphragmalElectrolyzerIO(){
		this.setReq(INCOMING_PORTS, OUTCOMING_PORTS);
		this.allowToStart();
	}

	
	@Override
	public void configureData(HOEData data) {
		DiaphragmalElectrolyzerData machine=(DiaphragmalElectrolyzerData) data;
		machine.setMaxPower(MAXPOWER);
		machine.setDepots(INCOMING_PORTS, OUTCOMING_PORTS);
		machine.setMachine(this);
		machine.setConfigured();
	}
	
	@Override
	public long getMeltdownTemperature() {
		return 600L;
	}

}
