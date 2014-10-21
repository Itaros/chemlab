package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.hoe.fluid.HOEFluid;

public class CarbonDioxideGas extends HOEFluid {

	public CarbonDioxideGas(){
		detectCommonName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return "chemlab:carbondioxide-gas";
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
