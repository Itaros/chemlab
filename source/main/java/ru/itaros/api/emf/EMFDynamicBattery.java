package ru.itaros.api.emf;

public class EMFDynamicBattery extends EMFBattery {

	public EMFDynamicBattery() {
		super(0);
	}
	
	public void setMax(int newsize){
		max=newsize;
	}
	
	

}
