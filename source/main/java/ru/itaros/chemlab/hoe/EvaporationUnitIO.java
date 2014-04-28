package ru.itaros.chemlab.hoe;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.DiaphragmalElectrolyzerData;
import ru.itaros.chemlab.loader.recipes.EvaporationUnitRecipes;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineCrafterData;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineCrafterIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class EvaporationUnitIO extends HOEMachineCrafterIO {

	public static final int MAXPOWER = 1000;
	public static final int INCOMING_PORTS		=	1;
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
	protected void produce(HOEData data) {
		HOEMachineCrafterData hm = (HOEMachineCrafterData) data;
		if(hm.decrementResources()){
			hm.incrementProduction();
		}
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
