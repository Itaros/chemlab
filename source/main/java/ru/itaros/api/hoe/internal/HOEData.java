package ru.itaros.api.hoe.internal;

import ru.itaros.hoe.io.HOEBiPolarSocket;



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
	

	
	private boolean isRunning=true;
	public void invalidate() {
		isRunning=false;
		HOEData child = getChild();
		if(child!=null){
			child.invalidate();
		}
	}	
	public void revalidate() {
		isRunning=true;
		HOEData child = getChild();
		if(child!=null){
			child.revalidate();
		}			
	}	
	public boolean isRunning(){
		return isRunning;
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
	
	private boolean skipEventNotified=false;
	public void notifySkipEvent() {
		skipEventNotified=true;
	}
	public boolean isSkipEventNotified(){
		return skipEventNotified;
	}
	
}

