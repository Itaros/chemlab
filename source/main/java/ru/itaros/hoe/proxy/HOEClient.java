package ru.itaros.hoe.proxy;

import ru.itaros.api.hoe.IHOEInterfacer;
import ru.itaros.hoe.interfacer.HOEInterfacer;
import ru.itaros.hoe.interfacer.HOEInterfacerDummy;

public class HOEClient extends HOEProxy {
	@Override
	public void initHOE() {
		//NOP
	}

	@Override
	public void shutdownHOE() {
		//NOP
	}
	
	@Override
	public IHOEInterfacer getInterfacer() {
		return new HOEInterfacerDummy();
	}
	
}
