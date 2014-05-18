package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class CellulosalExtractives extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:extractives-cellulo-high";
	}

	@Override
	public int getColor() {
		return 0xFFFFFF;
	}

	@Override
	public HOEFluidState getState() {
		return HOEFluidState.COLLOID;
	}	
	
}
