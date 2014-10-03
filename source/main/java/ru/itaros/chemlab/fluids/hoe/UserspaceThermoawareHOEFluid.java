package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.hoe.physics.IMatterState;

public class UserspaceThermoawareHOEFluid extends UserspaceHOEFluid implements IMatterState{

	private int lowerTemp;
	private int higherTemp;
	private IMatterState lowerForm;
	private IMatterState higherForm;
	private float heatcapacity;
	private long resistance;
	
	public UserspaceThermoawareHOEFluid(String unlocalizedName, int color,
			HOEFluidState state, int lowerTemp, int higherTemp, IMatterState lowerForm, IMatterState higherForm, float heatcapacity, long resistance) {
		super(unlocalizedName, color, state);
		
		this.lowerTemp=lowerTemp;
		this.lowerForm=lowerForm;
		this.higherForm=higherForm;
		this.higherTemp=higherTemp;
		this.heatcapacity=heatcapacity;
		this.resistance=resistance;
	}

	@Override
	public int lowerPoint() {
		return lowerTemp;
	}

	@Override
	public IMatterState lowerForm() {
		return lowerForm;
	}

	@Override
	public int upperPoint() {
		return higherTemp;
	}

	@Override
	public IMatterState upperForm() {
		return higherForm;
	}

	@Override
	public float heatCapacity() {
		return heatcapacity;
	}

	@Override
	public long resistance() {
		return resistance;
	}

}
