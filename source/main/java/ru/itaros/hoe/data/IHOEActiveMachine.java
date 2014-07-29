package ru.itaros.hoe.data;

public abstract interface IHOEActiveMachine {

	//protected void reevaluateWork();
	
	//private boolean checkWork();
	
	public void produce(boolean doReal);
	public boolean checkStorage();
	public boolean hasWork();
	
}
