package ru.itaros.chemlab.hoe.io;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.HeatingFurnaceData;
import ru.itaros.hoe.io.HOEMachineIO;

public class HeatingFurnaceIO extends HOEMachineIO {

	public static final int MAXPOWER = 0;
	public static final int INCOMING_PORTS		=	2;
	public static final int OUTCOMING_PORTS	=	1;
	
	
	public HeatingFurnaceIO(){
		this.setReq(INCOMING_PORTS, OUTCOMING_PORTS);
		this.allowToStart();
	}


	
	@Override
	public void configureData(HOEData data) {
		HeatingFurnaceData machine=(HeatingFurnaceData) data;
		machine.setMaxPower(MAXPOWER);
		machine.setMachine(this);
		machine.setConfigured();
	}

	@Override
	public long getMeltdownTemperature() {
		return 1600L;
	}
	
	
	@Override
	protected boolean isMachineActive(HOEData data) {
		return true;
	}



	@Override
	protected void produce(HOEData data, boolean doReal) {
		HeatingFurnaceData hfd = (HeatingFurnaceData)data;
		if(hfd.isReactionOngoing()){
			hfd.performReaction();
		}else{
			hfd.tryPickReaction();
		}
	}



}
