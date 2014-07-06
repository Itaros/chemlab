package ru.itaros.chemlab.loader.recipes.bc;

import buildcraft.api.recipes.BuildcraftRecipes;

public class BCRecipesLoader {

	
	public static void load(){
		loadIntegrationTableRecipes();
	}
	
	private static void loadIntegrationTableRecipes(){
		BuildcraftRecipes.integrationTable.addRecipe(new IOMultitoolUpgradeSyndicationResetterRecipe());
	}
	
	
}

