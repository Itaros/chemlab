package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes;

import ru.itaros.toolkit.hoe.machines.basic.HOEMachineCrafterData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
	private static String helperGenerateName(
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
			int gridOvershoot = gridOutput[i].stackSize;
			int maxstack = gridOutput[i].getMaxStackSize();
			int indexedAmount = data.getOutboundAmountByIndex(i);
			if(indexedAmount+gridOvershoot>maxstack){return false;}
		}
		return true;
	}
	@Override
	public boolean checkResources(HOEMachineCrafterData data) {
		for(int i = 0; i<gridInput.length;i++){
			int gridReq = gridInput[i].stackSize;
			int indexedAmount = data.getInboundAmountByIndex(i);
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
			 data.decrementInboundAmountByIndex(i,gridReq);
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
	
	@Override
	public void incrementProduction(HOEMachineCrafterData data) {
		for(int i = 0; i<gridOutput.length;i++){
			int gridSurp = gridOutput[i].stackSize;
			 data.incrementOutboundAmountByIndex(i,gridSurp);
		}
	}


}
