package ru.itaros.chemlab.proxy;

import ru.itaros.chemlab.HOELinker;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;

public abstract class Proxy {

	public abstract void initLinker();
	public abstract HOELinker getLinker();
	
	
	public abstract  void openProgrammerGUI(MachineCrafterTileEntity tile);
	
}
