package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.physics.IMatterState;

public class MoltenIron extends HOEFluid implements IMatterState {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:molten.iron";
	}

	@Override
	public int getColor() {
		return 0;
	}

	@Override
	public HOEFluidState getState() {
		return HOEFluidState.LIQUID;
	}

	@Override
	public int lowerPoint() {
		return 1538+274;//K
	}

	@Override
	public IMatterState lowerForm() {
		return ItemLoader.scraps_iron;
	}

	@Override
	public int upperPoint() {
		return Integer.MAX_VALUE;//K
	}

	@Override
	public IMatterState upperForm() {
		return null;
	}

	@Override
	public float heatCapacity() {
		return 3.539F;//mJ/K
	}

	@Override
	public long resistance() {
		return 97L;//nOhm
	}

}
