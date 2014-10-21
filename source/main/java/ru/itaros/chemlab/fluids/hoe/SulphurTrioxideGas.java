package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.hoe.fluid.HOEFluid;

public class SulphurTrioxideGas extends HOEFluid {

	public SulphurTrioxideGas(){
		detectCommonName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return "chemlab:sulphurtrioxide-gas";
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
