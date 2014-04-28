package ru.itaros.chemlab.proxy;

import ru.itaros.chemlab.HOELinker;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;

public class Server extends Proxy {

	protected HOELinker linker;
	public HOELinker getLinker(){
		return linker;
	}
	@Override
	public void initLinker() {
		linker=new HOELinker();
	}
	
	@Override
	public void openProgrammerGUI(MachineCrafterTileEntity tile) {
		// TODO Auto-generated method stub
		
	}
	
}
