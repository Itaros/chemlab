package ru.itaros.hoe.utils;

import ru.itaros.hoe.fluid.HOEFluidStack;
import net.minecraftforge.fluids.FluidStack;

public class FluidStackTransferTuple {

	HOEFluidStack stack1;
	FluidStack stack2;
	
	public void fill(HOEFluidStack hoeFluidStack, FluidStack stack2){
		this.stack1=hoeFluidStack;
		this.stack2=stack2;
	}
	
	public HOEFluidStack retr1(){
		return stack1;
	}
	public FluidStack retr2(){
		return stack2;
	}	
	
}
