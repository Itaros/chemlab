package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class WetLignin extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:lignin-wet";
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HOEFluidState getState() {
		return HOEFluidState.COLLOID;
	}		
	
}
