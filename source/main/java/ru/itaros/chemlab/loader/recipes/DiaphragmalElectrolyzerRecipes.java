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

public class DiaphragmalElectrolyzerRecipes {

	public static RecipesCollection recipes;
	
	public static void load(){
		IUniversalStack[] i = UniversalStackUtils.convert(new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.nacl_solution)),new ItemStack(ItemLoader.emptyhvlc,2)});
		IUniversalStack[] o = UniversalStackUtils.convert(new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sodiumhydroxide_solution)),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.h2_gas)),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.cl2_gas))});
		FixedConversionRecipe fcr = new FixedConversionRecipe(100,100*ChemLabValues.OILPOWER_FACTOR*2,i,o);
		fcr.setUnlocalizedName("diaelectr.naoh");
		
		
		recipes = new RecipesCollection(fcr);
		recipes.register();
		
	}
	
	
}
