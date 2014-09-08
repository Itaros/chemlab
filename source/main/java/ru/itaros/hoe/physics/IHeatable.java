package ru.itaros.hoe.physics;

/*
 * Interface defines ability to heat thing(IUstack, TileEntity or anything!)
 */
public interface IHeatable {

	public float getCurrentTemperature();
	public void setCurrentTemperature(float temp);
	
}
