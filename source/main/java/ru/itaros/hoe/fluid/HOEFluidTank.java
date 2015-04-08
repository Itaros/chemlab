package ru.itaros.hoe.fluid;

import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.hoe.utils.FluidUtility;

public class HOEFluidTank {

	private HOEFluidStack fluid;
	private int max;
	
	public HOEFluidTank(HOEFluid filter, int capacity){
		this.fluid = new HOEFluidStack(filter,0);
		this.max=capacity;
	}
	
	
	private HOEFluidTank() {
		// TODO Auto-generated constructor stub
	}


	public int getAmount(){
		return fluid.stackSize;
	}
	public int getCapacity(){
		return max;
	}
	
	
	public int inject(int amount, boolean forReal){
		int current = fluid.stackSize;
		int diff = max-current;
		
		//There are more space
		if(diff>amount){diff=amount;}
		
		if(!forReal){return diff;}
		
		fluid.stackSize+=diff;
		
		return diff;
		
	}
	
	public int extract(int amount, boolean forReal){
		int current = fluid.stackSize;
		if(amount>current){amount=current;}
		
		if(!forReal){return amount;}
		
		fluid.stackSize-=amount;
		
		return amount;
	}


	public boolean contains() {
		if(fluid.stackSize>0){return true;}else{return false;}
	}


	public HOEFluid getFluid() {
		return fluid.getFluid();
	}


	/*
	 * This method extracts fluid only if the required amount is available
	 */
	public boolean extractAtomic(int amount, boolean forReal) {
		if(fluid.stackSize>=amount){
			if(forReal){
				fluid.stackSize-=amount;
			}
			return true;
		}
		return false;
	}


	public void writeToNBT(NBTTagCompound tnbt) {
		tnbt.setInteger("max", max);
		
		fluid.writeNBT(tnbt);
		
	}
	public void readFromNBT(NBTTagCompound tnbt) {
		max = tnbt.getInteger("max");
		
		fluid = HOEFluidStack.createFromNBT(tnbt);
		
	}

	public static HOEFluidTank createFromNBT(NBTTagCompound tnbt) {
		HOEFluidTank f = new HOEFluidTank();
		f.readFromNBT(tnbt);
		return f;
	}



	
	
}
