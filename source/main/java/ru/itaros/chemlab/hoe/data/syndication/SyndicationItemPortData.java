package ru.itaros.chemlab.hoe.data.syndication;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.GasChimneyData;
import ru.itaros.hoe.data.utils.SynchroportOperationMode;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.EnumUtility;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackTransferTuple;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackUtility;
import ru.itaros.toolkit.hoe.machines.interfaces.ISynchroportItems;

public class SyndicationItemPortData extends HOEMachineData implements ISynchroportItems {

	/*
	 * Reflection autocaster
	 */
	public SyndicationItemPortData(HOEData parent){
		super(parent);
	}	
	
	public SyndicationItemPortData(){
		super();
	}

	private ItemStack inbound;
	private ItemStack filter;
	
	
	public ItemStack get_in(){
		return inbound;
	}
	public void set_in(ItemStack stack) {
		inbound=stack;
		this.markDirty();
	}	
	
	private SynchroportOperationMode mode=SynchroportOperationMode.UNIFORM;
	
	/*
	 * Switches SynchroportOperationMode to next
	 */
	public void cycleSOM(){
		int next = mode.ordinal();
		next++;
		if(next>=SynchroportOperationMode.values().length){
			next=0;
		}
		mode = SynchroportOperationMode.values()[next];
	}
	public SynchroportOperationMode getSOM(){
		return mode;
	}
	
	StackTransferTuple transferTuple = new StackTransferTuple();
	@Override
	public ItemStack tryToPutIn(ItemStack source) {
		return tryToPutIn(source, null);
	}

	@Override
	public ItemStack tryToPutIn(ItemStack source, ItemStack filter) {
		if(mode.canIn()){
			transferTuple.fill(inbound, source);
			source=StackUtility.tryToPutIn(transferTuple,false,filter);
			inbound=transferTuple.retr1();
		}
		this.markDirty();
		return source;
	}

	@Override
	public ItemStack tryToGetOut(ItemStack target) {
		return tryToGetOut(target, null);
	}

	@Override
	public ItemStack tryToGetOut(ItemStack target, ItemStack filter) {
		if(mode.canOut()){
			transferTuple.fill(target, inbound);
			target = StackUtility.tryToGetOut(transferTuple,filter);
			inbound=StackUtility.verify(transferTuple.retr2());
		}
		this.markDirty();
		return target;
	}

	@Override
	public void sync() {
		super.sync();
		
		SyndicationItemPortData childd=(SyndicationItemPortData) child;
		
		//childd.hasWork=hasWork;		
		childd.inbound = StackUtility.syncItemStacks(childd.inbound, inbound);
		childd.filter = StackUtility.syncItemStacks(childd.filter, filter);
		
		childd.mode = mode;
		
	}
	
	@Override
	protected void readConfigurationNBT(NBTTagCompound nbt) {
		filter=StackUtility.readItemStackFromNBT(nbt, "filter");
		mode = EnumUtility.readEnumValueImplicitly(SynchroportOperationMode.class, nbt, "mode");
		super.readConfigurationNBT(nbt);
	}

	@Override
	protected void writeConfigurationNBT(NBTTagCompound nbt) {
		EnumUtility.writeEnumValueImplicitly(nbt, mode, "mode");
		StackUtility.writeItemStackToNBT(filter, nbt, "filter");
		super.writeConfigurationNBT(nbt);
	}

	@Override
	protected void readInventoryNBT(NBTTagCompound nbt) {
		super.readInventoryNBT(nbt);
		inbound=StackUtility.readItemStackFromNBT(nbt, "in");		
	}

	@Override
	protected void writeInventoryNBT(NBTTagCompound nbt) {
		super.writeInventoryNBT(nbt);
		StackUtility.writeItemStackToNBT(inbound, nbt, "in");			
	}

	public void setFilter(ItemStack stack){
		filter=stack;
	}

	public ItemStack getFilter() {
		return filter;
	}


	@Override
	public boolean isPerformingBlockUpdates(){
		return true;//Always performs;
	}

	
	//Synchromanager(visual inventory sync)
	protected boolean isDirty=false;
	@Override
	public void markDirty() {
		isDirty=true;
	}
	@Override
	public boolean pollDirty() {
		boolean cache = isDirty;
		isDirty=false;
		return cache;
	}
	
	
	
}
