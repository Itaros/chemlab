package ru.itaros.chemlab.loader.recipes;

import ru.itaros.hoe.recipes.RecipesCollection;

public class MetalFormationMachineRecipes {

	public static RecipesCollection recipes;
	
	
	public static void load(){
		recipes = new RecipesCollection();
		recipes.register();
	}
	
	
}
