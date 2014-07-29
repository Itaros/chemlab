package ru.itaros.hoe.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.utils.StackUtility;

public class FixedConversionMetaUnawareRecipe extends FixedConversionRecipe {


	public FixedConversionMetaUnawareRecipe(int timeReq, int powerReq,
			ItemStack[] input, ItemStack[] output) {
		super(timeReq, powerReq, input, output);
	}
	public FixedConversionMetaUnawareRecipe(int timeReq, int powerReq,
			ItemStack input, ItemStack output) {
		super(timeReq, powerReq, input, output);
	}
	
	
	
	
	
	@Override
	public boolean checkStorage(HOEMachineCrafterData data) {
		boolean isInitialStorageSizeValid = super.checkStorage(data);
		boolean isSWGValid = false;
		
		ItemStack outcome = data.get_out(0);
		if(outcome==null || outcome.stackSize==0){return true;}else{
			ItemStack income = data.get_in(0);
			if(income==null){return false;}
			int sourcedam = income.getItemDamage();
			isSWGValid = sourcedam==outcome.getItemDamage();
		}
		return isInitialStorageSizeValid & isSWGValid;
	}






	@Override
	public void incrementProduction(HOEMachineCrafterData data) {}

	public void incrementProduction(HOEMachineCrafterData data, int damage) {
		for(int i = 0; i<gridOutput.length;i++){
			int gridSurp = gridOutput[i].stackSize;
			ItemStack stack = data.get_out(i);
			if(stack==null){
				stack=ItemStack.copyItemStack(gridOutput[i]);
			}else{
				stack = StackUtility.incrementStack(stack,gridSurp);
			}
			stack.setItemDamage(damage);
			
			data.set_out(i,stack);
		}
	}	
	
	@Override
	public void performProduction(HOEMachineCrafterData data) {
		int meta = pollItemDamage(data);
		consumeResources(data);
		incrementProduction(data,meta);
	}

	private int pollItemDamage(HOEMachineCrafterData data){
		ItemStack stack = data.get_in(0);
		if(stack==null){return 0;}
		return stack.getItemDamage();
	}	
	
	@Override
	public int getSlotIdFor(ItemStack type, boolean ignoreMetadata) {
		return super.getSlotIdFor(type, true);
	}

	
	
	
	
	
}
