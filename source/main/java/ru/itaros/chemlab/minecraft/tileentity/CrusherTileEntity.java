package ru.itaros.chemlab.minecraft.tileentity;

import ru.itaros.api.hoe.HOEAbstractLinker;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class CrusherTileEntity extends MachineTileEntity {

	@Override
	public HOEAbstractLinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("CrusherIO");
	}

}
