package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class DiaphragmalElectrolyzerRecipes {

	public static RecipesCollection recipes;
	
	public static void load(){
		//TODO: two empty capsules instead of one. But there is a problem with recipes related to this
		ItemStack[] i = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.nacl_solution)),new ItemStack(ItemLoader.emptyhvlc)};
		ItemStack[] o = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sodiumhydroxide_solution)),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.h2_gas)),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.cl2_gas))};
		FixedConversionRecipe fcr = new FixedConversionRecipe(100,100*ChemLabValues.OILPOWER_FACTOR*2,i,o);
		fcr.setUnlocalizedName("diaelectr.naoh");
		
		
		recipes = new RecipesCollection(fcr);
		recipes.register();
		
	}
	
	
}
