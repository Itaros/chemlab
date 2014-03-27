package ru.itaros.hoe.interfacer;

import ru.itaros.api.hoe.IHOEInterfacer;
import ru.itaros.api.hoe.IHOEThread;
import ru.itaros.api.hoe.exceptions.HOEWrongSideException;

/*
 * This class provides a way to discard all client-side calls to HOE to eliminate obscure null-pointer exceptions.
 */
public class HOEInterfacerDummy implements IHOEInterfacer{

	private void suicide(){
		throw new HOEWrongSideException();
	}
	
	
	@Override
	public IHOEThread AcquareHOEDomain() {
		suicide();
		return null;
	}

}
