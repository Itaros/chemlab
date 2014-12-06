package ru.itaros.chemlab.tiles;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.BloomeryData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class BloomeryTileEntity extends MachineTileEntity {

	@Override
	public HOELinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("BloomeryIO");
	}

	@Override
	protected HOEMachineData acquareData(HOEMachines machines,
			HOEDataFingerprint fingerprint) {
		BloomeryData data = new BloomeryData();
		machines.injectCustomData(data);
		data.setOwnerFingerprint(fingerprint);
		return data;
	}



}
