package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.BloomeryData;
import ru.itaros.hoe.io.HOEMachineIO;

public class BloomeryIO extends HOEMachineIO {


	public static final int MAXPOWER = 0;
	public static final int INCOMING_PORTS		=	2;
	public static final int OUTCOMING_PORTS	=	1;
	
	
	public BloomeryIO(){
		this.setReq(INCOMING_PORTS, OUTCOMING_PORTS);
		this.allowToStart();
	}


	
	@Override
	public void configureData(HOEData data) {
		BloomeryData machine=(BloomeryData) data;
		machine.setMaxPower(MAXPOWER);
		machine.setMachine(this);
		machine.ticksRequared=100;
		machine.setConfigured();
	}

	@Override
	public long getMeltdownTemperature() {
		return 1600L;
	}
	
	
	@Override
	protected boolean isMachineActive(HOEData data) {
		BloomeryData bdata = (BloomeryData)data;
		return bdata.getHeat().getKelvins()>1400L;
	}



	@Override
	protected void produce(HOEData data, boolean doReal) {
		BloomeryData bdata = (BloomeryData)data;
		bdata.produce();
	}

}
