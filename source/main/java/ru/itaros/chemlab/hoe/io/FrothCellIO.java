package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.recipes.RecipesCollection;

public class FrothCellIO extends HOEMachineCrafterIO {


	public static final int MAXPOWER = 1000;
	
	
	private static RecipesCollection recipes;
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return recipes;
	}	
	
	public FrothCellIO(){
		recipes = new RecipesCollection();
		recipes.register();
		
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
