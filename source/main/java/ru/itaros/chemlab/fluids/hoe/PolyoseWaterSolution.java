package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class PolyoseWaterSolution extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:polyose-proteined";
	}

	@Override
	public int getColor() {
		return 0x805410;
	}

	@Override
	public HOEFluidState getState() {
		return HOEFluidState.COLLOID;
	}		
	
}
