package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class AirLiquid extends Air{

	@Override
	public String getUnlocalizedName() {
		return "chemlab:air.liquid";
	}

	@Override
	public int getColor() {
		return 0xFFFFFF;
	}

	@Override
	public TempShift getTemperature() {
		return TempShift.cooled;
	}


	
	

}
