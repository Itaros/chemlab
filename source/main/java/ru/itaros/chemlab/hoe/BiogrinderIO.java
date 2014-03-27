package ru.itaros.chemlab.hoe;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;

public class BiogrinderIO extends HOEMachineIO {
	private static final int INCOMING_PORTS		=	1;
	private static final int OUTCOMING_PORTS	=	1;
	
	public BiogrinderIO(){
		this.setReq(INCOMING_PORTS, OUTCOMING_PORTS);
		this.allowToStart();
	}
	@Override
	protected void produce(HOEData data) {
		HOEMachineData hm = (HOEMachineData) data;
		
	}

}
