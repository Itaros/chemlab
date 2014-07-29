package ru.itaros.hoe.tiles;

import ru.itaros.hoe.data.machines.HOEMachineData;

public interface IHOEInventorySyncable {

	/*
	 * Those methods are signal callbacks for MC INVENTORY<->HOE INVENTORY sync
	 * Those syncs are performed on getServerData()
	 */
	public void pushToHOE();
	public void pullFromHOE();
	
	/*
	 * Inherited from MachineTileEntity
	 */
	public void sync();
	/*
	 * Inherited from MachineTileEntity
	 */	
	public HOEMachineData getServerData();
	
}
