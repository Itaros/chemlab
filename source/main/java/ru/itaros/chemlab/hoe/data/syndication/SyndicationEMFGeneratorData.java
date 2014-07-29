package ru.itaros.chemlab.hoe.data.syndication;

import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.hoe.data.machines.HOEMachineData;

public class SyndicationEMFGeneratorData extends HOEMachineData {
	/*
	 * Reflection autocaster
	 */
	public SyndicationEMFGeneratorData(HOEData parent){
		super(parent);
	}	
	
	public SyndicationEMFGeneratorData(){
		super();
	}

	@Override
	protected void readInventoryNBT(NBTTagCompound nbt) {
		super.readInventoryNBT(nbt);
	}

	@Override
	protected void writeInventoryNBT(NBTTagCompound nbt) {
		super.writeInventoryNBT(nbt);
	}
	
	
	
	
	
}
