package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

//TODO: HOE generic, not chemlab specific

public class Water extends HOEFluid {

	@Override
	public String getUnlocalizedName() {
		return "chemlab:water-natural";
	}

	@Override
	public int getColor() {
		return 0x3d669c;
	}

}
