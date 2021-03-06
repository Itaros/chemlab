package ru.itaros.chemlab.hoe.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.api.hoe.heat.Heat;
import ru.itaros.api.hoe.heat.IHeatContainer;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.hoe.adapter.HOEAdapters;
import ru.itaros.hoe.data.ISynchroportItems;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalItemStack;
import ru.itaros.hoe.itemhandling.UniversalStackFactory;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.utils.ItemStackTransferTuple;
import ru.itaros.hoe.utils.StackUtility;

public class BloomeryData extends HOEMachineData implements ISynchroportItems,
		IHeatContainer {

	/*
	 * Reflection autocaster
	 */
	public BloomeryData(HOEData parent){
		super(parent);
		//configure();
	}	
	
	public BloomeryData(){
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

	private Heat heat = new Heat(1402800000L);
	
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
		filter = ironOreFilter;
		//There is no hope if this is not an item. But really, this is a mess...
		if(inbound instanceof UniversalItemStack || inbound==null){
			transferItemStackTuple.fill((ItemStack) UniversalStackUtils.getSafeProxy(inbound), source);
			source=StackUtility.tryToPutIn(transferItemStackTuple,ignoreInboundMetadata,filter);
			inbound=UniversalStackUtils.setSafeProxy(inbound,transferItemStackTuple.retr1());
			this.markDirty();
		}
		return source;
	}

	ItemStack ironOreFilter = OreDictionary.getOres("oreIron").get(0);
	ItemStack ironBloomProto = OreDictionary.getOres("bloomIron").get(0);
	
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
			BloomeryData childd = (BloomeryData)child;
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

	public void produce() {
		if(inbound!=null && inbound.getProxy()!=null){
			inbound.decrement(1);
			inbound=StackUtility.verify(inbound);
			
			if(outbound==null || outbound.getProxy()==null){
				outbound=UniversalStackFactory.wrap(ironBloomProto.copy());
			}else{
				outbound.increment(1);
			}
		}
	}

	
	
	
}
