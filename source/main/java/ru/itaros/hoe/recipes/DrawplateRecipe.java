package ru.itaros.hoe.recipes;

import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.utils.StackUtility;

public class DrawplateRecipe extends FixedConversionRecipe{

	public DrawplateRecipe(
			int timeReq,
			int powerReq,
			IUniversalStack gridInput,
			IUniversalStack gridWaste
	){
		super(timeReq,powerReq,gridInput,gridInput);
		
		waste=gridWaste;
		
		//Modidications
		IUniversalStack[] output = this.gridOutput;
		IUniversalStack[] newOutput = new IUniversalStack[output.length+1];
		for(int i = 0 ; i < output.length; i++){
			newOutput[i]=output[i];
		}
		newOutput[newOutput.length-1]=gridWaste;
		this.gridOutput=newOutput;
	}	
	
	
	private IUniversalStack waste;
	
	
	@Override
	public void performProduction(HOEMachineCrafterData data) {
		int damage = pollItemDamage(data);
		consumeResources(data);
		incrementProduction(data,damage);
	}

	private int pollItemDamage(HOEMachineCrafterData data){
		IUniversalStack stack = data.get_in(0);
		if(stack==null){return 0;}
		return stack.getItemDamage();
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
			isSWGValid = sourcedam==outcome.getItemDamage()-1;
		}
		return isInitialStorageSizeValid & isSWGValid;
	}





	@Override
	public void incrementProduction(HOEMachineCrafterData data) {
		
	}	
	
	public void incrementProduction(HOEMachineCrafterData data, int damage) {
		boolean isWasteElevated=false;
		for(int i = 0; i<gridOutput.length;i++){
			int gridSurp = gridOutput[i].getStackSize();
			if(isWasteElevated && gridOutput[i]==waste){
				gridSurp*=2;
			}
			IUniversalStack stack = data.get_out(i);
			if(stack==null){
				stack = UniversalStackUtils.copyUniversalStack(gridOutput[i]);
				stack.setStackSize(gridSurp);
			}else{
				stack = StackUtility.incrementStack(stack,gridSurp);
			}
			if(gridOutput[i]!=waste){
				int max = stack.getMaxDamage();
				damage++;
				if(damage>max){
					isWasteElevated=true;
					continue;
				}
				stack.setItemDamage(damage);
			}
			data.set_out(i,stack);
		}
	}		
	
	
}
