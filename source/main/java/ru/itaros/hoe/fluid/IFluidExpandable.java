package ru.itaros.hoe.fluid;

public interface IFluidExpandable {

	public HOEFluid getExpandedForm();
	public int getExpansionRatio();
	public int getReleasedEnergyForExpansion();
	
}
