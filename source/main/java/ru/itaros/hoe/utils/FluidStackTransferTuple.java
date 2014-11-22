package ru.itaros.hoe.utils;

import ru.itaros.hoe.fluid.HOEFluidStack;
import net.minecraftforge.fluids.FluidStack;

public class FluidStackTransferTuple {

	FluidStack stack1_kl;
	HOEFluidStack stack1;
	FluidStack stack2_kl;
	HOEFluidStack stack2;
	
	public void fill(HOEFluidStack hoeFluidStack, FluidStack stack2){
		this.stack1=hoeFluidStack;
		this.stack2_kl=stack2;
	}
	
	public void fill(FluidStack fluidStack, HOEFluidStack stack2){
		this.stack1_kl=fluidStack;
		this.stack2=stack2;
	}	
	
	public HOEFluidStack retr1(){
		return stack1;
	}
	public FluidStack retr1_kl(){
		return stack1_kl;
	}	
	public FluidStack retr2_kl(){
		return stack2_kl;
	}	
	public HOEFluidStack retr2(){
		return stack2;
	}		
	
}
