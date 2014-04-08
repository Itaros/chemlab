package ru.itaros.chemlab.hoe;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.DiaphragmalElectrolyzerData;
import ru.itaros.chemlab.loader.recipes.DiaphragmalElectrolyzerRecipes;
import ru.itaros.chemlab.loader.recipes.WoodChainRecipes;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class DiaphragmalElectrolyzerIO extends HOEMachineIO {

	public static final int MAXPOWER = 1000;
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
	protected void produce(HOEData data) {
		DiaphragmalElectrolyzerData hm = (DiaphragmalElectrolyzerData) data;
		if(hm.decrementResources()){
			hm.incrementProduction();
		}
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
