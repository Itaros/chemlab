package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.hoe.fluid.HOEFluid;

//TODO: HOE generic, not chemlab specific

public class Water extends HOEFluid {

	public Water(){
		detectCommonName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return "chemlab:water-natural";
	}

	@Override
	public int getColor() {
		return 0x3d669c;
	}
	@Override
	public HOEFluidState getState() {
		return HOEFluidState.LIQUID;
	}		
}
