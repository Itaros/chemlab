package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services;

import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;

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
