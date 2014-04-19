package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.loader.recipes.AirCollectorRecipes;
import ru.itaros.chemlab.loader.recipes.CatalyticTankRecipes;
import ru.itaros.chemlab.loader.recipes.CrusherRecipes;
import ru.itaros.chemlab.loader.recipes.DiaphragmalElectrolyzerRecipes;
import ru.itaros.chemlab.loader.recipes.EvaporationUnitRecipes;
import ru.itaros.chemlab.loader.recipes.FluidCompressorRecipes;
import ru.itaros.chemlab.loader.recipes.FurnaceRecipes;
import ru.itaros.chemlab.loader.recipes.TurboexpanderRecipes;
import ru.itaros.chemlab.loader.recipes.WoodChainRecipes;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesLoader {
	public static void load(){
		
		
		CrusherRecipes.load();
		FurnaceRecipes.load();
		
		WoodChainRecipes.load();
		DiaphragmalElectrolyzerRecipes.load();
		
		AirCollectorRecipes.load();
		
		FluidCompressorRecipes.load();
		TurboexpanderRecipes.load();
		EvaporationUnitRecipes.load();
		
		CatalyticTankRecipes.load();
	}

	

	

}
