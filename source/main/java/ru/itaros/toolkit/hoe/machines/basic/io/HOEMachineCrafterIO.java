package ru.itaros.toolkit.hoe.machines.basic.io;

import net.minecraft.item.Item;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineCrafterData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.Recipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;


public abstract class HOEMachineCrafterIO extends HOEMachineIO{

	
	public abstract RecipesCollection getRecipesCollection();

	@Override
	public abstract void configureData(HOEData data);


	@Override
	protected void produce(HOEData data) {
		HOEMachineCrafterData hm = (HOEMachineCrafterData) data;
		if(hm.decrementResources()){
			hm.incrementProduction();
		}
		
	}	
	
	protected boolean isMachineActive(HOEData data){
		//if(data==null){return false;}//SOMETHING REALLY WRONG
		HOEMachineCrafterData machine = (HOEMachineCrafterData)data;
		if(!machine.isReadyForCycle()){return false;}
			
		//if(!machine.isRecipeSet){return false;}
		//if(!machine.checkStorage()){return false;}
		
		//power
		if(machine.useEnergy()){
			return true;
		}else{
			return false;
		}
	}
	
}
