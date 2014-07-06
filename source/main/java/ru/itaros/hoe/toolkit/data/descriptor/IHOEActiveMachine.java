package ru.itaros.hoe.toolkit.data.descriptor;

public abstract interface IHOEActiveMachine {

	//protected void reevaluateWork();
	
	//private boolean checkWork();
	
	public void produce(boolean doReal);
	public boolean checkStorage();
	public boolean hasWork();
	
}
