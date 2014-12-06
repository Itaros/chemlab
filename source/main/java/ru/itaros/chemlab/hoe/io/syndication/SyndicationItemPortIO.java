package ru.itaros.chemlab.hoe.io.syndication;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationItemPortData;
import ru.itaros.hoe.io.HOEMachineIO;

public class SyndicationItemPortIO extends HOEMachineIO {

	@Override
	public void configureData(HOEData data) {
		if(data instanceof SyndicationItemPortData){
			SyndicationItemPortData gcd = (SyndicationItemPortData)data;
			gcd.setMachine(this);
			gcd.ticksRequared=20;
			gcd.setConfigured();
		}

	}

	@Override
	protected boolean isMachineActive(HOEData data) {
		return false;//It does nothing by itself
	}

	

	@Override
	protected void produce(HOEData data, boolean doReal) {
	}

	@Override
	public long getMeltdownTemperature() {
		return 600L;
	}
	
}
