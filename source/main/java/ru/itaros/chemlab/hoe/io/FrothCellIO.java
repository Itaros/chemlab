package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.recipes.RecipesCollection;

public class FrothCellIO extends HOEMachineCrafterIO {


	public static final int MAXPOWER = 1000;
	public static final int INCOMING_PORTS		=	1;
	public static final int OUTCOMING_PORTS	=	3;
	
	
	private static RecipesCollection recipes;
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return recipes;
	}	
	
	public FrothCellIO(){
		recipes = new RecipesCollection();
		recipes.register();
		
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
	
	@Override
	public long getMeltdownTemperature() {
		return 600L;
	}

}
