package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.loader.recipes.AirCollectorRecipes;
import ru.itaros.chemlab.loader.recipes.AutomaticDrawplateRecipes;
import ru.itaros.chemlab.loader.recipes.CatalyticTankRecipes;
import ru.itaros.chemlab.loader.recipes.CrusherRecipes;
import ru.itaros.chemlab.loader.recipes.DiaphragmalElectrolyzerRecipes;
import ru.itaros.chemlab.loader.recipes.EvaporationUnitRecipes;
import ru.itaros.chemlab.loader.recipes.FluidCompressorRecipes;
import ru.itaros.chemlab.loader.recipes.GravMagRecipes;
import ru.itaros.chemlab.loader.recipes.HiRMixerRecipes;
import ru.itaros.chemlab.loader.recipes.HiTFurnaceRecipes;
import ru.itaros.chemlab.loader.recipes.MetalFormationMachineRecipes;
import ru.itaros.chemlab.loader.recipes.MixerRecipes;
import ru.itaros.chemlab.loader.recipes.QuenchingChamberRecipes;
import ru.itaros.chemlab.loader.recipes.TurboexpanderRecipes;
import ru.itaros.chemlab.loader.recipes.WasherRecipes;
import ru.itaros.chemlab.loader.recipes.WireCoatingExtruderRecipes;
import ru.itaros.chemlab.loader.recipes.WoodChainRecipes;


public class RecipesLoader {
	public static void load(){
		
		CrusherRecipes.load();
		HiTFurnaceRecipes.load();
		
		WoodChainRecipes.load();
		DiaphragmalElectrolyzerRecipes.load();
		
		AirCollectorRecipes.load();
		
		FluidCompressorRecipes.load();
		TurboexpanderRecipes.load();
		EvaporationUnitRecipes.load();
		
		CatalyticTankRecipes.load();
		
		HiRMixerRecipes.load();
		WasherRecipes.load();
		MixerRecipes.load();
		
		AutomaticDrawplateRecipes.load();
		QuenchingChamberRecipes.load();
		MetalFormationMachineRecipes.load();
		
		WireCoatingExtruderRecipes.load();
		
		GravMagRecipes.load();
		
	}

	

	

}
