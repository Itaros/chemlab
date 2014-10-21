package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.hoe.fluid.HOEFluid;

public class NitrogenGas extends HOEFluid {

	public NitrogenGas(){
		detectCommonName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return "chemlab:nitrogen-gas";
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
