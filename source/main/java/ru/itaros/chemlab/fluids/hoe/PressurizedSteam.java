package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class PressurizedSteam extends HOEFluid {

	
	//Item should use getItemDisplayName for coloring
	@Override
	public String getUnlocalizedName() {
		return "chemlab:steam-pressurized";
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
