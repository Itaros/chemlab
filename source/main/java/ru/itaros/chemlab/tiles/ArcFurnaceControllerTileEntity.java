package ru.itaros.chemlab.tiles;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.ArcFurnaceControllerData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class ArcFurnaceControllerTileEntity extends MachineTileEntity {

	public ArcFurnaceControllerTileEntity(){
		super();
	}
	
	@Override
	protected HOEMachineData acquareData(HOEMachines machines,
			HOEDataFingerprint fingerprint) {
		ArcFurnaceControllerData sbd= new ArcFurnaceControllerData();
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
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("ArcFurnaceControllerIO");
	}

}
