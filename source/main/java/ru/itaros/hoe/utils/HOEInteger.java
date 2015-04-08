package ru.itaros.hoe.utils;

//Mutable integers for HOE Payloads
public class HOEInteger {

	private int i;
	
	public HOEInteger(int i){
		this.i=i;
	}
	
	public HOEInteger add(int a){
		i+=a;
		return this;
	}

	public float toFloat() {
		return (float)i;
	}
	
}
