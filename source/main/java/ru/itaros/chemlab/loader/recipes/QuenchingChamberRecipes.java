package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.loader.TierLoader;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionMetaUnawareRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class QuenchingChamberRecipes {

	public static RecipesCollection recipes;
	
	public static void load(){
		
		FixedConversionMetaUnawareRecipe quench_wire_wrought=null;
		FixedConversionMetaUnawareRecipe quench_wire_iron=null;
		
		ItemStack formingGas_in = new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.forming_gas),20);
		ItemStack formingGas_out = new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.forming_gas),19);
		
		if(TierLoader.L0_WroughtIron.isEnabled()){
			ItemStack ww_in = new ItemStack(ItemLoader.rod_swg_hot_wroughtIron,10);
			ItemStack ww_out = new ItemStack(ItemLoader.rod_swg_wroughtIron,10);
			quench_wire_wrought = new FixedConversionMetaUnawareRecipe(1000,1000,new ItemStack[]{ww_in,formingGas_in},new ItemStack[]{ww_out,formingGas_out});
			quench_wire_wrought.setUnlocalizedName("quencher.wire.wrought");
		}
			ItemStack i_in = new ItemStack(ItemLoader.rod_swg_hot_iron,10);
			ItemStack i_out = new ItemStack(ItemLoader.rod_swg_iron,10);
			quench_wire_iron = new FixedConversionMetaUnawareRecipe(1000,1000,new ItemStack[]{i_in,formingGas_in},new ItemStack[]{i_out,formingGas_out});
			quench_wire_iron.setUnlocalizedName("quencher.wire.iron");		
		
			
		recipes = new RecipesCollection(quench_wire_wrought,quench_wire_iron);
		
		
	}
	
	
}
