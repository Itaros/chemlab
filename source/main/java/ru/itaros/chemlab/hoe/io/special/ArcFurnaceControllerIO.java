package ru.itaros.chemlab.hoe.io.special;

import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.io.HOEMachineIO;

public class ArcFurnaceControllerIO extends HOEMachineIO {

	@Override
	public void configureData(HOEData data) {
		HOEMachineData d = (HOEMachineData)data;
		d.setMachine(this);
		d.setConfigured();
	}

	@Override
	protected boolean isMachineActive(HOEData data) {
		return true;
	}

	@Override
	protected void produce(HOEData data, boolean doReal) {
		// TODO Auto-generated method stub

	}

}
