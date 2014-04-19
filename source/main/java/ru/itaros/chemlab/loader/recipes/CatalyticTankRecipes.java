package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class CatalyticTankRecipes {

	public static RecipesCollection recipes;
	
	public static void load(){
		
		FixedConversionRecipe fcr1;
		ItemStack[] i = {
				new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sulphurdioxide_gas),2),
				new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.oxygen_gas),1)};
		ItemStack[] o = {
				new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sulphurtrioxide_gas),2)
		};
		fcr1 = new FixedConversionRecipe(1000,10,i,o);
		fcr1.setUnlocalizedName("cattank.sulphurtrioxide");
		
		recipes = new RecipesCollection(fcr1);
		recipes.register();
		
	}
}
