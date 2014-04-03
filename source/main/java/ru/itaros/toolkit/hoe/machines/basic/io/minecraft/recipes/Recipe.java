package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes;

import ru.itaros.api.hoe.registries.IHOERecipeRegistry;
import net.minecraft.item.Item;

public abstract class Recipe {

	private String name;
	public String getName(){
		return name;
	}
	
	public Recipe(String name) {
		this.name=name;
	}
	public abstract int getIncomingSlots();
	public abstract int getOutcomingSlots();
	
	public abstract Item[] getIncomingStricttypes();
	public abstract Item[] getOutcomingStricttypes();
	
	
	//TODO: HOE-style exception
	public static IHOERecipeRegistry getRecipeRegistry(){
		try{
		return (IHOERecipeRegistry) Class.forName("ru.itaros.hoe.registries.HOERecipeRegistry").getMethod("getInstance").invoke(null);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
