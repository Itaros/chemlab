package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.loader.recipes.AirCollectorRecipes;
import ru.itaros.chemlab.loader.recipes.CatalyticTankRecipes;
import ru.itaros.chemlab.loader.recipes.CrusherRecipes;
import ru.itaros.chemlab.loader.recipes.DiaphragmalElectrolyzerRecipes;
import ru.itaros.chemlab.loader.recipes.EvaporationUnitRecipes;
import ru.itaros.chemlab.loader.recipes.FluidCompressorRecipes;
import ru.itaros.chemlab.loader.recipes.HiTFurnaceRecipes;
import ru.itaros.chemlab.loader.recipes.MixerRecipes;
import ru.itaros.chemlab.loader.recipes.TurboexpanderRecipes;
import ru.itaros.chemlab.loader.recipes.VanillaCraftingRecipes;
import ru.itaros.chemlab.loader.recipes.WoodChainRecipes;

public class RecipesLoader {
	public static void load(){
		VanillaCraftingRecipes.load();
		
		CrusherRecipes.load();
		HiTFurnaceRecipes.load();
		
		WoodChainRecipes.load();
		DiaphragmalElectrolyzerRecipes.load();
		
		AirCollectorRecipes.load();
		
		FluidCompressorRecipes.load();
		TurboexpanderRecipes.load();
		EvaporationUnitRecipes.load();
		
		CatalyticTankRecipes.load();
		
		MixerRecipes.load();
		
		
		
	}

	

	

}
