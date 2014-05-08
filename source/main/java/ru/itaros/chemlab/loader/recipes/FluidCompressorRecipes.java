package ru.itaros.chemlab.loader.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;
import ru.itaros.toolkit.hoe.facilities.fluid.IFluidCompressable;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class FluidCompressorRecipes {

	public static final int REQUIRED_TIME=100;
	
	public static RecipesCollection recipes;
	
	public static void load(){
		
		ArrayList<FixedConversionRecipe> templist = new ArrayList<FixedConversionRecipe>();
		
		HOEFluid[] fluids = HOEFluid.getFluidRegistry().all();
		for(HOEFluid f:fluids){
			if(f instanceof IFluidCompressable){
				IFluidCompressable ifc = (IFluidCompressable)f;
				
				ItemStack i = new ItemStack(HiVolumeLiquidCell.getByFluid(f));
				ItemStack o = new ItemStack(HiVolumeLiquidCell.getByFluid(ifc.getCompressedForm()));
				int requaredEnergy = ifc.getRequaredEnergyForCompression();
				templist.add(new FixedConversionRecipe(REQUIRED_TIME,requaredEnergy,i,o));
			}
		}
		
		FixedConversionRecipe[] rca = new FixedConversionRecipe[templist.size()];
		rca=templist.toArray(rca);
		
		recipes = new RecipesCollection(rca);
		recipes.register();
		
	}
	
	
}
