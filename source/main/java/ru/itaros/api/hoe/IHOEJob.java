package ru.itaros.api.hoe;

import ru.itaros.hoe.data.machines.HOEMachineData;


public interface IHOEJob {

	/*
	 * HOE Ticker. Tries to be aligned with minecraft
	 * (but doesn't throttle down along with minecraft, 
	 *  BUT can be overburdened on its own)
	 */
	public void run();
	
	/*
	 * Used in error reporting
	 */
	public HOEMachineData getCurrentlyProccessedData();
}
