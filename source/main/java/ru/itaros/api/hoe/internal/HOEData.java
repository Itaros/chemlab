package ru.itaros.api.hoe.internal;


public abstract class HOEData {
	
	public HOEData(){
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
	
	private boolean skipEventNotified=false;
	public void notifySkipEvent() {
		skipEventNotified=true;
	}
	public boolean isSkipEventNotified(){
		return skipEventNotified;
	}
	
}

