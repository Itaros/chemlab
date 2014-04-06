package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class PressurizedSteam extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:steam-pressurized";
	}

	@Override
	public int getColor() {
		return 0xFFFFFF;
	}

}
