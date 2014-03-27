package ru.itaros.api.hoe.internal;



public class HOEData {
	protected HOEData child;
	
	public HOEData getChild(){
		return child;
	}
	protected void spawnChild() {
		Class<? extends HOEData> mytype = this.getClass();
		try {
			child = mytype.getConstructor(HOEData.class).newInstance(this);
		} catch (Exception e){
			//Something very bad happened! %_%
			throw new RuntimeException(e);
		}
		//child = new HOEMachineData(this);
	}	
	
}

