package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class AirCollectorRecipes {

	public static RecipesCollection recipes;
	
	public static void load(){
		
		ItemStack i = new ItemStack(ItemLoader.emptyhvlc);
		ItemStack o = new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.air));
		
		FixedConversionRecipe fcr = new FixedConversionRecipe(50,1000,i,o);
		fcr.setUnlocalizedName("aircollector.air");
		
		
		
		recipes = new RecipesCollection(fcr);
		recipes.register();
		
		
	}
	
	
}
