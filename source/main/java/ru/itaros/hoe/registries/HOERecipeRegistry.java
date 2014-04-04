package ru.itaros.hoe.registries;

import java.util.Hashtable;

import ru.itaros.api.hoe.registries.IHOERecipeRegistry;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.Recipe;

public class HOERecipeRegistry implements IHOERecipeRegistry {

	Hashtable<String,Recipe> recipes = new Hashtable<String,Recipe>();
	
	private static  HOERecipeRegistry instance;
	public static  HOERecipeRegistry getInstance(){
		return instance;
	}	
	public HOERecipeRegistry(){
		instance=this;
		System.out.println("HOERECIPEREG is initialized!");
	}
	
	@Override
	public void add(Recipe r) {
		recipes.put(r.getName(), r);
	}

	@Override
	public Recipe get(String mnemonic) {
		if(recipes.containsKey(mnemonic)){
			return recipes.get(mnemonic);
		}else{
			return null;
		}
	}

}
