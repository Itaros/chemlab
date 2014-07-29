package ru.itaros.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.recipes.Recipe;
import ru.itaros.hoe.recipes.RecipesCollection;


public abstract class HOEMachineCrafterIO extends HOEMachineIO{

	
	public abstract RecipesCollection getRecipesCollection();

	@Override
	public abstract void configureData(HOEData data);


	@Override
	protected void produce(HOEData data, boolean doReal){
		HOEMachineCrafterData hm = (HOEMachineCrafterData) data;
		//hm.shutCycleOff();//Cycled thought
		hm.produce(doReal);
	}	
	
	protected boolean isMachineActive(HOEData data){
		//if(data==null){return false;}//SOMETHING REALLY WRONG
		HOEMachineCrafterData machine = (HOEMachineCrafterData)data;
		if(!machine.hasWork()){return false;}
			
		//if(!machine.isRecipeSet){return false;}
		//if(!machine.checkStorage()){return false;}
		
		//power
		if(machine.useEnergy()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void claimOwnership() {
		super.claimOwnership();
		
		for(Recipe r:getRecipesCollection().getRecipes()){
			r.claim(this);
		}
	}
	
	
	
	
}
