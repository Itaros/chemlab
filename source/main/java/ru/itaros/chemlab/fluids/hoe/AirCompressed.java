package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.IFluidExpandable;

public class AirCompressed extends Air implements IFluidExpandable {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:air.compressed";
	}

	@Override
	public int getColor() {
		return 0xFFFFFF;
	}

	@Override
	public HOEFluid getExpandedForm() {
		return HOEFluidLoader.air_liquid;
	}

	@Override
	public int getExpansionRatio() {
		return Air.COMPRESSION_RATIO;
	}

	@Override
	public int getReleasedEnergyForExpansion() {
		return Air.COMPRESSION_ENERGY;
	}

}
