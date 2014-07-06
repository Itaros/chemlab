package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class EndothermicGas extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:gas.endothermic";
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
