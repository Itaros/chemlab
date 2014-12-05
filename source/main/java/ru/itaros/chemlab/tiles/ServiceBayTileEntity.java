package ru.itaros.chemlab.tiles;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.ServiceBayData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class ServiceBayTileEntity extends MachineTileEntity {
	
	public ServiceBayTileEntity(){
		super();
	}

	@Override
	public void updateEntity() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected HOEMachineData acquareData(HOEMachines machines, HOEDataFingerprint fingerprint) {
		ServiceBayData sbd= new ServiceBayData();
		sbd.setOwnerFingerprint(fingerprint);
		machines.injectCustomData(sbd);
		return sbd;
	}

	@Override
	public HOELinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("ServiceBayIO");
	}

}
