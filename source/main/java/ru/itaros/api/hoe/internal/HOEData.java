package ru.itaros.api.hoe.internal;

import ru.itaros.toolkit.hoe.machines.basic.io.connectivity.HOEBiPolarSocket;



public abstract class HOEData {
	
	public HOEData(){
		connectivity = new HOEBiPolarSocket();
	}
	
	protected HOEData child;
	protected boolean isSided;
	
	public HOEData getChild(){
		return child;
	}
	public abstract boolean isConfigured();
	protected HOEData spawnChild() {
		Class<? extends HOEData> mytype = this.getClass();
		try {
			child = mytype.getConstructor(HOEData.class).newInstance(this);
			return child;
		} catch (Exception e){
			//Something very bad happened! %_%
			throw new RuntimeException(e);
		}
		//child = new HOEMachineData(this);
	}
	public boolean isSided() {
		return isSided;
	}	
	
	
	
	//================Intercomms================
	HOEBiPolarSocket connectivity;

	public void executeIntercoms(HOEData data) {
		connectivity.operate(data);
	}
	public HOEBiPolarSocket getIntercomSocket(){
		return connectivity;
	}
	//==========================================
	
}

