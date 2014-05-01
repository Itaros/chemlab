package ru.itaros.chemlab.hoe;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.DiaphragmalElectrolyzerData;
import ru.itaros.chemlab.loader.recipes.DiaphragmalElectrolyzerRecipes;
import ru.itaros.chemlab.loader.recipes.WoodChainRecipes;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineCrafterData;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineCrafterIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

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

}
