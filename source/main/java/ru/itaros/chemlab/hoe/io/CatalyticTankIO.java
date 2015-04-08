package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.CatalyticTankData;
import ru.itaros.chemlab.loader.recipes.CatalyticTankRecipes;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.recipes.RecipesCollection;

public class CatalyticTankIO extends HOEMachineCrafterIO {

	public static final int MAXPOWER = 100;
	
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return CatalyticTankRecipes.recipes;
	}	
	
	public CatalyticTankIO(){
		this.allowToStart();
	}

	
	@Override
	public void configureData(HOEData data) {
		CatalyticTankData machine=(CatalyticTankData) data;
		machine.setMaxPower(MAXPOWER);
		machine.setMachine(this);
		super.configureData(data);
	}

	@Override
	public long getMeltdownTemperature() {
		return 600L;
	}
	
	

}
