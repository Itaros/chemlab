package ru.itaros.hoe.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.utils.StackUtility;

public class FixedConversionRecipe extends Recipe {
	
	
	public FixedConversionRecipe(
			int timeReq,
			int powerReq,
			IUniversalStack input,
			IUniversalStack output
	){	
		this(timeReq,powerReq,new IUniversalStack[]{input},new IUniversalStack[]{output});
	}
	
	public FixedConversionRecipe(
			int timeReq,
			int powerReq,
			IUniversalStack[] gridInput,
			IUniversalStack[] gridOutput
	){
		this(timeReq,powerReq,gridInput,gridOutput,helperGenerateName(gridInput,gridOutput));
	}
	protected static String helperGenerateName(
			IUniversalStack[] in,
			IUniversalStack[] out
	){
		//TODO: this "name" stuff should be internal id as int
		String ins="";
		for(IUniversalStack i:in){
			ins+=i.getUnlocalizedName().hashCode()+",";
		}
		ins+="->";
		for(IUniversalStack o:out){
			ins+=o.getUnlocalizedName().hashCode()+",";
		}		
		
		String s = "hoe:"+ins;
		return s;
	}
	public FixedConversionRecipe
	(
			int timeReq,
			int powerReq,
			IUniversalStack[] gridInput,
			IUniversalStack[] gridOutput, String name
	){
		super(name);
		this.gridInput=gridInput;
		this.gridOutput=gridOutput;
		
		this.timeReq=timeReq;
		this.powerReq=powerReq;
		this.powerReqPerTick=this.powerReq/this.timeReq;
	}
	
	protected IUniversalStack[] gridInput;
	protected IUniversalStack[] gridOutput;
	
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
	public IUniversalStack[] getIncomingStricttypes() {
		return gridInput;
	}
	@Override
	public IUniversalStack[] getOutcomingStricttypes() {
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
			int gridOvershoot = gridOutput[i].getStackSize();
			int maxstack = gridOutput[i].getMaxStackSize();
			IUniversalStack comparable = data.get_out(i);
			if(UniversalStackUtils.isNull(comparable)){continue;}//free
			int indexedAmount = comparable.getStackSize();
			if(indexedAmount+gridOvershoot>maxstack){return false;}
		}
		return true;
	}
	@Override
	public boolean checkResources(HOEMachineCrafterData data) {
		for(int i = 0; i<gridInput.length;i++){
			int gridReq = gridInput[i].getStackSize();
			IUniversalStack comparable = data.get_in(i);
			if(comparable==null){return false;}//no res
			int indexedAmount = comparable.getStackSize();
			if(indexedAmount-gridReq<0){
				return false;
			}
		}
		return true;
	}
	
	
	
	
	@Override
	public void consumeResources(HOEMachineCrafterData data) {
		for(int i = 0; i<gridInput.length;i++){
			int gridReq = gridInput[i].getStackSize();	
			data.set_in(i,StackUtility.decrementStack(data.get_in(i),gridReq));
		}
	}
	
	@Override
	public void incrementProduction(HOEMachineCrafterData data) {
		for(int i = 0; i<gridOutput.length;i++){
			int gridSurp = gridOutput[i].getStackSize();
			IUniversalStack stack = data.get_out(i);
			if(UniversalStackUtils.isNull(stack)){
				stack=UniversalStackUtils.copyUniversalStack(gridOutput[i]);
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
