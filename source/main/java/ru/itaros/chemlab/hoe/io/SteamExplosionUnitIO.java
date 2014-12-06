package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.loader.recipes.WoodChainRecipes;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.recipes.RecipesCollection;

public class SteamExplosionUnitIO extends HOEMachineCrafterIO {

	public static final int MAXPOWER = 1000;
	public static final int INCOMING_PORTS		=	2;
	public static final int OUTCOMING_PORTS	=	2;
	
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return WoodChainRecipes.steamexplosionunitRecipes;
	}	
	
	public SteamExplosionUnitIO(){
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
