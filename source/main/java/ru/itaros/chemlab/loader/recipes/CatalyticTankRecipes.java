package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;

public class CatalyticTankRecipes {

	public static RecipesCollection recipes;
	
	public static void load(){
		
		FixedConversionRecipe fcr1;
		IUniversalStack[] i = UniversalStackUtils.convert(new ItemStack[]{
				new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sulphurdioxide_gas),2),
				new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.oxygen_gas),1)
				});
		IUniversalStack[] o = UniversalStackUtils.convert(new ItemStack[]{
				new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sulphurtrioxide_gas),2)
				});
		fcr1 = new FixedConversionRecipe(1000,1000*2,i,o);//Can be powered by redstone engines
		fcr1.setUnlocalizedName("cattank.sulphurtrioxide");
		
		recipes = new RecipesCollection(fcr1);
		recipes.register();
		
	}
}
