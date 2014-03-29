package ru.itaros.hoe.proxy;

import ru.itaros.api.hoe.IHOEInterfacer;
import ru.itaros.hoe.HOE;
import ru.itaros.hoe.interfacer.HOEInterfacer;
import ru.itaros.hoe.threading.HOEThreadController;

public class HOEServer extends HOEProxy {

	public static final int _HOE_THREADS_NUMBER = 1;

	@Override
	public void initHOE() {
		HOE.setController(new HOEThreadController(_HOE_THREADS_NUMBER));
	}

	@Override
	public void shutdownHOE() {
		HOE.HOETC().shutdown();
		HOE.setController(null);
	}

	@Deprecated
	@Override
	public IHOEInterfacer getInterfacer() {
		return new HOEInterfacer();
	}

}
