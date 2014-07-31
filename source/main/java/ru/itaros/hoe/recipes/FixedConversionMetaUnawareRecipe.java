package ru.itaros.hoe.recipes;

import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.utils.StackUtility;

public class FixedConversionMetaUnawareRecipe extends FixedConversionRecipe {


	public FixedConversionMetaUnawareRecipe(int timeReq, int powerReq,
			IUniversalStack[] input, IUniversalStack[] output) {
		super(timeReq, powerReq, input, output);
	}
	public FixedConversionMetaUnawareRecipe(int timeReq, int powerReq,
			IUniversalStack input, IUniversalStack output) {
		super(timeReq, powerReq, input, output);
	}
	
	
	
	
	
	@Override
	public boolean checkStorage(HOEMachineCrafterData data) {
		boolean isInitialStorageSizeValid = super.checkStorage(data);
		boolean isSWGValid = false;
		
		IUniversalStack outcome = data.get_out(0);
		if(outcome==null || outcome.getStackSize()==0){return true;}else{
			IUniversalStack income = data.get_in(0);
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
			int gridSurp = gridOutput[i].getStackSize();
			IUniversalStack stack = data.get_out(i);
			if(stack==null){
				stack=UniversalStackUtils.copyStack(gridOutput[i]);
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
		IUniversalStack stack = data.get_in(0);
		if(stack==null){return 0;}
		return stack.getItemDamage();
	}	
	
	@Override
	public int getSlotIdFor(IUniversalStack type, boolean ignoreMetadata) {
		return super.getSlotIdFor(type, true);
	}

	
	
	
	
	
}
