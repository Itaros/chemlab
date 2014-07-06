package ru.itaros.chemlab.hoe.syndication;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationEMFGeneratorData;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;

public class SyndicationEMFGeneratorIO extends HOEMachineIO {

	@Override
	public void configureData(HOEData data) {
		if(data instanceof SyndicationEMFGeneratorData){
			SyndicationEMFGeneratorData gcd = (SyndicationEMFGeneratorData)data;
			gcd.setMachine(this);
			//gcd.ticksRequared=20;
			gcd.setMaxPower(5000);
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

}
