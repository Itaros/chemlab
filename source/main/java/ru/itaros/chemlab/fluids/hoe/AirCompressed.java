package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;
import ru.itaros.toolkit.hoe.facilities.fluid.IFluidExpandable;

public class AirCompressed extends HOEFluid implements IFluidExpandable {

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
		return HOEFluidLoader.air;
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
