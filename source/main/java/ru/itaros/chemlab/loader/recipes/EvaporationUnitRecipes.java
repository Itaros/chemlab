package ru.itaros.chemlab.loader.recipes;

import ru.itaros.hoe.recipes.RecipesCollection;

public class EvaporationUnitRecipes {
	public static final int REQUIRED_TIME=100;
	
	public static RecipesCollection recipes;
	
	public static void load(){
		recipes = new RecipesCollection();
		recipes.register();
	}
}
