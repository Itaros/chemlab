package ru.itaros.api.hoe;

import java.lang.reflect.InvocationTargetException;

import ru.itaros.api.hoe.exceptions.HOEBaconNotWalrusException;

public abstract class HOEAbstractLinker {
	protected IHOEInterfacer getCurrentHOEInterfacer(){
		try {
			Class<?> hoeclass = Class.forName("ru.itaros.hoe.HOE");
			IHOEInterfacer interfacer =((IHOEInterfacer) hoeclass.getMethod("getInterfacer", null).invoke(null, null));
			return interfacer;
		} catch (ClassNotFoundException e) {
			throw new HOEBaconNotWalrusException();
		} catch (NoSuchMethodException e) {
			throw new HOEBaconNotWalrusException();
		} catch (SecurityException e) {
			throw new HOEBaconNotWalrusException();
			//TODO: WRON SECURIRY CONFIG NOTIFICATION
		} catch (IllegalAccessException e) {
			throw new HOEBaconNotWalrusException();
		} catch (IllegalArgumentException e) {
			throw new HOEBaconNotWalrusException();
		} catch (InvocationTargetException e) {
			throw new HOEBaconNotWalrusException();
		}
		
	}
	
	
	protected IHOEInterfacer interfacer;
	protected IHOEThread myapartment;	
	
	protected IHOEJob lowjob;
	public void init(){
		interfacer=this.getCurrentHOEInterfacer();
		myapartment=interfacer.AcquareHOEDomain();
	}
	
	public IHOEJob getJob(){
		return lowjob;
	}
	
	
}
