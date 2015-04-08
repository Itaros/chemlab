package ru.itaros.chemlab.hoe.io.special;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.GasChimneyData;
import ru.itaros.hoe.io.HOEMachineIO;

public class GasChimneyIO extends HOEMachineIO {

	@Override
	public void configureData(HOEData data) {
		if(data instanceof GasChimneyData){
			GasChimneyData gcd = (GasChimneyData)data;
			gcd.setMachine(this);
			gcd.ticksRequared=20;
			gcd.setConfigured();
		}

	}

	@Override
	protected boolean isMachineActive(HOEData data) {
		return ((GasChimneyData)data).hasWork();
	}

	@Override
	protected void produce(HOEData data, boolean doReal) {
		GasChimneyData d = (GasChimneyData)data;
		d.produce(doReal);
	}

	@Override
	public long getMeltdownTemperature() {
		return 1200L;
	}
	
}
