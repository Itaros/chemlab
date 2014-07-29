package ru.itaros.hoe.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.utils.StackUtility;

public class FixedConversionRecipe extends Recipe {
	
	
	public FixedConversionRecipe(
			int timeReq,
			int powerReq,
			ItemStack input,
			ItemStack output
	){	
		this(timeReq,powerReq,new ItemStack[]{input},new ItemStack[]{output});
	}
	
	public FixedConversionRecipe(
			int timeReq,
			int powerReq,
			ItemStack[] gridInput,
			ItemStack[] gridOutput
	){
		this(timeReq,powerReq,gridInput,gridOutput,helperGenerateName(gridInput,gridOutput));
	}
	protected static String helperGenerateName(
			ItemStack[] in,
			ItemStack[] out
	){
		//TODO: this "name" stuff should be internal id as int
		String ins="";
		for(ItemStack i:in){
			ins+=i.getUnlocalizedName().hashCode()+",";
		}
		ins+="->";
		for(ItemStack o:out){
			ins+=o.getUnlocalizedName().hashCode()+",";
		}		
		
		String s = "hoe:"+ins;
		return s;
	}
	public FixedConversionRecipe
	(
			int timeReq,
			int powerReq,
			ItemStack[] gridInput,
			ItemStack[] gridOutput, String name
	){
		super(name);
		this.gridInput=gridInput;
		this.gridOutput=gridOutput;
		
		this.timeReq=timeReq;
		this.powerReq=powerReq;
		this.powerReqPerTick=this.powerReq/this.timeReq;
	}
	
	protected ItemStack[] gridInput;
	protected ItemStack[] gridOutput;
	
	protected int timeReq;
	protected double powerReq;
	protected double powerReqPerTick;
	
	@Override
	public int getIncomingSlots() {
		return gridInput.length;
	}
	@Override
	public int getOutcomingSlots() {
		return gridOutput.length;
	}
	
	@Override
	public ItemStack[] getIncomingStricttypes() {
		return gridInput;
	}
	@Override
	public ItemStack[] getOutcomingStricttypes() {
		return gridOutput;
	}
	public int getTicksRequared() {
		return timeReq;
	}
	
	//Storage operations
	@Override
	public boolean checkStorage(HOEMachineCrafterData data) {
		for(int i = 0; i<gridOutput.length;i++){
			if(gridOutput[i]==null){continue;}
			int gridOvershoot = gridOutput[i].stackSize;
			int maxstack = gridOutput[i].getMaxStackSize();
			ItemStack comparable = data.get_out(i);
			if(comparable==null){continue;}//free
			int indexedAmount = comparable.stackSize;
			if(indexedAmount+gridOvershoot>maxstack){return false;}
		}
		return true;
	}
	@Override
	public boolean checkResources(HOEMachineCrafterData data) {
		for(int i = 0; i<gridInput.length;i++){
			int gridReq = gridInput[i].stackSize;
			ItemStack comparable = data.get_in(i);
			if(comparable==null){return false;}//no res
			int indexedAmount = comparable.stackSize;
			if(indexedAmount-gridReq<0){
				return false;
			}
		}
		return true;
	}
	
	
	
	
	@Override
	public void consumeResources(HOEMachineCrafterData data) {
		for(int i = 0; i<gridInput.length;i++){
			int gridReq = gridInput[i].stackSize;	
			data.set_in(i,StackUtility.decrementStack(data.get_in(i),gridReq));
		}
	}
	
	@Override
	public void incrementProduction(HOEMachineCrafterData data) {
		for(int i = 0; i<gridOutput.length;i++){
			int gridSurp = gridOutput[i].stackSize;
			ItemStack stack = data.get_out(i);
			if(stack==null){
				stack=ItemStack.copyItemStack(gridOutput[i]);
			}else{
				stack = StackUtility.incrementStack(stack,gridSurp);
			}
			data.set_out(i,stack);
		}
	}	
	
	@Override
	public boolean tryToConsumeEnergy(HOEMachineData hoeMachineData) {
		if(hoeMachineData.getPower()>=powerReqPerTick){
			hoeMachineData.decrementPower(powerReqPerTick);
			return true;
		}else{
			return false;
		}
	}	
	



}
