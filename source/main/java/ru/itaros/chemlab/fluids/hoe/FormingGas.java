package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.hoe.fluid.HOEFluid;

public class FormingGas extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:gas.forming";
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
