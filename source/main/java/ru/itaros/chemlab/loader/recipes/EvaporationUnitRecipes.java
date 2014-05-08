package ru.itaros.chemlab.loader.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid.TempShift;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluidStack;
import ru.itaros.toolkit.hoe.facilities.fluid.IFluidComposite;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class EvaporationUnitRecipes {
	public static final int REQUIRED_TIME=100;
	
	public static RecipesCollection recipes;
	
	public static void load(){
		
		ArrayList<FixedConversionRecipe> templist = new ArrayList<FixedConversionRecipe>();
		
		HOEFluid[] fluids = HOEFluid.getFluidRegistry().all();
		for(HOEFluid f:fluids){
			if(f.getTemperature()!=TempShift.cooled){continue;}
			if(f instanceof IFluidComposite){
				IFluidComposite ifc = (IFluidComposite)f;
				
				ItemStack[] i = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(f))};
				HOEFluidStack[] composition = ifc.getComposition();
				
				ArrayList<ItemStack> os = new ArrayList<ItemStack>();
				for(HOEFluidStack stack:composition){
					ItemStack part = new ItemStack(HiVolumeLiquidCell.getByFluid(stack.type),stack.stackSize);
					os.add(part);
				}
				ItemStack[] o = new ItemStack[os.size()];
				o = os.toArray(o);
				int requaredEnergy = 1;// ifc.getReleasedEnergyForExpansion();
				templist.add(new FixedConversionRecipe(REQUIRED_TIME,requaredEnergy,i,o));
			}
		}
		
		FixedConversionRecipe[] rca = new FixedConversionRecipe[templist.size()];
		rca=templist.toArray(rca);
		
		recipes = new RecipesCollection(rca);
		recipes.register();
		
	}
}
