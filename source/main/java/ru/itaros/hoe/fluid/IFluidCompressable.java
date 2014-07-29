package ru.itaros.hoe.fluid;

public interface IFluidCompressable {

	public HOEFluid getCompressedForm();
	public int getCompressionRatio();
	public int getRequaredEnergyForCompression();
	
}
