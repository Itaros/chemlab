package ru.itaros.chemlab.fluids.hoe;


public class AirLiquid extends Air{

	public AirLiquid(){
		detectCommonName();
	}
	
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

	@Override
	public HOEFluidState getState() {
		return HOEFluidState.LIQUID;
	}


	
	

}
