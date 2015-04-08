package ru.itaros.api.hoe.heat;

/*
 * This interface is used on HOEData.
 */
public interface IHeatContainer {

	public Heat getHeat();

	public void updateDistribution();
	
	public long getMeltdownPoint();
	
}
