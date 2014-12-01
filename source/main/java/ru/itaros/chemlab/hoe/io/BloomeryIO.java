package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.recipes.RecipesCollection;

public class BloomeryIO extends HOEMachineCrafterIO {


	public static final int MAXPOWER = ChemLabValues.ENERGY_FRACTION*100*2;
	public static final int INCOMING_PORTS		=	2;
	public static final int OUTCOMING_PORTS	=	1;
	
	
	private static RecipesCollection recipes=new RecipesCollection();
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return recipes;
	}	
	
	public BloomeryIO(){
		this.setReq(INCOMING_PORTS, OUTCOMING_PORTS);
		this.allowToStart();
		recipes.register();
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
