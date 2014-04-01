package ru.itaros.chemlab.proxy;

import ru.itaros.chemlab.HOELinker;

public abstract class Proxy {

	public abstract void initLinker();
	public abstract HOELinker getLinker();
	
	
	public abstract  void openProgrammerGUI();
	
}
