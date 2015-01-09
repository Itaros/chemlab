package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.loader.recipes.MixerRecipes;
import ru.itaros.chemlab.loader.recipes.QuenchingChamberRecipes;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.recipes.RecipesCollection;

public class QuenchingChamberIO extends HOEMachineCrafterIO {

	public static final int MAXPOWER = 250;
	
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return QuenchingChamberRecipes.recipes;
	}	
	
	public QuenchingChamberIO(){
		this.allowToStart();
	}

	
	@Override
	public void configureData(HOEData data) {
		HOEMachineCrafterData machine=(HOEMachineCrafterData) data;
		machine.setMaxPower(MAXPOWER);
		machine.setMachine(this);
		super.configureData(data);
	}
	
	@Override
	public long getMeltdownTemperature() {
		return 600L;
	}

}
