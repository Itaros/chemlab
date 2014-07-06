package ru.itaros.chemlab.loader.recipes.bc;

import ru.itaros.chemlab.items.IOMultitool;
import ru.itaros.chemlab.loader.ItemLoader;
import net.minecraft.item.ItemStack;
import buildcraft.BuildCraftSilicon;
import buildcraft.api.recipes.IIntegrationRecipeManager.IIntegrationRecipe;
import buildcraft.silicon.ItemRedstoneChipset.Chipset;

public class IOMultitoolUpgradeSyndicationResetterRecipe implements
		IIntegrationRecipe {

	
	public IOMultitoolUpgradeSyndicationResetterRecipe(){
		components=new ItemStack[]{};
		inA=new ItemStack[]{new ItemStack(ItemLoader.iomultitool)};
		inB=new ItemStack[]{Chipset.GOLD.getStack()};		
	}
	
	@Override
	public ItemStack[] getComponents() {
		return components;
	}

	@Override
	public double getEnergyCost() {
		return 20000;
	}

	ItemStack[] components;
	ItemStack[] inA;
	ItemStack[] inB;
	
	@Override
	public ItemStack[] getExampleInputsA() {
		return inA;
	}

	@Override
	public ItemStack[] getExampleInputsB() {
		return inB;
	}

	@Override
	public ItemStack getOutputForInputs(ItemStack inputA, ItemStack inputB,
			ItemStack[] components) {
		ItemStack output = inputA.copy();
		IOMultitool.addFlag(output,IOMultitool.FLAG_SYND_RESETER);
		return output;
	}

	@Override
	public boolean isValidInputA(ItemStack i) {
		if(i==null){return false;}
		if(i.getItem()==inA[0].getItem()){return true;}
		
		return false;
	}

	@Override
	public boolean isValidInputB(ItemStack i) {
		for(ItemStack s:inB){
			if(ItemStack.areItemStacksEqual(i, s)){return true;}
		}		
		return false;
	}

}
