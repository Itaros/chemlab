package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.hoe.fluid.HOEFluid;

public class SulphurDioxideGas extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:sulphurdioxide-gas";
	}

	@Override
	public int getColor() {
		return 0xFFFFFF;
	}

	@Override
	public HOEFluidState getState() {
		return HOEFluidState.GAS;
	}	
	
}
