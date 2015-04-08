package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.hoe.fluid.HOEFluid;

public class UserspaceHOEFluid extends HOEFluid{

	private String name;
	private int color;
	private HOEFluidState state;
	
	public UserspaceHOEFluid(String groupname,String name, int color, HOEFluidState state){
		this.name=name;
		this.color=color;
		this.state=state;
		detectCommonName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return name;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public HOEFluidState getState() {
		return state;
	}

}
