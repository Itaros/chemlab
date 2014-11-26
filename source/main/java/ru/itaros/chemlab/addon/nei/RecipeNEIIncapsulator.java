package ru.itaros.chemlab.addon.nei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import codechicken.nei.PositionedStack;
import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.HOEFluidStack;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.recipes.Recipe;

public class RecipeNEIIncapsulator {

	public RecipeNEIIncapsulator(Recipe recipe){
		IUniversalStack[] cl_in = recipe.getIncomingStricttypes();
		IUniversalStack[] cl_ou = recipe.getOutcomingStricttypes();
		
		PositionedStack[] nei_in=generatePositionedStacks(cl_in,true);
		PositionedStack[] nei_ou=generatePositionedStacks(cl_ou,false);
		
		in = new ArrayList<PositionedStack>(Arrays.asList(nei_in));
		out = new ArrayList<PositionedStack>(Arrays.asList(nei_ou));
		if(recipe!=null && recipe.getOwnerIO()!=null && recipe.getOwnerIO().getHostBlock()!=null){
			host = new PositionedStack(new ItemStack(recipe.getOwnerIO().getHostBlock()),64+8,21,false);
		}
	}
	
	List<PositionedStack> in;
	List<PositionedStack> out;
	PositionedStack host;

	private PositionedStack[] generatePositionedStacks(IUniversalStack[] unistacks, boolean isIn) {
		ArrayList<PositionedStack> ps = new ArrayList<PositionedStack>();
		
		int stepping = 19;
		int y_start = 2;//36-stepping;
		
		int xpos=isIn?48-8:112-8;
		
		int horiffset = 0;
		int step = -1;
		int i =-1;
		for(IUniversalStack s : unistacks){
			i++;step++;
			if(step>=3){step=0;horiffset++;}
			ItemStack toAppend;//ItemStack hvlcWrap = 
			if(s.getProxy() instanceof ItemStack){
				toAppend = (ItemStack) s.getProxy();
			}else if(s.getProxy() instanceof HOEFluidStack){
				HOEFluidStack hoefls = (HOEFluidStack) s.getProxy();
				toAppend = HiVolumeLiquidCell.getByFluid(hoefls.getFluid());
				toAppend.stackSize=hoefls.stackSize;
			}else{
				toAppend=null;
			}
			ps.add(new PositionedStack(toAppend,xpos+(horiffset*stepping*(isIn?-1:1)),y_start+(stepping*step),true));
		}
		
		PositionedStack[] psarr = new PositionedStack[ps.size()];
		psarr = ps.toArray(psarr);
		return psarr;
	}
	
	public static RecipeNEIIncapsulator[] generateFromRecipes(Recipe[] recipes){
		RecipeNEIIncapsulator[] rslt = new RecipeNEIIncapsulator[recipes.length];
		int i =-1;
		for(Recipe r: recipes){
			i++;
			rslt[i]=new RecipeNEIIncapsulator(r);
		}
		
		return rslt;
	}

	public static RecipeNEIIncapsulator[] findAllByOutput(
			RecipeNEIIncapsulator[] recipes, ItemStack output) {
		
		ArrayList<RecipeNEIIncapsulator> fill = new ArrayList<RecipeNEIIncapsulator>();
		for(RecipeNEIIncapsulator i:recipes){
			for(PositionedStack ps:i.out){
				if(ps.item.isItemEqual(output)){
					fill.add(i);
					break;
				}else{
					continue;
				}
			}
		}
		
		RecipeNEIIncapsulator[] rslt = new RecipeNEIIncapsulator[fill.size()];
		rslt = fill.toArray(rslt);
		return rslt;
		
	}
	
	public static RecipeNEIIncapsulator[] findAllByInput(
			RecipeNEIIncapsulator[] recipes, ItemStack output) {
		
		ArrayList<RecipeNEIIncapsulator> fill = new ArrayList<RecipeNEIIncapsulator>();
		for(RecipeNEIIncapsulator i:recipes){
			for(PositionedStack ps:i.in){
				if(ps.item.isItemEqual(output)){
					fill.add(i);
					break;
				}else{
					continue;
				}
			}
		}
		
		RecipeNEIIncapsulator[] rslt = new RecipeNEIIncapsulator[fill.size()];
		rslt = fill.toArray(rslt);
		return rslt;
		
	}	
	
	
	
}
