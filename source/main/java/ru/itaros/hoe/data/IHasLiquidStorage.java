package ru.itaros.hoe.data;

import ru.itaros.hoe.fluid.HOEFluidDepot;


/*
 * HOEData interface. Apply it to data which wants to have liquid storage
 */
public interface IHasLiquidStorage {

	public HOEFluidDepot getFluidDepot();
	
	/*
	 * This method is special. It is called to accommodate fluid recipes
	 * or to be configured on startup for static machines
	 */
	public void configureDepot();
	
	/*
	 * This method is invoked to ensure that this depot is empty at all
	 */
	public boolean isDepotReconfigurable();
	
}
