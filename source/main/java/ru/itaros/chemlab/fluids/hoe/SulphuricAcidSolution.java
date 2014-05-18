package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class SulphuricAcidSolution extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:sulphuricacid-solution";
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
