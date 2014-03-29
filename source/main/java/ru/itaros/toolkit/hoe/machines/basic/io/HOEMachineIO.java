package ru.itaros.toolkit.hoe.machines.basic.io;

import net.minecraft.item.Item;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.Recipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;


public abstract class HOEMachineIO extends HOEIO{

	public abstract RecipesCollection getRecipesCollection();

	@Override
	public void tick(HOEData data) {
		HOEMachineData machine = (HOEMachineData) data;
		if(!machine.isRecipeSet){return;}
		if(!machine.checkStorage()){return;}
		machine.incrementTick();
		if(machine.getTicks()>machine.ticksRequared){
			machine.voidTicks();
			produce(data);
		}
	}
	
	protected abstract void produce(HOEData data);

	public abstract void configureData(HOEData data);

	public void setRecipe(HOEMachineData server, Item[] items) {
		if(server==null){return;}//No way on CLIENT
		RecipesCollection collection = getRecipesCollection();
		Recipe rep = collection.findIncomingPattern(items);
		server.setRecipe(rep);
	}
}
