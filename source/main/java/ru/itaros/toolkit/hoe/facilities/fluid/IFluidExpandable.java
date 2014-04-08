package ru.itaros.toolkit.hoe.facilities.fluid;

public interface IFluidExpandable {

	public HOEFluid getExpandedForm();
	public int getExpansionRatio();
	public int getReleasedEnergyForExpansion();
	
}
