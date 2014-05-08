package ru.itaros.hoe.interfacer;

import ru.itaros.api.hoe.IHOEInterfacer;
import ru.itaros.api.hoe.IHOEThread;
import ru.itaros.hoe.HOE;

/*
 * This class provides a way to communicate with HOE
 */
public final class HOEInterfacer implements IHOEInterfacer{
	public IHOEThread AcquareHOEDomain(){
		return HOE.HOETC().getNextDomain();
	}
}
