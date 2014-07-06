package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class MixerRecipes {
	public static RecipesCollection recipes;
	
	public static void load(){
		ItemStack[] in,out;
		
		in = new ItemStack[]	{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.h2_gas),4),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.nitrogen_gas),4),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.carbonmonooxide_gas),2)};
		out = new ItemStack[]	{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.endothermic_gas),1)};
		FixedConversionRecipe endothermicgas = new FixedConversionRecipe(50, 20, in, out);
		endothermicgas.setUnlocalizedName("mixer.endogas");
		
		in = new ItemStack[]	{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.h2_gas),4),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.nitrogen_gas),4)};
		out = new ItemStack[]	{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.forming_gas),1)};
		FixedConversionRecipe forminggas = new FixedConversionRecipe(50, 20, in, out);
		forminggas.setUnlocalizedName("mixer.formigas");
		
		
		
		recipes = new RecipesCollection(endothermicgas,forminggas);
		recipes.register();
		
		
	}
	
	
}
