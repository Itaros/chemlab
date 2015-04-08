package ru.itaros.chemlab.tiles;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.tiles.MachineCrafterTileEntity;

public class BiogrinderTileEntity extends MachineCrafterTileEntity {

	@Override
	public HOELinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineCrafterIO) ChemLab.getIOCollection().getHOEIO("BiogrinderIO");
	}

}
