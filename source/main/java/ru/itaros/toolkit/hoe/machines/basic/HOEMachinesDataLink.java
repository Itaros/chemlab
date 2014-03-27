package ru.itaros.toolkit.hoe.machines.basic;

import ru.itaros.api.hoe.IHOEDataLink;
import ru.itaros.api.hoe.exceptions.HOENoSuchDataExistsException;
import ru.itaros.api.hoe.internal.HOEData;

public final class HOEMachinesDataLink implements IHOEDataLink {

	private HOEMachines threadproto;
	public HOEMachinesDataLink(HOEMachines threadproto){
		this.threadproto=threadproto;
	}
	
	@Override
	public HOEMachineData generateCell() {
		return threadproto.generateMachineData();
	}

	@Override
	public void deleteCell(HOEData data) throws HOENoSuchDataExistsException {
		threadproto.removeMachineData((HOEMachineData)data);
	}

}
