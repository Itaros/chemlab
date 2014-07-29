package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.hoe.fluid.HOEFluid;

public class NaClSolution extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:nacl-solution";
	}

	@Override
	public int getColor() {
		return 0;
	}

	@Override
	public HOEFluidState getState() {
		return HOEFluidState.LIQUID;
	}		
	
}
