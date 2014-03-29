package ru.itaros.chemlab.proxy;

import ru.itaros.chemlab.HOELinker;

public class Client extends Proxy {

	@Override
	public void initLinker() {
		//NOP
	}

	@Override
	public HOELinker getLinker() {
		//NOP
		return null;
	}

}
