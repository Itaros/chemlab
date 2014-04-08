package ru.itaros.toolkit.hoe.facilities.fluid;

public interface IFluidCompressable {

	public HOEFluid getCompressedForm();
	public int getCompressionRatio();
	public int getRequaredEnergyForCompression();
	
}
