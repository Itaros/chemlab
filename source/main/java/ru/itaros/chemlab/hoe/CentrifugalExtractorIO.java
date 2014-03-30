package ru.itaros.chemlab.hoe;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.loader.recipes.DebugRecipes;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class CentrifugalExtractorIO extends HOEMachineIO {
	public static final int MAXPOWER = 1000;
	public static final int INCOMING_PORTS		=	1;
	public static final int OUTCOMING_PORTS	=	1;
	
	
	@Override
	public RecipesCollection getRecipesCollection() {
		return DebugRecipes.biogrinderRecipes;
	}	
	
	public CentrifugalExtractorIO(){
		this.setReq(INCOMING_PORTS, OUTCOMING_PORTS);
		this.allowToStart();
	}
	@Override
	protected void produce(HOEData data) {
		//FMLLog.log(Level.INFO,"PRODUCTION!");
		HOEMachineData hm = (HOEMachineData) data;
		if(hm.decrementResources()){
			hm.incrementProduction();
		}
	}
	
	@Override
	public void configureData(HOEData data) {
		HOEMachineData machine=(HOEMachineData) data;
		machine.setMaxPower(MAXPOWER);
		machine.setDepots(INCOMING_PORTS, OUTCOMING_PORTS);
		machine.setMachine(this);
		machine.setConfigured();
	}

}
