package ru.itaros.chemlab.loader.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.IFluidExpandable;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;

public class TurboexpanderRecipes {
	public static final int REQUIRED_TIME=100;
	
	public static RecipesCollection recipes;
	
	public static void load(){
		
		ArrayList<FixedConversionRecipe> templist = new ArrayList<FixedConversionRecipe>();
		
		HOEFluid[] fluids = HOEFluid.getFluidRegistry().all();
		for(HOEFluid f:fluids){
			if(f instanceof IFluidExpandable){
				IFluidExpandable ifc = (IFluidExpandable)f;
				
				IUniversalStack i = UniversalStackUtils.convert(new ItemStack(HiVolumeLiquidCell.getByFluid(f)));
				IUniversalStack o = UniversalStackUtils.convert(new ItemStack(HiVolumeLiquidCell.getByFluid(ifc.getExpandedForm())));
				int requaredEnergy = 10;// ifc.getReleasedEnergyForExpansion();
				FixedConversionRecipe fcr = new FixedConversionRecipe(REQUIRED_TIME,requaredEnergy,i,o);
				fcr.setUnlocalizedName("turboexp."+f.getCommonName());
				templist.add(fcr);
			}
		}
		
		FixedConversionRecipe[] rca = new FixedConversionRecipe[templist.size()];
		rca=templist.toArray(rca);
		
		recipes = new RecipesCollection(rca);
		recipes.register();
		
	}
}
