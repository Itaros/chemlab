package ru.itaros.chemlab.hoe.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;

public class ServiceBayData extends HOEMachineData {

	/*
	 * Reflection autocaster
	 */
	public ServiceBayData(HOEData parent){
		super(parent);
	}	
	
	public ServiceBayData(){
		super();
	}	
	
	
	public enum ServiceMode{
		STANDBY,
		
		ANODES,
		DIAPHRAGMS,
		CATALIZATION_GRIDS
	}
	
	private ServiceMode current = ServiceMode.STANDBY;
	
	private ItemStack injectables;
	
	@Override
	protected void init() {
		//NOTHING TO INIT
	}

	@Override
	public void sync() {
		super.sync();
		ServiceBayData childd = (ServiceBayData)child;
		if(injectables==null){childd.injectables=null;}
		if(childd.injectables==null && injectables!=null){
			childd.bindChildToParent(this);
		}
		if(childd.injectables!=null && injectables!=null){
			if(childd.injectables.getItem()==injectables.getItem()){
				childd.injectables.stackSize=injectables.stackSize;
			}else{
				childd.bindChildToParent(this);
			}
		}
	}

	@Override
	protected void bindChildToParent(HOEMachineData parent) {
		super.bindChildToParent(parent);
		ServiceBayData sbd_parent = (ServiceBayData)parent;
		if(sbd_parent.injectables!=null){
			injectables = sbd_parent.injectables.copy();
		}else{
			injectables = null;
		}
		current=sbd_parent.current;		
	}

	public boolean hasSupplies(){
		if(injectables==null){return false;}
		if(injectables.stackSize>0){return true;}else{return false;}
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
