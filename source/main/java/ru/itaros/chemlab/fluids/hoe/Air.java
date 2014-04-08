package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;
import ru.itaros.toolkit.hoe.facilities.fluid.IFluidCompressable;

public class Air extends HOEFluid implements IFluidCompressable{

	public static final int COMPRESSION_ENERGY=1000;
	public static final int COMPRESSION_RATIO=10;
	
	
	@Override
	public String getUnlocalizedName() {
		return "chemlab:air";
	}

	@Override
	public int getColor() {
		return 0xFFFFFF;
	}

	
	@Override
	public HOEFluid getCompressedForm() {
		return HOEFluidLoader.air_compressed;
	}

	@Override
	public int getCompressionRatio() {
		return COMPRESSION_RATIO;
	}

	@Override
	public int getRequaredEnergyForCompression() {
		return COMPRESSION_ENERGY;
	}

}
