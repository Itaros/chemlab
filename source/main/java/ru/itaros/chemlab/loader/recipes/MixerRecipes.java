package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;

public class MixerRecipes {
	public static RecipesCollection recipes;
	
	public static void load(){
		IUniversalStack[] in,out;
		
		in = UniversalStackUtils.convert(new ItemStack[]	{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.h2_gas),4),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.nitrogen_gas),4),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.carbonmonooxide_gas),2)});
		out = UniversalStackUtils.convert(new ItemStack[]	{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.endothermic_gas),1)});
		FixedConversionRecipe endothermicgas = new FixedConversionRecipe(50, 20, in, out);
		endothermicgas.setUnlocalizedName("mixer.endogas");
		
		in = UniversalStackUtils.convert(new ItemStack[]	{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.h2_gas),4),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.nitrogen_gas),4)});
		out = UniversalStackUtils.convert(new ItemStack[]	{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.forming_gas),1)});
		FixedConversionRecipe forminggas = new FixedConversionRecipe(50, 20, in, out);
		forminggas.setUnlocalizedName("mixer.formigas");
		
		
		
		recipes = new RecipesCollection(endothermicgas,forminggas);
		recipes.register();
		
		
	}
	
	
}
