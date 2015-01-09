package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.loader.recipes.MixerRecipes;
import ru.itaros.chemlab.loader.recipes.WoodChainRecipes;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.recipes.RecipesCollection;

public class MixerIO extends HOEMachineCrafterIO {

	public static final int MAXPOWER = 500;
	
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return MixerRecipes.recipes;
	}	
	
	public MixerIO(){
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
