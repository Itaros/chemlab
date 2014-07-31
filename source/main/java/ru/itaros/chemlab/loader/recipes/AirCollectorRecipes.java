package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;

public class AirCollectorRecipes {

	public static RecipesCollection recipes;
	
	public static void load(){
		
		IUniversalStack i = UniversalStackUtils.convert(new ItemStack(ItemLoader.emptyhvlc));
		IUniversalStack o = UniversalStackUtils.convert(new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.air)));
		
		FixedConversionRecipe fcr = new FixedConversionRecipe(50,50*ChemLabValues.OILPOWER_FACTOR/3,i,o);
		fcr.setUnlocalizedName("aircollector.air");
		
		
		
		recipes = new RecipesCollection(fcr);
		recipes.register();
		
		
	}
	
	
}
