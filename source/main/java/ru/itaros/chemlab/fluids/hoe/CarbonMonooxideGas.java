package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class CarbonMonooxideGas extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:carbonmonooxide-gas";
	}

	@Override
	public int getColor() {
		return 0;
	}

	@Override
	public HOEFluidState getState() {
		return HOEFluidState.GAS;
	}	
	
}
