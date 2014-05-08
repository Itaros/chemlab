package ru.itaros.chemlab.fluids.hoe;


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
