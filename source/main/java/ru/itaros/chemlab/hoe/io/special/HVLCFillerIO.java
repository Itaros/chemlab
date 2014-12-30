package ru.itaros.chemlab.hoe.io.special;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.HVLCFillerData;
import ru.itaros.hoe.io.HOEMachineIO;

public class HVLCFillerIO extends HOEMachineIO {

	@Override
	public void configureData(HOEData data) {
		
		if(data instanceof HVLCFillerData){
			HVLCFillerData hff = (HVLCFillerData)data;
			hff.setMachine(this);
			hff.ticksRequared=5;
			hff.setMaxPower(200);
			hff.setConfigured();
		}
	}

	@Override
	protected boolean isMachineActive(HOEData data) {
		HVLCFillerData hdata = (HVLCFillerData)data;
		return hdata.hasWork();
	}

	@Override
	protected void produce(HOEData data, boolean doReal) {
		if(data instanceof HVLCFillerData){
			HVLCFillerData hff = (HVLCFillerData)data;
			hff.produce(doReal);
		}
	}

	@Override
	public long getMeltdownTemperature() {
		return 2000;
	}

}
