package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.hoe.fluid.HOEFluid;

public class SodiumHydroxideSolution extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:sodiumhydroxide-solution";
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public HOEFluidState getState() {
		return HOEFluidState.LIQUID;
	}		
}
