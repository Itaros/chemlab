package ru.itaros.chemlab.proxy;

import ru.itaros.chemlab.HOELinker;

public class Server extends Proxy {

	protected HOELinker linker;
	public HOELinker getLinker(){
		return linker;
	}
	@Override
	public void initLinker() {
		linker=new HOELinker();
	}
	
}
