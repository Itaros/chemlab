package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.DiaphragmalElectrolyzerData;
import ru.itaros.chemlab.loader.recipes.DiaphragmalElectrolyzerRecipes;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.recipes.RecipesCollection;

public class DiaphragmalElectrolyzerIO extends HOEMachineCrafterIO {

	public static final int MAXPOWER = 10000;
	
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return DiaphragmalElectrolyzerRecipes.recipes;
	}	
	
	public DiaphragmalElectrolyzerIO(){
		this.allowToStart();
	}

	
	@Override
	public void configureData(HOEData data) {
		DiaphragmalElectrolyzerData machine=(DiaphragmalElectrolyzerData) data;
		machine.setMaxPower(MAXPOWER);
		machine.setMachine(this);
		super.configureData(data);
	}
	
	@Override
	public long getMeltdownTemperature() {
		return 600L;
	}

}
