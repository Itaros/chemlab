package ru.itaros.chemlab.loader.recipes;

import ru.itaros.hoe.recipes.RecipesCollection;

public class WoodChainRecipes {
	public static RecipesCollection biogrinderRecipes;
	public static RecipesCollection centrifugalExtractorRecipes;
	//public static RecipesCollection washerRecipes;
	public static RecipesCollection impregnatorRecipes;
	public static RecipesCollection pressRecipes;
	public static RecipesCollection steamboilerRecipes;
	public static RecipesCollection steamexplosionunitRecipes;
	
	
	public static void load(){
		biogrinderRecipes = new RecipesCollection();
		biogrinderRecipes.register();
		
		centrifugalExtractorRecipes = new RecipesCollection();
		centrifugalExtractorRecipes.register();
		
		impregnatorRecipes = new RecipesCollection();
		impregnatorRecipes.register();		
		
		pressRecipes = new RecipesCollection();
		pressRecipes.register();			
		
		steamboilerRecipes = new RecipesCollection();
		steamboilerRecipes.register();
		
		steamexplosionunitRecipes = new RecipesCollection();
		steamexplosionunitRecipes.register();		
		
		
	}
	
	
	
	
}
