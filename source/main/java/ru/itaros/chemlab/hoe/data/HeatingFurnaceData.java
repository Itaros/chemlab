package ru.itaros.chemlab.hoe.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.heat.Heat;
import ru.itaros.api.hoe.heat.IHeatContainer;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.items.ChemLabChemicalItem;
import ru.itaros.hoe.adapter.HOEAdapters;
import ru.itaros.hoe.data.ISynchroportItems;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.framework.chemistry.ChemicalReaction;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalItemStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.utils.ItemStackTransferTuple;
import ru.itaros.hoe.utils.StackUtility;

public class HeatingFurnaceData extends HOEMachineData implements
		ISynchroportItems, IHeatContainer {

	/*
	 * Reflection autocaster
	 */
	public HeatingFurnaceData(HOEData parent){
		super(parent);
		//configure();
	}	
	
	public HeatingFurnaceData(){
		super();
		//configure();
	}
	
	private IUniversalStack inbound,outbound;
	
	
	protected boolean ignoreInboundMetadata=false;
	
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

	private Heat heat = new Heat(1670000L*840L);
	
	@Override
	public Heat getHeat() {
		return heat;
	}

	@Override
	public void updateDistribution() {
		HOEData[] exchangables = this.getConnectome().getConnected();
		for(HOEData c : exchangables){
			if(c instanceof IHeatContainer){
				IHeatContainer heatContainer = (IHeatContainer)c;
				heatContainer.getHeat().exchange(getHeat());
			}
		}
		getHeat().exchange(HOEAdapters.getInstance().getEnviroLab().getEnviromentalHeat());
	}

	ItemStackTransferTuple transferItemStackTuple = new ItemStackTransferTuple();
	@Override
	public ItemStack tryToPutItemsIn(ItemStack source) {
		return tryToPutItemsIn(source, null);
	}
	@Override
	public ItemStack tryToPutItemsIn(ItemStack source, ItemStack filter) {
		//There is no hope if this is not an item. But really, this is a mess...
		if(inbound instanceof UniversalItemStack || inbound==null){
			transferItemStackTuple.fill((ItemStack) UniversalStackUtils.getSafeProxy(inbound), source);
			source=StackUtility.tryToPutIn(transferItemStackTuple,ignoreInboundMetadata,filter);
			inbound=UniversalStackUtils.setSafeProxy(inbound,transferItemStackTuple.retr1());
			this.markDirty();
		}
		return source;
	}

	@Override
	public ItemStack tryToGetItemsOut(ItemStack target) {
		return tryToGetItemsOut(target, null);
	}
	@Override
	public ItemStack tryToGetItemsOut(ItemStack target, ItemStack filter) {
		if(outbound instanceof UniversalItemStack){
			transferItemStackTuple.fill(target, (ItemStack) outbound.getProxy());
			target = StackUtility.tryToGetOut(transferItemStackTuple,filter);
			outbound.setProxy(StackUtility.verify(transferItemStackTuple.retr2()));
			this.markDirty();
		}
		return target;
	}

	@Override
	protected void readInventoryNBT(NBTTagCompound nbt) {
		super.readInventoryNBT(nbt);
		heat=Heat.readNBT(heat, nbt, "heat");
		inbound = StackUtility.readUniversalStackFromNBT(nbt, "inbound");
		outbound = StackUtility.readUniversalStackFromNBT(nbt, "outbound");
	}

	@Override
	protected void writeInventoryNBT(NBTTagCompound nbt) {
		super.writeInventoryNBT(nbt);
		Heat.writeNBT(heat, nbt, "heat");
		StackUtility.writeItemStackToNBT(inbound, nbt, "inbound");
		StackUtility.writeItemStackToNBT(outbound, nbt, "outbound");
	}

	@Override
	public boolean sync() {
		boolean isSucceded = super.sync();
		if(isSucceded){
			HeatingFurnaceData childd = (HeatingFurnaceData)child;
			heat.syncInto(childd.heat);
			
			childd.inbound = StackUtility.syncUniversalStacks(childd.inbound, inbound);
			childd.outbound = StackUtility.syncUniversalStacks(childd.outbound, outbound);
		}
		return isSucceded;
	}

	public IUniversalStack get_in() {
		return inbound;
	}

	public IUniversalStack get_out() {
		return outbound;
	}

	@Override
	public long getMeltdownPoint() {
		return io.getMeltdownTemperature();
	}


	//Reaction Framework
	private ChemicalReaction ongoingReaction;
	
	public boolean isReactionOngoing() {
		if(ongoingReaction==null){
			return false;
		}else{
			return true;
		}
	}

	public void tryPickReaction() {
		if(inbound!=null){
			if(inbound.getProxy()==null){inbound=null;return;}//invalid stack
			Object itemObject = inbound.getItem();
			if(itemObject instanceof ChemLabChemicalItem){
				ChemLabChemicalItem clci = (ChemLabChemicalItem)itemObject;
				ongoingReaction = clci.getAtmospericCombustionReaction();
				evalReactionTime();
			}
		}
	}

	private void evalReactionTime() {
		if(ongoingReaction!=null){
			this.ticksRequared=100;
		}
	}

	public void performReaction() {
		if(inbound==null || inbound.getProxy()==null){cancelReaction();return;}
		//It is heating furnace. All is destroyed. CL4 might change that
		getHeat().addEnergy(-1L*ongoingReaction.getReactionEnthalpy()/12L*2260000L/9L);
		inbound.decrement(1);//HACK: Decremental amount should be scaled to stoichiometry
		inbound=StackUtility.verify(inbound);
	}

	private void cancelReaction() {
		ongoingReaction=null;
		this.ticksRequared=100;
	}

	
	
	
}
